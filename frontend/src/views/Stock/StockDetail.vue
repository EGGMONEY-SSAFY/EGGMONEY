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
import { computed, onMounted } from "vue"

const stockStore = useStockStore()
const route = useRoute()
const path = computed(() => route.path)
onMounted(async () => {})

let price = 0
let Quantity = 0
</script>

<template>
  <div>
    <BoxUserInfo :price="price" class="mt-16 sticky top-16" />
    <!-- {{ chartData }} -->
    <!-- 임시 -->
    <HeldStocksChart />
    <h1 class="text-center">주식 정보</h1>
    <BoxStockInfo :Quantity="Quantity" />
    <h1 class="text-center">현재가 거래</h1>
    <BoxCurrentBuy :price="price" />
    <BoxCurrentSell :price="price" :Quantity="Quantity" />
    <div class="text-center">지정가 거래</div>
    <BoxLimitBuy :price="price" />
    <BoxLimitSell :price="price" :Quantity="Quantity" />
  </div>
</template>
