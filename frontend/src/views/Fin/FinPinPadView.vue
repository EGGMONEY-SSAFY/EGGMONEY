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
  console.log(previousRoute)
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
    console.log("예금생성 요청")
    router.push({ name: "FinSuccessView" })
    finStore.postUserDeposit()
  } else if (previousRoute?.toString().includes("savings")) {
    console.log("적금이 포함된 주소")
    router.push({ name: "FinSuccessView" })
    finStore.postUserSavings()
  } else if (previousRoute?.toString().includes("loan") && userStore.user?.role === "자녀") {
    console.log("대출 생성 요청")
    finStore.postUserLoan()
    router.push({ name: "FinSuccessLoanView" })
  } else if (previousRoute?.toString().includes("loan") && userStore.user?.role === "부모") {
    finStore.sendfinLoanJudge()
    router.push({ name: "FinSuccessLoanView" })
  }
  console.log("비밀번호 검증 성공")
}

const handleFail = () => {
  // 비밀번호 검증 실패 시 처리 로직
  console.log("비밀번호 검증 실패")

  showFailModal.value = true // 모달을 띄움

  // 5초 후 비밀번호 재설정 페이지로 이동
  // TODO: 비밀번호 재설정 페이지 name 알아내서 변경하기.
  const countdownInterval = setInterval(() => {
    remainingTime.value--
    if (remainingTime.value <= 0) {
      clearInterval(countdownInterval) // 카운트다운 종료
      router.push({ name: "PasswordResetView" }) // 비밀번호 재설정 페이지로 이동
    }
  }, 1000)
}
</script>
<template>
  <div class="mt-16">
    <SimplePinPadComponent
      @pin-success="handleSuccess"
      @pinFail="handleFail"
    ></SimplePinPadComponent>
  </div>
  <div
    v-if="showFailModal"
    class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75"
  >
    <div class="bg-white rounded-lg p-6 max-w-sm text-center">
      <p class="text-lg font-semibold text-gray-900">비밀번호 인증 실패</p>
      <p>{{ remainingTime }}초 후 비밀번호 재설정 페이지로 이동합니다.</p>
    </div>
  </div>
</template>
