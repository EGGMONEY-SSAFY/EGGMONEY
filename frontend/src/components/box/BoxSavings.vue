<script setup lang="ts">
import { useAssetStore } from "@/stores/asset"
import IconRightArrow from "../icons/IconRightArrow.vue"
import { onMounted, watch } from "vue"
import { useUserStore } from "@/stores/user"
import type { Savings } from "@/stores/fin"
import type { User } from "@/stores/user"

const userStore = useUserStore()
const assetStore = useAssetStore()

const props = defineProps<{ user: User; savings: Savings | null }>()

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
  <div class="bg-white m-4 p-2 rounded-lg shadow">
    <div class="pb-4">
      <div class="m-3 flex justify-between text-sm">
        <h1
          class="font-semibold text-white bg-main-color rounded-xl text-center p-2 px-3 text-wrap"
        >
          {{ props.savings?.productName }}
        </h1>
        <!-- <button class="text-main-color font-semibold text-base my-auto" @click="">통장관리</button> -->
      </div>
      <div class="text-center">
        <h1 class="mt-8 text-lg underline underline-offset-4">
          적용금리
          <span class="text-main-color font-semibold">
            연 {{ props.savings?.savingsRate?.toFixed(2) }} %
          </span>
        </h1>
        <h1 style="font-size: 30px" class="my-3 font-semibold">
          {{ props.savings?.balance?.toLocaleString() }}<span class="ms-1 font-bold">원</span>
        </h1>
        <h1 v-if="props.savings?.expireDate" class="text-sm">
          만기일 {{ formatExpireDate(props.savings?.expireDate) }}
        </h1>
      </div>
      <div
        class="flex text-justify justify-center mt-8 px-5 gap-4"
        v-if="userStore.user?.role !== '자녀'"
      >
        <button class="bg-red-500 px-3 py-2 rounded-xl text-white font-semibold">해지하기</button>
        <button class="bg-lime-700 px-3 py-2 rounded-xl text-white font-semibold">납입하기</button>
      </div>
    </div>
  </div>
</template>
