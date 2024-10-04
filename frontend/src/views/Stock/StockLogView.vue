<script setup lang="ts">
import BoxTradeLog from "@/components/box/BoxTradeLog.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed } from "vue"
import { useRoute } from "vue-router"

const route = useRoute()
const path = computed(() => {
  return route.path
})
const store = useVariableStore()
store.setTitle("거래내역")

const logList = [
  {
    stockItem: "KOSPI",
    tradeDate: "2024-10-02T00:22:03.339892",
    tradeType: "BUY",
    price: 2650,
    amount: 2,
    totalPrice: 5300,
  },
  {
    stockItem: "KOSDAQ",
    tradeDate: "2024-10-02T00:10:12.302082",
    tradeType: "SELL",
    price: 774,
    amount: 2,
    totalPrice: 1548,
  },
]
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <div class="flex flex-col flex-grow justify-between">
      <BoxTradeLog v-for="log in logList" :log="log" :key="log.stockItem" />
    </div>
  </div>
</template>
