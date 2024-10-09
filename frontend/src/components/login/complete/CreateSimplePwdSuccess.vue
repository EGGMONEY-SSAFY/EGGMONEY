<template>
  <div class="flex flex-col items-center justify-center bg-yellow-50 main-container1">
    <h1 class="text-xl font-bold mb-8">간편 비밀번호</h1>
    <p class="text-center text-gray-700">설정이 완료되었습니다</p>
    <img class="my-6 w-36" src="@/assets/common/완료 폭죽.png" alt="선물 이미지" />
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
  setTimeout(() => {
    router.push("/asset")
  }, 1500)
})
onUnmounted(() => {
  finStore.isYellowPage = false
})
</script>

<style scoped>
.main-container1 {
  width: 100%;
  height: 100%;
  min-height: 85vh;
}

@media (min-width: 393px) {
  .main-container1 {
    max-width: 393px;
  }
}
</style>
