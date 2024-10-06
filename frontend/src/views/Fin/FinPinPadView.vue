<script setup lang="ts">
import SimplePinPadComponent from "@/components/login/SimplePinPadComponent.vue"
import { useFinStore } from "@/stores/fin"
import { onMounted, ref } from "vue"
import { useRoute, useRouter, type HistoryState, type RouterHistory } from "vue-router"
import FinSuccessLoan from "./FinSuccessLoanView.vue"

const props = defineProps({
  check: String,
})

const router = useRouter()
const previousRoute = router.options.history.state.back
onMounted(() => {
  console.log(previousRoute)
})

const finStore = useFinStore()

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
  } else if (previousRoute?.toString().includes("loan")) {
    console.log("대출 생성 요청")
    finStore.postUserLoan()
    router.push({ name: "FinSuccessLoanView" })
  }
  console.log("비밀번호 검증 성공")
}

const handleFail = () => {
  // 비밀번호 검증 실패 시 처리 로직
  console.log("비밀번호 검증 실패")
  // 실패 횟수에 따라 추가 로직 처리 가능
}
</script>
<template>
  <!-- 예금, 적금, 대출 모두 이용할 수 있도록 해보기 만약 예금이라면
    finStore에 저장된 예금 정보를 가지고 요청보내는 axios요청 함수 실행-->
  <SimplePinPadComponent @pin-success="handleSuccess" @pinFail="handleFail"></SimplePinPadComponent>
</template>
