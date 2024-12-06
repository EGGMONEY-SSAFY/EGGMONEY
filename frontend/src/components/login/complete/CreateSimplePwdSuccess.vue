<template>
  <div
    class="flex flex-col items-center justify-center bg-yellow-50 min-h-screen max-w-[393px] mx-auto"
  >
    <h1 class="text-xl font-bold mb-8">회원가입 완료!</h1>
    <p class="text-center font-bold text-gray-700">회원이 되신 것을 축하합니다.</p>
    <img class="my-6 w-36" src="@/assets/common/완료 폭죽.png" alt="선물 이미지" />
  </div>
</template>

<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { useUserStore } from "@/stores/user"
import { onMounted, onUnmounted } from "vue"
import { useRouter } from "vue-router"

const finStore = useFinStore()
const router = useRouter()

onMounted(async () => {
  finStore.isYellowPage = true
  setTimeout(async () => {
    const userStore = useUserStore()
    if (userStore.user && userStore.familyId === null && userStore.user.role === "부모") {
      await router.push("/family")
    } else {
      await router.push("/asset")
    }
    window.location.reload()
  }, 1500)
})
onUnmounted(() => {
  finStore.isYellowPage = false
})
</script>

<style scoped></style>
