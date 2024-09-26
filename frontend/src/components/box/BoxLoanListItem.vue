<script setup lang="ts">
import type { Loan } from "@/stores/fin"
import type { User } from "@/stores/user"
import { onMounted } from "vue"
import { useRouter } from "vue-router"
import IconRightArrow from "../icons/IconRightArrow.vue"

const props = defineProps<{ user: User; loan: Loan }>()
const router = useRouter()

function goLoanDetail(loanId: number) {
  router.push({ name: "AssetLoanListItem", params: { loanId: loanId } })
}
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow grid p-2 gap-2 px-4 pb-5">
    <div class="mt-3">
      <h1
        class="bg-red-500 rounded-lg text-white px-3 py-1 inline-flex"
        v-if="loan.loanStatus === 'REFUSAL'"
      >
        거절
      </h1>
      <div class="flex justify-between" v-if="loan.loanStatus === 'APPROVAL'">
        <h1 class="bg-green-700 rounded-lg text-white px-3 py-1 inline-flex my-auto">승낙</h1>
        <div
          class="border-gray-500 p-1 rounded-lg border-2 grid grid-flow-col"
          role="button"
          tabindex="0"
          @click="goLoanDetail(loan.loanId)"
        >
          <h1 class="text-sm px-1 font-semibold my-auto">상세보기</h1>
          <RouterLink to="/">
            <IconRightArrow class="size-6" />
          </RouterLink>
        </div>
      </div>
      <h1
        class="bg-main-color rounded-lg text-white px-3 py-1 inline-flex"
        v-if="loan.loanStatus === 'PROGRESS'"
      >
        진행중
      </h1>
      <h1
        class="bg-gray-500 rounded-lg text-white px-3 py-1 inline-flex"
        v-if="loan.loanStatus === 'EXPIRED'"
      >
        완납
      </h1>
    </div>
    <div class="flex justify-between pe-5 text-end mt-2">
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
