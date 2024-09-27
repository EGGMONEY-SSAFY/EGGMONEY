<template>
  <div class="flex flex-col items-center justify-center h-screen" style="background-color: #fefce8">
    <h1 class="text-xl font-bold">회원가입 완료!</h1>
    <p class="text-center text-gray-700">
      회원이 되신 것을 축하합니다.
    </p>
    <img class="my-6" src="@/assets/common/완료 폭죽.png" alt="선물 이미지" />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()

onMounted(() => {
  setTimeout(() => {
    router.push("/assets")
  }, 1500)
})
</script>
