<script setup lang="ts">
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import BoxUserInfo from "@/components/box/BoxUserInfo2.vue"
import BoxCurrentBuy from "@/components/box/BoxCurrentBuy.vue"
import BoxCurrentSell from "@/components/box/BoxCurrentSell.vue"
import BoxStockInfo from "@/components/box/BoxStockInfo.vue"
import BoxLimitBuy from "@/components/box/BoxLimitBuy.vue"
import BoxLimitSell from "@/components/box/BoxLimitSell.vue"
import { useRoute } from "vue-router"
import { useStockStore } from "@/stores/stock"
import { computed, onMounted, ref } from "vue"
import StockLogChart from "@/components/cart/StockLogChart.vue"
import { useVariableStore } from "@/stores/variable"

const nameMap: Record<string, string> = {
  KOSPI: "코스피",
  KOSDAQ: "코스닥",
  AUTOMOTIVE: "자동차",
  SEMICONDUCTOR: "반도체",
  HEALTHCARE: "헬스케어",
  BANKING: "은행",
  ENERGY_CHEMICAL: "에너지화학",
  STEEL: "철강",
  CONSTRUCTION: "건설",
  TRANSPORTATION: "운송",
  MEDIA_ENTERTAINMENT: "미디어",
  IT: "IT",
  UTILITIES: "유틸리티",
}

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

interface StockList {
  stockId: number
  stockItem: string
  updatedDate: string
  price: number
  gap: number
  ratio: number
}
const route = useRoute()
const stockStore = useStockStore()
const store = useVariableStore()
const stockList = ref<StockList[]>([])
const stockName = route.params.stockName as string
const data = ref()
const price = ref()
onMounted(async () => {
  store.setTitle(nameMap[route.params.stockName as string])
  const fetchedStockPrice = await stockStore.getStockPrice()
  const stockName = (await route.params.stockName) as string
  stockList.value = await fetchedStockPrice
  data.value = await stockStore.getMyStockInfo(idMap[stockName])
})

const matchingStock = computed(() => {
  return stockList.value.find((stock) => stock.stockItem === stockName)
})
</script>

<template>
  <div v-if="data && matchingStock">
    <NavBarTab />
    <BoxUserInfo :price="price" class="sticky mt-4 top-16" />
    <!-- 임시 -->
    <StockLogChart :stockId="idMap[stockName]" />
    <h1 class="text-center">주식 정보</h1>
    <BoxStockInfo />
    <h1 class="text-center">현재가 거래</h1>
    <BoxCurrentBuy :price="matchingStock.price" />
    <BoxCurrentSell :price="matchingStock.price" :Quantity="data.amount" />
    <div class="text-center">지정가 거래</div>
    <BoxLimitBuy :price="matchingStock.price" />
    <BoxLimitSell :price="matchingStock.price" :Quantity="data.amount" />
  </div>
</template>
