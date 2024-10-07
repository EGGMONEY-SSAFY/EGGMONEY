<script setup lang="ts">
import { useStockStore } from "@/stores/stock"
import { onMounted, ref } from "vue"

const AuthStore = useStockStore()
const myStock = ref()
const myStockB = ref()
const myStockI = ref()
onMounted(async () => {
  myStock.value = await AuthStore.getMoneyInfo()
  myStockB.value = myStock.value.balance.toLocaleString()
  myStockI.value = myStock.value.investablePrice.toLocaleString()
})
</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="m-4 mb-2">
      <span>현재 잔액 : </span>
      <span class="font-bold">{{ myStockB }} 알</span>
    </div>
    <div class="m-4 mt-2">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">{{ myStockI }} 알</span>
    </div>
  </div>
</template>
