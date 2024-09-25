<script setup lang="ts">
import BoxUserInfo from "@/components/box/BoxUserInfo2.vue"
import BoxCurrentBuy from "@/components/box/BoxCurrentBuy.vue"
import BoxCurrentSell from "@/components/box/BoxCurrentSell.vue"
import BoxStockInfo from "@/components/box/BoxStockInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import { useRoute } from "vue-router"
import BoxLimitBuy from "@/components/box/BoxLimitBuy.vue"
import BoxLimitSell from "@/components/box/BoxLimitSell.vue"

// 임시
const data = { 코스피: 30000, 코스닥: 20000, 반도체: 10000, 바이오: 5000 }
const ownQuantity = { 코스피: 2, 코스닥: 3, 반도체: 1, 바이오: 0 }
const total = Object.values(data).reduce((acc, value) => acc + value, 0)

const route = useRoute()
const store = useVariableStore()
const name = route.params.stock as keyof typeof data
let price = 0
let Quantity = 0
if (typeof name === "string" && name in data) {
  store.setTitle(name)
  price = data[name]
  Quantity = ownQuantity[name]
}

const userData = { 현재잔액: 135000, 투자가능금액: 35000 }
</script>

<template>
  <div>
    <BoxUserInfo :price="price" class="mt-16 sticky top-16" />
    <!-- 임시 -->
    <HeldStocksChart :total="total" />
    <h1 class="text-center">주식 정보</h1>
    <BoxStockInfo :Quantity="Quantity" />
    <h1 class="text-center">현재가 거래</h1>
    <BoxCurrentBuy :price="price" :userData="userData" />
    <BoxCurrentSell :price="price" :Quantity="Quantity" />
    <div class="text-center">지정가 거래</div>
    <BoxLimitBuy :price="price" :userData="userData" />
    <BoxLimitSell :price="price" :Quantity="Quantity" />
  </div>
</template>
