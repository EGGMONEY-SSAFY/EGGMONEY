<script setup lang="ts">
import BoxItem from "@/components/box/BoxAvailInvest.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import BoxStock from "@/components/box/BoxStock.vue"

const store = useVariableStore()
store.setTitle("증권")
const data = { 코스피: 30000, 코스닥: 20000, 반도체: 10000, 바이오: 5000 }
const total = Object.values(data).reduce((acc, value) => acc + value, 0)
const preData = { 코스피: 28000, 코스닥: 21000, 반도체: 7000, 바이오: 10000 }
</script>

<template>
  <div>
    <NavBarTab />
    <BoxItem />
    <HeldStocksChart :total="total" />
    <div v-for="(price, stock) in data" :key="stock">
      <BoxStock :price="price" :stock="stock" :prePrice="preData[stock]" />
    </div>
  </div>
</template>
