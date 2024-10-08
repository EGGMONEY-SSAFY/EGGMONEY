<script setup lang="ts">
import BoxTradeLog from "@/components/box/BoxTradeLog.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useStockStore } from "@/stores/stock"
import { useVariableStore } from "@/stores/variable"
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"

const route = useRoute()
const path = computed(() => {
  return route.path
})
const store = useVariableStore()
store.setTitle("거래내역")
const stockStore = useStockStore()
const logList = ref()
onMounted(async () => {
  logList.value = await stockStore.getMyStockLog()
})
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <div class="flex flex-col justify-between flex-grow" v-if="logList">
      <BoxTradeLog v-for="log in logList" :log="log" :key="log.stockItem" />
    </div>
    <div v-else>sfasdf</div>
  </div>
</template>
