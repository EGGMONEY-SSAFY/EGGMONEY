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
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="flex items-center mt-3 text-lg"><IconSetting class="ml-4 size-6" />&nbsp; 금융</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="goDeposit">
      <div class="mx-8 my-4">
        <p>예금 상품 조회</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer" @click="goSavings">
      <div class="mx-8 my-4">
        <p>적금 상품 조회</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between mb-3 cursor-pointer" @click="goLoan">
      <div class="mx-8 my-4">
        <p>대출</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
