<template>
  <div class="flex flex-col items-center justify-center h-screen" style="background-color: #fefce8">
    <h1 class="text-xl font-bold">계좌 등록 성공!</h1>
    <img class="my-6" src="@/assets/common/완료 폭죽.png" alt="선물 이미지" />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()

onMounted(() => {
  setTimeout(() => {
    router.push("/pinpad")
  }, 1500)
})
</script>
