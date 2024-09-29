<script setup lang="ts">
import IconRightArrow from "../icons/IconRightArrow.vue"
import IconSetting from "../icons/IconAllFinance.vue"
import { useRouter } from "vue-router"

const router = useRouter()
function goDeposit() {
  router.push({ name: "FinDepositView" })
}
function goSavings() {
  router.push({ name: "FinSavingsView" })
}
function goLoan() {
  router.push({ name: "FinLoanView" })
}
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="text-lg flex items-center mt-3"><IconSetting class="size-6 ml-4" />&nbsp; 금융</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="goDeposit">
      <div class="my-4 mx-8">
        <p>예금 상품 조회</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer" @click="goSavings">
      <div class="my-4 mx-8">
        <p>적금 상품 조회</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between mb-3 cursor-pointer" @click="goLoan">
      <div class="my-4 mx-8">
        <p>대출</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
