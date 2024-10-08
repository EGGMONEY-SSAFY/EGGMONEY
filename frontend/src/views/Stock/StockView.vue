<script setup lang="ts">
import BoxUserInfo from "@/components/box/BoxUserInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import BoxStock from "@/components/box/BoxStock.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useStockStore } from "@/stores/stock"
import { onMounted, ref } from "vue"

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
  store.setTitle("증권")
})
</script>

<template>
  <div>
    <NavBarTab />
    <BoxUserInfo />
    <HeldStocksChart />
    <div v-if="stockList">
      <div v-for="stock in stockList" :key="stock.stockItem">
        <BoxStock :stock="stock" />
      </div>
    </div>
  </div>
</template>
