<script setup lang="ts">
import { useUserStore } from "@/stores/user"
import { useFinStore, type Savings } from "@/stores/fin"
import type { User } from "@/stores/user"
import { useRouter } from "vue-router"
import { ref } from "vue"

const userStore = useUserStore()
const router = useRouter()
const finStore = useFinStore()
const isModalOpen = ref(false)

const props = defineProps<{ user: User; savings: Savings | null }>()

function goSavingsDetail() {
  router.push({ name: "AssetSavingsDetailView", params: { userId: props.user.userId } })
}

async function sendSavings(userId: Number) {
  await finStore.sendSavings(userId)
  closeModal()
  window.location.reload()
}

// 모달을 여는 함수
function openModal() {
  isModalOpen.value = true
}

// 모달을 닫는 함수
function closeModal() {
  isModalOpen.value = false
}

// 만기일 포맷팅 함수
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
          class="p-2 px-3 font-semibold text-center text-white bg-main-color rounded-xl text-wrap"
        >
          {{ props.savings?.productName }}
        </h1>
        <button class="my-auto text-base font-semibold text-main-color" @click="goSavingsDetail()">
          통장관리
        </button>
      </div>
      <div class="text-center">
        <h1 class="mt-8 text-lg underline underline-offset-4">
          적용금리
          <span class="font-semibold text-main-color">
            연 {{ props.savings?.savingsRate?.toFixed(2) }} %
          </span>
        </h1>
        <h1 style="font-size: 30px" class="my-3 font-semibold">
          {{ props.savings?.balance?.toLocaleString() }}<span class="font-bold ms-1">원</span>
        </h1>
        <h1 v-if="props.savings?.expireDate" class="text-sm">
          만기일 {{ formatExpireDate(props.savings?.expireDate) }}
        </h1>
      </div>
      <div
        class="flex justify-center gap-4 px-5 mt-8 text-justify"
        v-if="userStore.user?.role === '자녀'"
      >
        <button
          class="px-3 py-2 font-semibold text-white bg-lime-700 rounded-xl"
          @click="openModal"
        >
          납입하기
        </button>
      </div>
    </div>
    <!-- 모달 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50"
    >
      <div class="p-6 bg-white rounded-lg shadow-lg w-80" v-if="userStore.user?.userId">
        <h2 class="mb-4 text-base font-bold">정말 적금을 납입하시겠습니까?</h2>
        <div class="mb-5">
          <p class="mb-1 text-sm text-red-500">* 적금을 납입시 돈을</p>
          <p class="text-sm text-red-500">돌려받을 수 없습니다.</p>
        </div>
        <div class="flex justify-end">
          <button class="px-4 py-2 mr-2 text-white bg-gray-500 rounded" @click="closeModal">
            취소
          </button>
          <button
            class="px-4 py-2 text-white bg-blue-500 rounded"
            @click="sendSavings(props.user.userId)"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
