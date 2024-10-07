<script setup lang="ts">
import { useAssetStore } from "@/stores/asset"
import { useUserStore } from "@/stores/user"
import type { User } from "@/stores/user"
import { ref, watch } from "vue"

const assetStore = useAssetStore()
const props = defineProps<{ user: User; balance: Number | null }>()
async function createWithdrawal(price: number) {
  await assetStore.createWithdrawal(price)
  window.location.reload()
}
const userStore = useUserStore()

const isModalOpen = ref<boolean>(false) // 모달 창 상태
const withdrawalAmount = ref<number | null>(null) // 출금 금액
const errorMessage = ref<string>("출금 금액을 입력해주세요.") // 에러 메시지 상태

// 모달을 여는 함수
function openModal() {
  isModalOpen.value = true
}

// 모달을 닫는 함수
function closeModal() {
  isModalOpen.value = false
  withdrawalAmount.value = null // 출금 금액 초기화
}

// 출금 금액을 감시하면서 에러 메시지 업데이트
watch(withdrawalAmount, (newVal) => {
  if (newVal === null || newVal <= 0) {
    errorMessage.value = "출금 금액을 입력해주세요."
  } else {
    errorMessage.value = ""
  }
})

// 출금을 확인하는 함수
function confirmWithdrawal() {
  if (withdrawalAmount.value !== null && withdrawalAmount.value > 0) {
    // 출금 처리 로직
    assetStore.createWithdrawal(withdrawalAmount.value)
    closeModal()
  }
}
</script>

<template>
  <div class="p-2 m-4 bg-white rounded-lg shadow">
    <div class="">
      <div class="flex justify-between m-3 text-sm">
        <h1 class="p-2 px-3 font-semibold text-center text-white bg-main-color rounded-xl">
          입출금통장
        </h1>
        <div class="flex gap-2 my-auto text-sm font-semibold">
          <h1>{{ user.bank }}</h1>
          <h1>{{ props.user.realAccount }}</h1>
        </div>
      </div>
      <div class="m-4 font-bold">
        <h1 class="mt-5 text-center" style="font-size: 35px">
          {{ props.balance?.toLocaleString() }}<span class="font-bold ms-1">원</span>
        </h1>
      </div>
    </div>
    <div
      class="flex justify-around px-5 mt-8 mb-5 text-justify"
      v-if="userStore.user?.role !== '부모'"
    >
      <button class="px-5 py-2 font-semibold text-white bg-red-500 rounded-xl" @click="openModal">
        출금요청
      </button>
    </div>
    <!-- 출금 모달 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50"
    >
      <div class="p-6 bg-white rounded-lg shadow-lg w-80">
        <h2 class="mb-4 text-lg font-bold">얼마를 출금하시겠습니까?</h2>
        <input
          v-model="withdrawalAmount"
          type="number"
          step="100"
          class="w-full p-2 mb-4 border"
          placeholder="출금 금액 입력"
        />
        <p v-if="errorMessage" class="mb-4 text-sm text-red-500">{{ errorMessage }}</p>
        <div class="flex justify-end">
          <button class="px-4 py-2 mr-2 text-white bg-gray-500 rounded" @click="closeModal">
            취소
          </button>
          <button class="px-4 py-2 text-white bg-blue-500 rounded" @click="confirmWithdrawal">
            출금
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
