<template>
  <div class="flex flex-col items-center justify-center bg-yellow-50 main-container2">
    <h1 class="text-xl font-bold mb-8">계좌 등록 성공!</h1>
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
    router.push("/pinpad")
  }, 1500)
})
onUnmounted(() => {
  finStore.isYellowPage = false
})
</script>
<style scoped>
.main-container2 {
  width: 90%;
  height: 100%;
  min-height: 98vh; /* 전체 높이를 101vh로 설정 */
}

@media (min-width: 393px) {
  .main-container2 {
    max-width: 393px;
  }
}
</style>
