<script setup lang="ts">
import BoxUserInfo from "@/components/box/BoxUserInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import BoxStock from "@/components/box/BoxStock.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"

const store = useVariableStore()
store.setTitle("증권")
const stockData = { 코스피: 30000, 코스닥: 20000, 반도체: 10000, 바이오: 5000 }
const total = Object.values(stockData).reduce((acc, value) => acc + value, 0)
const preData = { 코스피: 28000, 코스닥: 21000, 반도체: 7000, 바이오: 10000 }
const userData = { 현재잔액: 135000, 투자가능금액: 35000 }
</script>

<template>
  <div>
    <NavBarTab />
    <BoxUserInfo :userData="userData" />
    <div class="text-center">총 주식 금액 {{ total.toLocaleString() }} 알</div>
    <HeldStocksChart :total="total" />
    <div v-for="(price, stock) in stockData" :key="stock">
      <BoxStock :price="price" :stock="stock" :prePrice="preData[stock]" />
    </div>
  </div>
</template>
