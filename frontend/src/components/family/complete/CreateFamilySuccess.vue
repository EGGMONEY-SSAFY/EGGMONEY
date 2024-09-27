<template>
  <div class="flex flex-col items-center justify-center bg-yellow-50 main-container">
    <h1 class="text-xl font-bold mb-8">가족 생성 완료!</h1>
    <p class="text-center text-gray-700">
      가족 생성을 완료했어요. <br />이제 자유롭게 가족을 초대할 수 있어요.
    </p>
    <img class="my-6 w-40" src="@/assets/common/완료 상자.png" alt="선물 이미지" />
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
    router.push("/family")
  }, 1500)
})

onUnmounted(() => {
  finStore.isYellowPage = false
})
</script>

<style scoped>
.main-container {
  width: 100%;
  height: 100%;
  min-height: 101vh; /* 전체 높이를 101vh로 설정 */
}

@media (min-width: 393px) {
  .main-container {
    max-width: 393px;
  }
}
</style>
