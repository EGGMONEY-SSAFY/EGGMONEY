
<template>
<a id="kakao-login-btn" @click="loginWithKakao">
  <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
    alt="카카오 로그인 버튼" />
</a>
<p id="token-result">{{ tokenResult }}</p>
<button @click="requestUserInfo" v-if="tokenResult" class="api-btn">사용자 정보 가져오기</button>
</template>


<script setup lang="ts" >
import {ref, onMounted} from 'vue';

declare const Kakao: any;
const tokenResult = ref<string | null>(null);

onMounted(()=>{
    Kakao.init('4a3017157e71beff602c22df21edc91f');
    displayToken();
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');

    if (code) {
        // 코드를 Spring Boot 백엔드로 전송
        fetch('http://localhost:8080/kakao-callback?code=' + code)
            .then(response => response.json())
            .then(data => {
                // 토큰 또는 사용자 데이터 처리
                console.log('Token Response:', data);
                tokenResult.value = 'login success, token: ' + data.access_token;
            })
            .catch(error => console.error('Error fetching token:', error));
    }
})
    
  function loginWithKakao() {
    Kakao.Auth.authorize({
      redirectUri: 'http://localhost:5173/login',
    });
  }
  function requestUserInfo() {
  Kakao.API.request({
    url: '/v2/user/me',
  })
    .then((res: any) => {
      alert('User info: ' + JSON.stringify(res));
    })
    .catch((err: any) => {
      alert('Failed to request user information: ' + JSON.stringify(err));
    });
}
  // 아래는 데모를 위한 UI 코드입니다.
  function displayToken() {
    const token = getCookie('authorize-access-token');

    if(token) {
      Kakao.Auth.setAccessToken(token);
      Kakao.Auth.getStatusInfo()
        .then(function(res: { status: string; }) {
          if (res.status === 'connected') {
            tokenResult.value
              = 'login success, token: ' + Kakao.Auth.getAccessToken();
          }
        })
        .catch(function(err: any) {
          Kakao.Auth.setAccessToken(null);
        });
    }
  }

  function getCookie(name: string): string | null {
    const parts = document.cookie.split(name + '=');
    if (parts.length === 2) { return parts[1].split(';')[0]; }
    return null;
  }
</script>