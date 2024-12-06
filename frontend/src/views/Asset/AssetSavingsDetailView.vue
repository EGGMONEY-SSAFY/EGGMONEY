<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { onMounted, ref } from "vue"
import { useVariableStore } from "@/stores/variable"
import type { Savings } from "@/stores/fin"
import { useRoute, useRouter } from "vue-router"
import { useUserStore } from "@/stores/user"

const varStore = useVariableStore()
const userSavings = ref<Savings | null>(null)
const route = useRoute()
const userStore = useUserStore()
const finStore = useFinStore()
const isModalOpen = ref(false) // 모달 상태 관리
const router = useRouter()

async function deleteSavings(userId: number) {
  await finStore.deleteSavings(userId)
  router.push({ name: "AssetMainView" })
}

const openModal = () => {
  isModalOpen.value = true
}

const closeModal = () => {
  isModalOpen.value = false
}

const formatExpireDate = (expireDate?: string | null) => {
  if (!expireDate) return "" // expireDate가 없는 경우 빈 문자열 반환
  const date = new Date(expireDate)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, "0") // 월은 0부터 시작하므로 +1
  const day = String(date.getDate()).padStart(2, "0")
  return `${year}년 ${month}월 ${day}일`
}

onMounted(async () => {
  varStore.setTitle("적금 상세내역")
  await finStore.getUserSavings(Number(route.params.userId))
  if (finStore.savings != null) {
    if (typeof finStore.savings === "object") {
      userSavings.value = finStore.savings
    }
  }
})
</script>
<template>
  <div class="mx-5 pt-14" v-if="userSavings">
    <div class="bg-white rounded-lg shadow justify-between px-4 py-3 gap-3" v-if="finStore.savings">
      <div class="grid gap-3">
        <h1 class="text-xl text-center font-semibold">적금 상세내역</h1>
        <hr />
        <div class="grid gap-2 pe-5">
          <div class="grid grid-flow-col grid-cols-2">
            <h1>상품명</h1>
            <h1 class="text-end">{{ userSavings.productName }}</h1>
          </div>
          <div class="grid grid-flow-col grid-cols-2">
            <h1>적금 이율</h1>
            <h1 class="text-end" v-if="userSavings.savingsRate">
              {{ userSavings.savingsRate.toFixed(2) }}
            </h1>
          </div>
          <div class="grid grid-flow-col grid-cols-2">
            <h1>적금 기간</h1>
            <h1 class="text-end">{{ userSavings.paymentDate }} 개월</h1>
          </div>
          <div class="flex justify-between">
            <h1>적금액</h1>
            <h1 class="text-end">{{ userSavings.balance?.toLocaleString() }}</h1>
          </div>
          <div class="flex justify-between">
            <h1>납입액</h1>
            <h1 class="text-end">{{ userSavings.paymentMoney?.toLocaleString() }}</h1>
          </div>
          <div class="flex justify-between" v-if="userSavings.createdAt">
            <h1>실행일</h1>
            <h1 class="text-end">{{ formatExpireDate(userSavings.createdAt) }}</h1>
          </div>
          <div class="flex justify-between" v-if="userSavings.expireDate">
            <h1>만기일</h1>
            <h1 class="text-end">{{ formatExpireDate(userSavings.expireDate) }}</h1>
          </div>
        </div>
      </div>
    </div>
    <div class="pt-5 text-end grid" v-if="userStore.user?.role === '자녀'" @click="">
      <button class="bg-red-500 rounded-lg text-white p-2" @click="openModal">해지하기</button>
    </div>
    <!-- 모달 창 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 flex items-center justify-center z-50 bg-gray-900 bg-opacity-50"
    >
      <div class="bg-white p-6 rounded-lg shadow-lg w-80">
        <h2 class="text-lg font-bold mb-4">정말 적금을 해지하시겠습니까?</h2>
        <div class="grid gap-1 mb-3">
          <p class="text-sm text-red-500">* 해지 후에는 취소할 수 없습니다.</p>
          <p class="text-sm text-red-500">계속 진행하시겠습니까?</p>
        </div>
        <div class="mb-6 text-sm grid gap-1">
          <h1>
            해지 시에는
            <span class="text-lg font-bold text-red-500 underline underline-offset-2"
              >{{ userSavings?.balance?.toLocaleString() }}원</span
            >
            을
          </h1>
          <h1>받을 수 있습니다.</h1>
        </div>
        <div class="flex justify-end">
          <button class="bg-gray-500 text-white px-4 py-2 rounded mr-2" @click="closeModal">
            취소
          </button>
          <button
            class="bg-red-500 text-white px-4 py-2 rounded"
            @click="() => deleteSavings(Number(userSavings?.savingsId))"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
