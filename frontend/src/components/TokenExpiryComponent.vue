<template>
    <div>
        <p id="token-result">{{ tokenResult }}</p>
        <div v-if="showAlert" class="color:red font-weight:bold">
            <p>토큰이 곧 만료됩니다. 갱신하려면 클릭하세요</p>
            <button @click="refreshToken">토큰 갱신</button>
        </div>
    </div>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';


const tokenResult = ref<string|null>(null);
const showAlert = ref(false);
let tokenExpiryTime:number|null=null;
const router = useRouter();

onMounted(()=>{
    const accessToken = localStorage.getItem('access_token');
    const refreshToken = localStorage.getItem('refresh_token');
    if(accessToken && refreshToken){
        //토큰 만료 시간 추출
        const tokenPayload = parseJwt(accessToken);
        if (tokenPayload && tokenPayload.exp){
            tokenExpiryTime = tokenPayload.exp*1000;//만료 시간을 밀리초로 변환
            startTokenExpiryWatcher();
        }
    }
})

//Jwt에서 만료 시간 파싱
function parseJwt(token: string){
    try{
        return JSON.parse(atob(token.split('.')[1]));
    }catch(e){
        return null;
    }
}
//토큰 만료 감시기
function startTokenExpiryWatcher(){
    if(tokenExpiryTime){
        const currentTime = Date.now();
        const timeUntilExpiry = tokenExpiryTime-currentTime;
        const alertTime = 10 * 60 * 1000;//10분

        // 10분 전에 알림을 띄우도록 타이머 설정
        if(timeUntilExpiry > alertTime){
            setTimeout(()=>{
                showAlert.value = true;
            }, timeUntilExpiry - alertTime);
        } else{
            showAlert.value = true;
        }
    }
}
//토큰 갱신 함수
async function refreshToken() {
    const refreshToken = localStorage.getItem('refresh_token');
    if (!refreshToken){
        console.error('No refresh token found');
        return;
    }
    try {
        const response = await fetch('http://localhost:8080/api/auth/refresh',{
            method:'POST',
            headers:{
                'Content-Type':'application/json',
            },
            body: JSON.stringify({refreshToken}),
        });
        const data = await response.json();

        if(response.ok){
            // 새로운 토큰 저장 및 만료 시간 업데이트
            localStorage.setItem('access_token', data.accessToken);
            tokenExpiryTime = parseJwt(data.accessToken).exp*1000;
            startTokenExpiryWatcher();
            showAlert.value = false;
            console.log('토큰 갱신 완료');
        } else{
            console.error('토큰 갱신 실패:', data.message);
        }
    }catch(error){
        console.error('토큰 갱신 중 오류 발생:', error);
    }
}


</script>
