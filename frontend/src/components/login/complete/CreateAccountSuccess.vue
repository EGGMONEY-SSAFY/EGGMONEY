<template>
  <div
    class="flex flex-col items-center justify-center bg-yellow-50 min-h-screen max-w-[393px] mx-auto"
  >
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
    // router.push("/pinpad")
  }, 1500)
})
onUnmounted(() => {
  finStore.isYellowPage = false
})
</script>
<style scoped></style>
