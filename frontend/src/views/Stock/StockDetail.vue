<script setup lang="ts">
import BoxItem from "@/components/box/BoxAvailInvest.vue"
import BoxCurrentBuy from "@/components/box/BoxCurrentBuy.vue"
import BoxCurrentPrice from "@/components/box/BoxCurrentPrice.vue"
import BoxCurrentSell from "@/components/box/BoxCurrentSell.vue"
import BoxStockInfo from "@/components/box/BoxStockInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import { useRoute } from "vue-router"

// 임시
const data = { 코스피: 30000, 코스닥: 20000, 반도체: 10000, 바이오: 5000 }
const total = Object.values(data).reduce((acc, value) => acc + value, 0)

const route = useRoute()
const store = useVariableStore()
const name = route.params.stock as keyof typeof data
let price = 0
if (typeof name === "string" && name in data) {
  store.setTitle(name)
  price = data[name]
}
</script>

<template>
  <div>
    <BoxItem class="mt-16 sticky top-16" />
    <BoxCurrentPrice :price="price" class="sticky top-32" />
    <!-- 임시 -->
    <HeldStocksChart :total="total" />
    <BoxStockInfo />
    <BoxCurrentBuy :price="price" />
    <BoxCurrentSell :price="price" />
  </div>
</template>
