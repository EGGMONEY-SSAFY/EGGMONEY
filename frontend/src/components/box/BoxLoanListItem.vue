<script setup lang="ts">
import type { User } from "@/stores/user"
import type { Loan } from "@/stores/fin"
import { useRouter } from "vue-router"
import IconRightArrow from "../icons/IconRightArrow.vue"
const props = defineProps<{ user: User; loan: Loan }>()
const router = useRouter()

function goLoanDetail(loanId: number) {
  router.push({ name: "AssetLoanListItem", params: { loanId: loanId } })
}
</script>

<template>
  <div class="grid gap-2 p-2 px-4 pb-5 m-4 bg-white rounded-lg shadow">
    <div class="mt-3">
      <h1
        class="inline-flex px-3 py-1 text-white bg-red-500 rounded-lg"
        v-if="props.loan.loanStatus === 'REFUSAL'"
      >
        거절
      </h1>
      <div class="flex justify-between" v-if="props.loan.loanStatus === 'APPROVAL'">
        <h1 class="inline-flex px-3 py-1 my-auto text-white bg-green-700 rounded-lg">승낙</h1>
        <div
          class="grid grid-flow-col p-1 border-2 border-gray-500 rounded-lg"
          role="button"
          tabindex="0"
          v-if="loan.loanId"
          @click="goLoanDetail(props.loan.loanId)"
        >
          <h1 class="px-1 my-auto text-sm font-semibold">상세보기</h1>
          <IconRightArrow class="size-6" />
        </div>
      </div>
      <h1
        class="inline-flex px-3 py-1 text-white rounded-lg bg-main-color"
        v-if="props.loan.loanStatus === 'PROGRESS'"
      >
        진행중
      </h1>
      <h1
        class="inline-flex px-3 py-1 text-white bg-gray-500 rounded-lg"
        v-if="props.loan.loanStatus === 'EXPIRED'"
      >
        완납
      </h1>
    </div>
    <div class="flex justify-between mt-2 pe-5 text-end">
      <h1>신청 금액</h1>
      <h1 class="text-wrap">{{ props.loan.loanAmount }}</h1>
    </div>
    <div class="flex justify-between pe-5 text-end">
      <h1>대출 잔액</h1>
      <h1 class="text-wrap">{{ props.loan.balance }}</h1>
    </div>
    <div class="flex justify-between pe-5 text-end">
      <h1>대출 이율</h1>
      <h1 class="text-wrap">{{ props.loan.loanRate?.toFixed(2) }}</h1>
    </div>
    <div class="flex justify-between pe-5 text-end">
      <h1>신청 사유</h1>
      <h1 class="text-wrap">{{ props.loan.loanReason }}</h1>
    </div>
    <div class="flex justify-between pe-5 text-end" v-if="props.loan.loanStatus === 'REFUSAL'">
      <h1>거절 사유</h1>
      <h1 class="text-wrap">{{ props.loan.refuseReason }}</h1>
    </div>
  </div>
</template>
