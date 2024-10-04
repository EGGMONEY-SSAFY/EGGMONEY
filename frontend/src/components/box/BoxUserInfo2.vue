<script setup lang="ts">
import { useStockStore } from "@/stores/stock"
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"

const nameMap: Record<string, string> = {
  KOSPI: "코스피",
  KOSDAQ: "코스닥",
  AUTOMOTIVE: "자동차",
  BANKING: "은행",
  CONSTRUCTION: "건설",
  ENERGY_CHEMICAL: "에너지화학",
  HEALTHCARE: "헬스케어",
  IT: "IT",
  MEDIA_ENTERTAINMENT: "미디어",
  SEMICONDUCTOR: "반도체",
  STEEL: "철강",
  TRANSPORTATION: "운송",
  UTILITIES: "유틸리티",
}

interface StockList {
  stockId: number
  stockItem: string
  updatedDate: string
  price: number
  gap: number
  ratio: number
}

const storeStock = useStockStore()
const stockList = ref<StockList[]>([])
onMounted(async () => {
  const fetchedStockPrice = await storeStock.getStockPrice()
  stockList.value = fetchedStockPrice
})

const userData = { 현재잔액: 135000, 투자가능금액: 35000 }
const route = useRoute()
const name = route.params.stock as string

const matchingStock = computed(() => {
  return stockList.value.find((stock) => stock.stockItem === name)
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col gap-2">
    <div class="mx-4 mt-4">
      <span>현재 잔액 : </span>
      <span class="font-bold">{{ userData["현재잔액"].toLocaleString() }} 알</span>
    </div>
    <div class="mx-4">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">{{ userData["투자가능금액"].toLocaleString() }} 알</span>
    </div>
    <div class="mx-4 mb-4">
      <span>{{ nameMap[name] }} 가격 : </span>
      <div v-if="matchingStock" class="inline">
        <span class="font-bold">{{ matchingStock.price }} 알</span>
      </div>
    </div>
  </div>
</template>
