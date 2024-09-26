<script setup lang="ts">
import { onMounted } from "vue"
import { useRouter } from "vue-router"
import type { User } from "@/stores/user"
import type { Analytics } from "@/stores/asset"

const props = defineProps<{ user: User; analytics: Analytics | null }>()
const router = useRouter()

const goMainAccount = () => {
  router.push({ name: "AssetMainAccountView" })
}

const goSavings = () => {
  router.push({ name: "AssetSavingsView" })
}

const goDeposit = () => {
  router.push({ name: "AssetDepositView" })
}

const goLoan = () => {
  router.push({ name: "AssetLoanListView" })
}
</script>

<template>
  <div class="bg-white m-4 p-3 rounded-lg shadow grid grid-cols-1 grid-flow-row gap-4">
    <div class="grid grid-flow-row grid-cols-1 gap-6 py-3 px-3">
      <!-- 메인 계좌 -->
      <div
        class="flex"
        v-if="props.analytics?.입출금통장 != null"
        @click="goMainAccount"
        role="button"
      >
        <img src="@/assets/asset/account.png" alt="main-account" style="width: 51px" />
        <div class="flex items-center justify-between px-4 pe-2 w-full">
          <h1 class="flex-grow truncate font-bold">입출금통장</h1>
          <h3 class="whitespace-nowrap">
            {{ props.analytics.입출금통장.toLocaleString() }}<span class="ps-1">원</span>
          </h3>
        </div>
      </div>
      <hr v-if="props.analytics?.예금 != null" />
      <!-- 예금 계좌 -->
      <div class="flex" v-if="props.analytics?.예금 != null" @click="goDeposit" role="button">
        <img src="@/assets/asset/account_2.png" alt="main-account" style="width: 51px" />
        <div class="flex items-center justify-between px-4 pe-2 w-full">
          <h1 class="flex-grow truncate font-bold">예금 통장</h1>
          <h3 class="whitespace-nowrap">
            {{ props.analytics.예금.toLocaleString() }}<span class="ps-1">원</span>
          </h3>
        </div>
      </div>
      <hr v-if="props.analytics?.적금 != null" />
      <!-- 적금 계좌 -->
      <div class="flex" v-if="props.analytics?.적금 != null" @click="goSavings" role="button">
        <img src="@/assets/asset/savings_2.png" alt="main-account" style="width: 51px" />
        <div class="flex items-center justify-between px-4 pe-2 w-full">
          <h1 class="flex-grow truncate font-bold">적금 통장</h1>
          <h3 class="whitespace-nowrap">
            {{ props.analytics.적금.toLocaleString() }}<span class="ps-1">원</span>
          </h3>
        </div>
      </div>
      <hr v-if="props.analytics?.대출 != null" />
      <!-- 대출 계좌 -->
      <div class="flex" v-if="props.analytics?.대출 != null" @click="goLoan" role="button">
        <img src="@/assets/asset/loan.png" alt="main-account" style="width: 51px" />
        <div class="flex items-center justify-between px-4 pe-2 w-full">
          <h1 class="flex-grow truncate font-bold">대출금</h1>
          <h3 class="whitespace-nowrap text-red-600">
            {{ props.analytics.대출.toLocaleString() }}<span class="ps-1 text-red-600">원</span>
          </h3>
        </div>
      </div>
    </div>
  </div>
</template>
