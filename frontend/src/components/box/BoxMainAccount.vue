<script setup lang="ts">
import { useAssetStore } from "@/stores/asset";
import { useUserStore } from "@/stores/user"
import type { User } from "@/stores/user";
import { ref, watch } from "vue";

const assetStore = useAssetStore()
const props = defineProps<{ user: User; balance: Number | null }>()
async function createWithdrawal (userId:number, price:number){
  await assetStore.createWithdrawal(userId, price)
  window.location.reload();
}
const userStore = useUserStore()

const isModalOpen = ref<boolean>(false); // 모달 창 상태
const withdrawalAmount = ref<number | null>(null); // 출금 금액
const errorMessage = ref<string>("출금 금액을 입력해주세요."); // 에러 메시지 상태

// 모달을 여는 함수
function openModal() {
  isModalOpen.value = true;
}

// 모달을 닫는 함수
function closeModal() {
  isModalOpen.value = false;
  withdrawalAmount.value = null; // 출금 금액 초기화
}

// 출금 금액을 감시하면서 에러 메시지 업데이트
watch(withdrawalAmount, (newVal) => {
  if (newVal === null || newVal <= 0) {
    errorMessage.value = "출금 금액을 입력해주세요.";
  } else {
    errorMessage.value = "";
  }
});

// 출금을 확인하는 함수
function confirmWithdrawal() {
  if (withdrawalAmount.value !== null && withdrawalAmount.value > 0) {
    // 출금 처리 로직
    assetStore.createWithdrawal(userStore.user?.userId || 0, withdrawalAmount.value);
    closeModal();
  }
}
</script>

<template>
  <div class="bg-white m-4 p-2 rounded-lg shadow">
    <div class="">
      <div class="m-3 flex justify-between text-sm">
        <h1 class="font-semibold text-white bg-main-color rounded-xl text-center p-2 px-3">
          입출금통장
        </h1>
        <div class="flex my-auto text-sm gap-2 font-semibold">
          <h1>{{ user.bank }}</h1>
          <h1>{{ props.user.realAccount }}</h1>
        </div>
      </div>
      <div class="m-4 font-bold">
        <h1 class="text-center mt-5" style="font-size: 35px">
          {{ props.balance?.toLocaleString() }}<span class="ms-1 font-bold">원</span>
        </h1>
      </div>
    </div>
    <div
      class="flex text-justify justify-around mt-8 px-5 mb-5"
      v-if="userStore.user?.role === '부모'"
    >
    <button class="bg-red-500 px-5 py-2 rounded-xl text-white font-semibold" @click="openModal">
      출금요청
    </button>
    </div>
        <!-- 출금 모달 -->
        <div v-if="isModalOpen" class="fixed inset-0 flex items-center justify-center z-50 bg-gray-900 bg-opacity-50">
      <div class="bg-white p-6 rounded-lg shadow-lg w-80">
        <h2 class="text-lg font-bold mb-4">얼마를 출금하시겠습니까?</h2>
        <input
          v-model="withdrawalAmount"
          type="number"
          step="100"
          class="border p-2 w-full mb-4"
          placeholder="출금 금액 입력"
        />
        <p v-if="errorMessage" class="text-red-500 text-sm mb-4">{{ errorMessage }}</p>
        <div class="flex justify-end">
          <button class="bg-gray-500 text-white px-4 py-2 rounded mr-2" @click="closeModal">취소</button>
          <button class="bg-blue-500 text-white px-4 py-2 rounded" @click="confirmWithdrawal">출금</button>
        </div>
      </div>
    </div>
  </div>
</template>
