<script setup lang="ts">
import BoxUserInfo from "@/components/box/BoxUserInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import BoxStock from "@/components/box/BoxStock.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useStockStore } from "@/stores/stock"
import { onMounted, ref } from "vue"

const idMap: Record<string, string> = {
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

interface StockList {
  stockId: number
  stockItem: string
  updatedDate: string
  price: number
  gap: number
  ratio: number
}

const store = useVariableStore()
const storeStock = useStockStore()
const stockList = ref<StockList[]>([])
onMounted(async () => {
  const fetchedStockPrice = await storeStock.getStockPrice()
  stockList.value = fetchedStockPrice
})

store.setTitle("증권")
</script>

<template>
  <div>
    <NavBarTab />
    <BoxUserInfo />
    <div class="text-center">총 주식 금액 100 알</div>
    <HeldStocksChart />
    <div v-if="stockList">
      <div v-for="stock in stockList" :key="stock.stockItem">
        <BoxStock :stock="stock" />
      </div>
    </div>
  </div>
</template>
