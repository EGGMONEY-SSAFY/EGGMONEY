<script setup lang="ts">
import { useStockStore } from "@/stores/stock"
import { onMounted, ref } from "vue"
import { useRoute } from "vue-router"

const idMap: Record<string, number> = {
  KOSPI: 1,
  KOSDAQ: 2,
  AUTOMOTIVE: 3,
  SEMICONDUCTOR: 4,
  HEALTHCARE: 5,
  BANKING: 6,
  ENERGY_CHEMICAL: 7,
  STEEL: 8,
  CONSTRUCTION: 9,
  TRANSPORTATION: 10,
  MEDIA_ENTERTAINMENT: 11,
  IT: 12,
  UTILITIES: 13,
}

const route = useRoute()
const stockStore = useStockStore()
const stockName = route.params.stockName as string
const data = ref()
onMounted(async () => {
  data.value = await stockStore.getMyStockInfo(idMap[stockName])
})
</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow" v-if="data">
    <div class="flex justify-between">
      <div class="m-4">
        <p>매수 평균가</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p>{{ data.buyAverage }} 알</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>보유 수량</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p>{{ data.amount }} 주</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>매수 금액</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p>{{ data.totalInvestment }} 알</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>평가액</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p>{{ data.value }} 알</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>수익률</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p>{{ data.roi }}%</p>
      </div>
    </div>
  </div>
</template>
