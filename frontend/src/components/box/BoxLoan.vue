<script setup lang="ts">
import { useUserStore } from "@/stores/user"
import { useFinStore, type Loan } from "@/stores/fin"
import { useRouter } from "vue-router"
import {  ref } from "vue"

const userStore = useUserStore()
const props = defineProps<{ loan: Loan | null }>()
const router = useRouter()
const finStore = useFinStore()

const isModalOpen = ref(false) // 모달 상태

const goLoanDetail = () => {
  router.push({ name: "AssetLoanListItemDetail", params: { loanId: props.loan?.loanId } })
}

// 모달을 여는 함수
const openModal = () => {
  isModalOpen.value = true
}

// 모달을 닫는 함수
const closeModal = () => {
  isModalOpen.value = false
}

// sendLoan 메소드
async function sendLoan(loanId: number | null) {
  if (loanId) {
    await finStore.sendLoan(loanId)
  }
  closeModal()
  window.location.reload()
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
  <div class="p-2 m-4 bg-white rounded-lg shadow">
    <div class="pb-4">
      <div class="flex justify-between m-3 text-sm">
        <h1
          class="p-2 px-3 font-semibold text-center text-main-color border-main-color border-2 rounded-xl text-wrap"
          role="button"
          tabindex="0"
        >
          대출
        </h1>
        <button
          class="my-auto text-base font-semibold text-white bg-main-color px-5 py-2 rounded-xl"
          @click="goLoanDetail"
          v-if="props.loan"
        >
          상세보기
        </button>
      </div>
      <div class="text-center">
        <h1 class="mt-8 text-lg underline underline-offset-4">
          적용금리
          <span class="font-semibold text-main-color">
            연 {{ props.loan?.loanRate?.toFixed(2) }} %
          </span>
        </h1>
        <h1 style="font-size: 25px" class="my-3 font-semibold">
          잔금 : {{ props.loan?.balance?.toLocaleString() }}<span class="font-bold ms-1">원</span>
        </h1>
        <h1 v-if="props.loan?.expirationDate" class="text-sm">
          만기일 {{ formatExpireDate(props.loan?.expirationDate) }}
        </h1>
      </div>
      <div
        class="flex justify-around px-5 mt-8 text-justify"
        v-if="userStore.user?.role !== '부모'"
      >
        <!-- <button class="px-5 py-2 font-semibold text-white bg-red-500 rounded-xl">해지하기</button> -->
        <button
          class="px-5 py-2 font-semibold text-white bg-lime-700 rounded-xl"
          @click="openModal"
        >
          상환하기
        </button>
      </div>
    </div>
    <!-- 모달 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50"
    >
      <div class="p-6 bg-white rounded-lg shadow-lg w-80">
        <h2 class="mb-4 text-lg font-bold">정말 상환하시겠습니까?</h2>
        <div class="mb-5">
          <p class="mb-1 text-sm text-red-500">* 상환 후에는 취소가 불가능합니다.</p>
          <p class="text-sm text-red-500">계속 진행하시겠습니까?</p>
        </div>
        <div class="flex justify-end">
          <button class="px-4 py-2 mr-2 text-white bg-gray-500 rounded" @click="closeModal">
            취소
          </button>
          <button
            class="px-4 py-2 text-white bg-blue-500 rounded"
            @click="sendLoan(props.loan?.loanId)"
            v-if="props.loan?.loanId"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
