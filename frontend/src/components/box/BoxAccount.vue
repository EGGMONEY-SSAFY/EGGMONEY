<script setup lang="ts">
import { onMounted } from "vue"

interface User {
  userId: number
  name: string
}

interface Analytics {
  입출금통장: Number | null
  예금: Number | null
  적금: Number | null
  대출: Number | null
  주식: Number | null
}

const props = defineProps<{ user: User; analytics: Analytics | null }>()

onMounted(() => {
  console.log(props.analytics?.입출금통장)
})
</script>

<template>
  <div class="bg-white m-4 p-3 rounded-lg shadow grid grid-cols-1 grid-flow-row gap-4">
    <div class="grid grid-flow-row grid-cols-1 gap-6 py-3">
      <!-- 메인 계좌 -->
      <div class="flex" v-if="props.analytics?.입출금통장 != null">
        <img src="@/assets/asset/main-account.png" alt="main-account" />
        <div class="flex items-center justify-between px-3 w-full">
          <h1 class="flex-grow truncate font-bold">입출금통장</h1>
          <h3 class="whitespace-nowrap">
            {{ props.analytics.입출금통장.toLocaleString() }}<span class="ps-1">원</span>
          </h3>
        </div>
      </div>
      <!-- 예금 계좌 -->
      <div class="flex" v-if="props.analytics?.예금 != null">
        <img src="@/assets/asset/savings.png" alt="main-account" />
        <div class="flex items-center justify-between px-3 w-full">
          <h1 class="flex-grow truncate font-bold">예금 통장</h1>
          <h3 class="whitespace-nowrap">
            {{ props.analytics.예금.toLocaleString() }}<span class="ps-1">원</span>
          </h3>
        </div>
      </div>
      <!-- 적금 계좌 -->
      <div class="flex" v-if="props.analytics?.적금 != null">
        <img src="@/assets/asset/savings.png" alt="main-account" />
        <div class="flex items-center justify-between px-3 w-full">
          <h1 class="flex-grow truncate font-bold">적금 통장</h1>
          <h3 class="whitespace-nowrap">
            {{ props.analytics.적금.toLocaleString() }}<span class="ps-1">원</span>
          </h3>
        </div>
      </div>
      <!-- 대출 계좌 -->
      <div class="flex" v-if="props.analytics?.대출 != null">
        <img src="@/assets/asset/savings.png" alt="main-account" />
        <div class="flex items-center justify-between px-3 w-full">
          <h1 class="flex-grow truncate font-bold">대출금</h1>
          <h3 class="whitespace-nowrap text-red-600">
            {{ props.analytics.대출.toLocaleString() }}<span class="ps-1 text-red-600">원</span>
          </h3>
        </div>
      </div>
    </div>
  </div>
</template>
