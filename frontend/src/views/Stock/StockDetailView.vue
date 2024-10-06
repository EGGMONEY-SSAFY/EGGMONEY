<script setup lang="ts">
import BoxUserInfo from "@/components/box/BoxUserInfo2.vue"
import BoxCurrentBuy from "@/components/box/BoxCurrentBuy.vue"
import BoxCurrentSell from "@/components/box/BoxCurrentSell.vue"
import BoxStockInfo from "@/components/box/BoxStockInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import BoxLimitBuy from "@/components/box/BoxLimitBuy.vue"
import BoxLimitSell from "@/components/box/BoxLimitSell.vue"
import { useRoute } from "vue-router"
import { useStockStore } from "@/stores/stock"
import { computed, onMounted, ref } from "vue"

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
const name = route.params.stockName as string
const stockStore = useStockStore()
const stockList = ref<StockList[]>([])
const stockName = route.params.stockName as string
const data = ref()
const price = ref()
onMounted(async () => {
  const fetchedStockPrice = await stockStore.getStockPrice()
  stockList.value = fetchedStockPrice
  data.value = await stockStore.getMyStockInfo(idMap[stockName])
})

const matchingStock = computed(() => {
  return stockList.value.find((stock) => stock.stockItem === name)
})
</script>

<template>
  <div v-if="data && matchingStock">
    <BoxUserInfo :price="price" class="mt-16 sticky top-16" />
    <!-- 임시 -->
    <HeldStocksChart />
    <h1 class="text-center">주식 정보</h1>
    <BoxStockInfo />
    <h1 class="text-center">현재가 거래</h1>
    <BoxCurrentBuy :price="matchingStock.price" />
    <BoxCurrentSell :price="matchingStock.price" :Quantity="data.amount" />
    <div class="text-center">지정가 거래</div>
    <BoxLimitBuy :price="matchingStock.price" />
    <BoxLimitSell :price="matchingStock.price" :Quantity="data.amount" />
    <div class="p-4">
      <button class="btn btn-primary">Flowbite Button</button>
    </div>
  </div>
</template>
