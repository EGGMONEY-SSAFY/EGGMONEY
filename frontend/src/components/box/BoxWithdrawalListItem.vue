<script setup lang="ts">
import { useUserStore, type User } from "@/stores/user"
import { useAssetStore, type Withdrawal } from "@/stores/asset"
import { ref } from "vue"
import { useRouter } from "vue-router"

const props = defineProps<{ user: User; withdrawal: Withdrawal }>()
const router = useRouter()
const assetStore = useAssetStore()
const userStore = useUserStore()
const isModalOpen = ref(false)

// 모달을 여는 함수
function openModal() {
  isModalOpen.value = true
}

// 모달을 닫는 함수
function closeModal() {
  isModalOpen.value = false
}

async function sendWithJudge(withdrawalId: number, judge: string, userId: number) {
  //   assetStore.
  if (judge && userId && withdrawalId) {
    await assetStore.sendWithdrawalJudge(withdrawalId, judge, userId)
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
  return `${year}년 ${month}월 ${day}일`
}
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow grid p-2 gap-2 px-4 pb-5" v-if="userStore.user">
    <div class="flex mt-3 justify-between">
      <div class="">
        <h1
          class="bg-red-500 rounded-lg text-white px-3 py-1 inline-flex my-auto"
          v-if="withdrawal.type === 'REFUSAL'"
        >
          거절
        </h1>
        <h1
          class="bg-green-700 rounded-lg text-white px-3 py-1 inline-flex my-auto"
          v-if="withdrawal.type === 'APPROVAL'"
        >
          승낙
        </h1>
        <h1
          class="bg-main-color rounded-lg text-white px-3 py-1 inline-flex my-auto"
          v-if="withdrawal.type === 'PROGRESS'"
        >
          진행중
        </h1>
      </div>
      <div class="flex justify-between" v-if="withdrawal.type === 'PROGRESS'">
        <h1
          class="rounded-lg text-main-color font-bold px-3 py-1 inline-flex border-main-color border-2"
          role="button"
          tabindex="0"
          v-if="userStore.user.role === '부모'"
          @click="openModal"
        >
          심사하기
        </h1>
      </div>
    </div>
    <div class="flex justify-between pe-5 text-end mt-2">
      <h1 class="font-bold">출금액</h1>
      <h1 class="text-wrap">{{ props.withdrawal.withdrawalPrice.toLocaleString() }}</h1>
    </div>
    <div class="flex justify-between pe-5 text-end mt-2">
      <h1 class="font-bold">신청일</h1>
      <h1 class="text-wrap">{{ formatExpireDate(props.withdrawal.createdAt) }}</h1>
    </div>
    <div
      class="flex justify-between pe-5 text-end mt-2"
      v-if="props.withdrawal.updatedAt && props.withdrawal.type != 'PROGRESS'"
    >
      <h1 v-if="props.withdrawal.type === 'REFUSAL'" class="font-bold">거절일</h1>
      <h1 v-if="props.withdrawal.type === 'APPROVAL'" class="font-bold">승인일</h1>
      <h1 class="text-wrap">{{ formatExpireDate(props.withdrawal.updatedAt) }}</h1>
    </div>
    <!-- 모달 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50 z-50"
    >
      <div class="bg-white p-6 rounded-lg shadow-lg w-80" v-if="userStore.user?.userId">
        <h2 class="text-base font-bold mb-4">출금 요청을 승낙하시겠습니까?</h2>
        <div class="mb-5">
          <p class="text-sm text-red-500 mb-1 text-wrap">* 실물 계좌에서 돈이 빠져나갑니다</p>
        </div>
        <div class="flex justify-end">
          <button
            class="bg-gray-500 text-white px-4 py-2 rounded mr-2"
            @click="sendWithJudge(props.withdrawal.withdrawalId, '거절', userStore.user.userId)"
          >
            거절
          </button>
          <button
            class="bg-blue-500 text-white px-4 py-2 rounded"
            @click="sendWithJudge(props.withdrawal.withdrawalId, '승인', userStore.user.userId)"
          >
            승낙
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
