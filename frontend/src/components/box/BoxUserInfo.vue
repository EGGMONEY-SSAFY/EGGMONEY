<script setup lang="ts">
import { useStockStore } from "@/stores/stock"
import { useUserStore } from "@/stores/user"
import { onMounted, ref } from "vue"

const StockStore = useStockStore()
const userStore = useUserStore()
const role = userStore.user?.role
const myStock = ref()
const myStockB = ref()
const myStockI = ref()

onMounted(async () => {
  myStock.value = await StockStore.getMoneyInfo()
  myStockB.value = myStock.value.balance.toLocaleString()
  myStockI.value = myStock.value.investablePrice.toLocaleString()
})
</script>

<template>
  <div class="flex flex-col gap-2 m-4 bg-white rounded-lg shadow" v-if="role === '자녀'">
    <div class="mx-6 mt-4">
      <span>현재 잔액 : </span>
      <span class="font-bold">{{ myStockB }} 알</span>
    </div>
    <div class="mx-6">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">{{ myStockI }} 알</span>
    </div>
    <div class="mx-6 mb-4">
      <span>총 주식 금액 : </span>
      <span class="font-bold" v-if="StockStore.totalStockValue"
        >{{ StockStore.totalStockValue.toLocaleString() }} 알</span
      >
    </div>
  </div>
</template>
