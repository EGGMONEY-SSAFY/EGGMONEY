<script setup lang="ts">
import SimplePinPadComponent from "@/components/login/SimplePinPadComponent.vue"
import { useFinStore } from "@/stores/fin"
import { onMounted, onUnmounted, ref } from "vue"
import { useRouter } from "vue-router"
import { useUserStore } from "@/stores/user"

const props = defineProps({
  check: String,
})

const router = useRouter()
const previousRoute = router.options.history.state.back
onMounted(() => {
  finStore.isTab = true
})
onUnmounted(() => {
  finStore.isTab = false
})

const finStore = useFinStore()
const showFailModal = ref(false)
const remainingTime = ref(5)
const userStore = useUserStore()

const handleSuccess = () => {
  // 비밀번호 검증 성공 시 처리 로직
  if (previousRoute?.toString().includes("deposit")) {

    finStore.postUserDeposit()
  } else if (previousRoute?.toString().includes("savings")) {

    finStore.postUserSavings()
  } else if (previousRoute?.toString().includes("loan") && userStore.user?.role === "자녀") {

    finStore.postUserLoan()
  } else if (previousRoute?.toString().includes("loan") && userStore.user?.role === "부모") {
    finStore.sendfinLoanJudge()
  }

}

const handleFail = () => {

  showFailModal.value = true // 모달을 띄움

  // 5초 후 비밀번호 재설정 페이지로 이동
  const countdownInterval = setInterval(() => {
    remainingTime.value--
    if (remainingTime.value <= 0) {
      clearInterval(countdownInterval) // 카운트다운 종료
      router.push({ name: "WonAuthView" }) // 1원인증 페이지로 넘기기()
    }
  }, 1000)
}
</script>
<template>
  <div class="fixed inset-0 z-20 mt-20">
  
    <SimplePinPadComponent
      @pin-success="handleSuccess"
      @pinFail="handleFail"
    ></SimplePinPadComponent>
  </div>
  <div
    v-if="showFailModal"
    class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
  >
    <div class="max-w-sm p-6 text-center bg-white rounded-lg">
      <p class="text-lg font-semibold text-gray-900">비밀번호 인증 실패</p>
      <p>{{ remainingTime }}초 후 비밀번호 재설정 페이지로 이동합니다.</p>
    </div>
  </div>
</template>
