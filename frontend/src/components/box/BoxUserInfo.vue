<script setup lang="ts">
const props = defineProps({
  userData: Object,
})

const currentBalance = props.userData?.["현재잔액"]
const availAmt = props.userData?.["투자가능금액"]
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="m-4 mb-2">
      <span>현재 잔액 : </span>
      <span class="font-bold">{{ currentBalance.toLocaleString() }} 알</span>
    </div>
    <div class="m-4 mt-2">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">{{ availAmt.toLocaleString() }} 알</span>
    </div>
  </div>
</template>
