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
})

store.setTitle("증권")
const stockData = { 코스피: 30000, 코스닥: 20000, 반도체: 10000, 바이오: 5000 }
const total = Object.values(stockData).reduce((acc, value) => acc + value, 0)

</script>

<template>
  <div>
    <NavBarTab />
    <BoxUserInfo />
    <!-- {{ storeStock.getMyStock }} -->
    <div class="text-center">총 주식 금액 {{ total.toLocaleString() }} 알</div>
    <HeldStocksChart :total="total" />
    <div v-if="stockList">
      <div v-for="stock in stockList" :key="stock.stockItem">
        <BoxStock :stock="stock" />
      </div>
    </div>
  </div>
</template>
