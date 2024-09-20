
<template>
<a id="kakao-login-btn" @click="redirectToLogin">
 <!-- @click="loginWithKakao"> -->
  <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
    alt="카카오 로그인 버튼" />
</a>
<!-- <p id="token-result">{{ tokenResult }}</p>
<button @click="requestUserInfo" v-if="tokenResult" class="api-btn">사용자 정보 가져오기</button> -->
</template>


<script setup lang="ts" >
import {ref, onMounted} from 'vue';
import axios from 'axios';

declare const Kakao: any;
const tokenResult = ref<string | null>(null);
  function redirectToLogin() {
  // 백엔드의 카카오 로그인 URL로 리다이렉트
  window.location.href = 'http://localhost:8080/kakao/login'; // 백엔드 로그인 URL
}
interface KakaoTokenResponse {
  access_token: string;
  token_type: string;
  refresh_token: string;
  expires_in: number;
  scope: string;
  refresh_token_expires_in: number;
}
function clearUrlParams() {
  const url = new URL(window.location.href);
  url.searchParams.delete('code');
  window.history.replaceState({}, '', url.toString());
}
onMounted(()=>{
    // Kakao.init('4a3017157e71beff602c22df21edc91f');
    
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    console.log(code)
    if (code) {
        // 코드를 Spring Boot 백엔드로 전송
        axios
        .get<KakaoTokenResponse>(`http://localhost:8080/kakao/callback`)
    .then((response) => {
        console.log('Response Data:', response.data);
        Kakao.Auth.setAccessToken(response.data.access_token);
        clearUrlParams();
    })
    .catch(error => {
        console.error('Error:', error);  // 에러 처리
    });
    }
})
    
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
//   }
</script>