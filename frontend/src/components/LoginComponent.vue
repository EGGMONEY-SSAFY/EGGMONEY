
<template>
<a id="kakao-login-btn" @click="redirectToLogin">

  <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
    alt="카카오 로그인 버튼" />
</a>

</template>


<script setup lang="ts" >
import {ref, onMounted} from 'vue';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';
import {  useRouter } from 'vue-router';
declare const Kakao: any;
const tokenResult = ref<string | null>(null);
  function redirectToLogin() {
  
  window.location.href = 'http://localhost:8080/kakao/login'; // 백엔드 로그인 URL
}
interface ResponseApi<T>{
  success: boolean;
  data: T | null;
  message: string;
}

interface TokenResponse{
  accessToken: string;
  refreshToken: string;
  redirectUrl: string;
}

const authStore = useAuthStore();
const router = useRouter();


function clearUrlParams() {
  const url = new URL(window.location.href);
  url.searchParams.delete('code');
  window.history.replaceState({}, '', url.toString());
}
onMounted(()=>{
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    console.log(code)
    if (code) {
        
        axios
        .get<ResponseApi<TokenResponse>>(`http://localhost:8080/kakao/callback`,{
          params:{code}
        })
    .then((response) => {
        console.log('Response Data:', response.data);
       
        if(response.data.success && response.data.data){
          const {accessToken, refreshToken, redirectUrl} = response.data.data;
          authStore.setTokens(accessToken, refreshToken);
          console.log(authStore)
          clearUrlParams();
          console.log(redirectUrl)
          router.push("/");
        }
        
       
    })
    .catch(error => {
        console.error('Error:', error);  // 에러 처리
    });
    }
})

</script>


<!--
 @click="loginWithKakao"> 
 <p id="token-result">{{ tokenResult }}</p>
<button @click="requestUserInfo" v-if="tokenResult" class="api-btn">사용자 정보 가져오기</button> 
// 백엔드의 카카오 로그인 URL로 리다이렉트
// 코드를 Spring Boot 백엔드로 전송
// Kakao.Auth.setAccessToken(response.data.access_token);    
//   function loginWithKakao() {
//     Kakao.Auth.authorize({
//       redirectUri: 'http://localhost:5173/login',
//     });
//   }
//   function requestUserInfo() {
//   const token = Kakao.Auth.getAccessToken();
//   if (token) {
//     Kakao.API.request({
//       url: '/v2/user/me',
//     })
//       .then((res: any) => {
//         alert('User info: ' + JSON.stringify(res));
//         console.log(JSON.stringify(res))
//       })
//       .catch((err: any) => {
//         alert('Failed to request user information: ' + JSON.stringify(err));
//       });
//   } else {
//     alert('No access token available');
//   }
  
// }
//   // 아래는 데모를 위한 UI 코드입니다.
//   function displayToken() {
//   Kakao.Auth.getStatusInfo(function(statusObj: { status: string }) {
//     if (statusObj.status === 'connected') {
//       tokenResult.value = 'login success, token: ' + Kakao.Auth.getAccessToken();
//     }
//   });
//     }
//   //}

//   function getCookie(name: string): string | null {
//     const parts = document.cookie.split(name + '=');
//     if (parts.length === 2) { return parts[1].split(';')[0]; }
//     return null;
//   } -->