<template>
  <div class="flex flex-col items-center justify-center bg-gray-100 rounded-lg">
    <div class="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
      <h2 class="text-center text-xl font-bold text-gray-800 mb-6">Login</h2>
      <button
        id="kakao-login-btn"
        @click="redirectToLogin"
        class="w-full bg-[#FAE100] hover:bg-[#FAE100] text-black font-bold py-3 px-4 rounded-full mb-4 flex items-center justify-center"
      >
        <img
          src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
          alt="카카오 로그인 버튼"
          class="w-6 h-6 mr-2"
        />
        카카오로 시작하기
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue"
import axios from "axios"
import { useAuthStore } from "@/stores/auth"
import { useRouter } from "vue-router"
function redirectToLogin() {
  // window.location.href = "/api/kakao/login" // 백엔드 로그인 URL
  axios
    .get("/api/kakao/login")
    .then(async (response) => {
      // 성공적으로 로그인 URL을 가져온 경우
      window.location.href = response.data
    })
    .catch((error) => {
      console.error("로그인 요청 중 오류 발생:", error)
    })
}

interface TokenResponse {
  accessToken: string
  refreshToken: string
  redirectUrl: string
}

const authStore = useAuthStore()
const router = useRouter()

function clearUrlParams() {
  const url = new URL(window.location.href)
  url.searchParams.delete("code")
  window.history.replaceState({}, "", url.toString())
}
onMounted(() => {
  const urlParams = new URLSearchParams(window.location.search)
  const code = urlParams.get("code")
  // console.log(code)
  if (code) {
    axios
      .get<TokenResponse>(`/api/kakao/callback`, {
        params: { code },
        withCredentials: true,
      })
      .then((response) => {
        // console.log("Response Data:", response.data)

        if (response.data) {
          const { accessToken, refreshToken, redirectUrl } = response.data
          authStore.setTokens(accessToken, refreshToken)
          // console.log(response.data)
          clearUrlParams()
          // console.log(redirectUrl)
          router.push(redirectUrl)
        }
      })
      .catch((error) => {
        console.error("Error:", error)
      })
  }
})
</script>
