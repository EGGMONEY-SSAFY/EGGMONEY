<script setup lang="ts">
import { useUserStore } from "@/stores/user"
import type { Loan } from "@/stores/fin"
import { useRouter } from "vue-router"

const userStore = useUserStore()
const props = defineProps<{ loan: Loan | null }>()
const router = useRouter()

const goLoanDetail = () => {
  router.push({ name: "AssetLoanListItemDetail", params: { loanId: props.loan?.loanId } })
}

const formatExpireDate = (expireDate?: string) => {
  if (!expireDate) return "" // expireDate가 없는 경우 빈 문자열 반환
  const date = new Date(expireDate)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, "0") // 월은 0부터 시작하므로 +1
  const day = String(date.getDate()).padStart(2, "0")
  return `${year}. ${month}. ${day}`
}
</script>

<template>
  <div class="bg-white m-4 p-2 rounded-lg shadow">
    <div class="pb-4">
      <div class="m-3 flex justify-between text-sm">
        <h1
          class="font-semibold text-white bg-main-color rounded-xl text-center p-2 px-3 text-wrap"
          role="button"
          tabindex="0"
        >
          대출
        </h1>
        <button
          class="text-main-color font-semibold text-base my-auto"
          @click="goLoanDetail"
          v-if="props.loan"
        >
          상세보기
        </button>
      </div>
      <div class="text-center">
        <h1 class="mt-8 text-lg underline underline-offset-4">
          적용금리
          <span class="text-main-color font-semibold">
            연 {{ props.loan?.loanRate?.toFixed(2) }} %
          </span>
        </h1>
        <h1 style="font-size: 25px" class="my-3 font-semibold">
          잔금 : {{ props.loan?.balance?.toLocaleString() }}<span class="ms-1 font-bold">원</span>
        </h1>
        <h1 v-if="props.loan?.expirationDate" class="text-sm">
          만기일 {{ formatExpireDate(props.loan?.expirationDate) }}
        </h1>
      </div>
      <div
        class="flex text-justify justify-around mt-8 px-5"
        v-if="userStore.user?.role !== '자녀'"
      >
        <!-- <button class="bg-red-500 px-5 py-2 rounded-xl text-white font-semibold">해지하기</button> -->
        <button class="bg-lime-700 px-5 py-2 rounded-xl text-white font-semibold">상환하기</button>
      </div>
    </div>
  </div>
</template>
