<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { onMounted, ref, watch } from "vue"
import { useVariableStore } from "@/stores/variable"
import type { Loan } from "@/stores/fin"
import { useRoute } from "vue-router"

const varStore = useVariableStore()
const userLoan = ref<Loan | null>(null)
const route = useRoute()

const finStore = useFinStore()

const formatExpireDate = (expireDate?: string) => {
  if (!expireDate) return "" // expireDate가 없는 경우 빈 문자열 반환
  const date = new Date(expireDate)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, "0") // 월은 0부터 시작하므로 +1
  const day = String(date.getDate()).padStart(2, "0")
  return `${year}년 ${month}월 ${day}일`
}

onMounted(async () => {
  varStore.setTitle("대출 상세내역")
  await finStore.getUserLoan(Number(route.params.loanId))
  if (finStore.loan != null) {
    if (typeof finStore.loan === "object" && "balance" in finStore.loan) {
      userLoan.value = finStore.loan
    }
  }
})
</script>
<template>
  <div class="mx-5 pt-14" v-if="userLoan">
    <div class="bg-white rounded-lg shadow justify-between px-4 py-3 gap-3" v-if="finStore.loan">
      <div class="grid gap-3">
        <h1 class="text-xl text-center font-semibold">대출 상세내역</h1>
        <hr />
        <div class="grid gap-2 pe-5">
          <div class="grid grid-flow-col grid-cols-2">
            <h1>상품명</h1>
            <h1 class="text-end">대출</h1>
          </div>
          <div class="flex justify-between" v-if="userLoan.createdAt">
            <h1>실행일</h1>
            <h1 class="text-end">{{ formatExpireDate(userLoan.createdAt) }}</h1>
          </div>
          <div class="flex justify-between" v-if="userLoan.expirationDate">
            <h1>만기일</h1>
            <h1 class="text-end">{{ formatExpireDate(userLoan.expirationDate) }}</h1>
          </div>
          <div class="flex justify-between">
            <h1>대출액</h1>
            <h1 class="text-end">{{ userLoan.loanAmount?.toLocaleString() }}</h1>
          </div>
          <div class="flex justify-between">
            <h1>잔액</h1>
            <h1 class="text-end">{{ userLoan.balance.toLocaleString() }}</h1>
          </div>
          <div class="flex justify-between">
            <h1>적용금리</h1>
            <h1 class="text-end">{{ userLoan.loanRate?.toFixed(2) }}</h1>
          </div>
          <div class="flex justify-between">
            <h1>상환방식</h1>
            <h1 class="text-end" v-if="userLoan.loanType === 'LUMPSUM'">원리금 균등상환</h1>
            <h1 class="text-end" v-else>만기 일시상환</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
