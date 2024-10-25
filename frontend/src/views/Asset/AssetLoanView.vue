<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { onMounted, ref, watch } from "vue"
import { useVariableStore } from "@/stores/variable"
import BoxLoan from "@/components/box/BoxLoan.vue"
import type { Loan, LoanLog } from "@/stores/fin"
import BoxLoanLogs from "@/components/box/BoxLoanLogs.vue"
import { useRoute } from "vue-router"

const varStore = useVariableStore()
const userLoan = ref<Loan | null>(null)

const loanHistory = ref<LoanLog[] | null>(null)
const route = useRoute()

const finStore = useFinStore()

onMounted(async () => {
  varStore.setTitle("대출 상세보기")
  await finStore.getUserLoan(Number(route.params.loanId))
  if (finStore.loan != null) {
    if (typeof finStore.loan === "object" && "balance" in finStore.loan) {
      userLoan.value = finStore.loan
    }
  }
  if (route.params.loanId) {
    await finStore.getUserLoanLogs(Number(route.params.loanId))
    loanHistory.value = finStore.loanLogs
  }
})
</script>
<template>
  <div v-if="userLoan" class="pt-6">
    <BoxLoan :loan="userLoan" />
    <BoxLoanLogs :history="loanHistory" />
  </div>
  <div v-else class="mt-20">
    <div class="grid justify-center">
      <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
    </div>
    <h1 class="text-xl text-center pt-10 font-bold">현재 진행중인 대출이 없습니다 ❕</h1>
  </div>
</template>
