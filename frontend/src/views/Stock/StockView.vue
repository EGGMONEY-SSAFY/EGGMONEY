<script setup lang="ts">
import BoxUserInfo from "@/components/box/BoxUserInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import BoxStock from "@/components/box/BoxStock.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useStockStore } from "@/stores/stock"
import { onMounted, ref } from "vue"

interface StockPrice {
  stockItem: string
  price: number
  gap: number
  ratio: number
}

interface PriceList {
  date: string
  stockPrices: StockPrice[]
}

const store = useVariableStore()
const storeStock = useStockStore()
const priceList = ref<PriceList | null>(null)
onMounted(async () => {
  const fetchedStockPrice = await storeStock.getStockPrice()
  priceList.value = fetchedStockPrice
})

store.setTitle("증권")
const stockData = { 코스피: 30000, 코스닥: 20000, 반도체: 10000, 바이오: 5000 }
const total = Object.values(stockData).reduce((acc, value) => acc + value, 0)
const userData = { 현재잔액: 135000, 투자가능금액: 35000 }
</script>

<template>
  <div>
    <NavBarTab />
    <BoxUserInfo :userData="userData" />
    <div class="text-center">총 주식 금액 {{ total.toLocaleString() }} 알</div>
    <HeldStocksChart :total="total" />
    <div v-if="priceList">
      <div v-for="stockprice in priceList.stockPrices" :key="stockprice.stockItem">
        <BoxStock :stockprice="stockprice" />
      </div>
    </div>
  </div>
</template>
