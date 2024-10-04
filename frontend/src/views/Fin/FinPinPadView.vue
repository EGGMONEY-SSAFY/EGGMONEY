<script setup lang="ts">
import SimplePinPadComponent from "@/components/login/SimplePinPadComponent.vue"
import { useFinStore } from "@/stores/fin";
const props = defineProps({
  check: String,
})
const finStore = useFinStore()

const handleSuccess = () => {
  // 비밀번호 검증 성공 시 처리 로직
  finStore.postUserLoan()  


  console.log('비밀번호 검증 성공');
};

const handleFail = () => {
  // 비밀번호 검증 실패 시 처리 로직
  console.log('비밀번호 검증 실패');
  // 실패 횟수에 따라 추가 로직 처리 가능
};

</script>
<template>
  <!-- 예금, 적금, 대출 모두 이용할 수 있도록 해보기 만약 예금이라면
    finStore에 저장된 예금 정보를 가지고 요청보내는 axios요청 함수 실행-->
  <SimplePinPadComponent @pin-success="handleSuccess" @pinFail="handleFail"></SimplePinPadComponent>
</template>
