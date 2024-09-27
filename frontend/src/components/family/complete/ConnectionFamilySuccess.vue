<template>
  <div class="flex flex-col items-center justify-center h-screen" style="background-color: #fefce8">
    <h1 class="text-xl font-bold">가족 연결 완료!</h1>
    <p class="text-center text-gray-700">이제 자유롭게 가족의 서비스를 이용할 수 있어요.</p>
    <img class="my-6" src="@/assets/common/완료 상자.png" alt="선물 이미지" />
  </div>
</template>

<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { onMounted, onUnmounted } from "vue"
import { useRouter } from "vue-router"
const finStore = useFinStore()
const router = useRouter()

onMounted(() => {
  finStore.isYellowPage = true
  // setTimeout(() => {
  //   router.push("/family")
  // }, 1500)
})
onUnmounted(() => {
  finStore.isYellowPage = false
})
</script>
