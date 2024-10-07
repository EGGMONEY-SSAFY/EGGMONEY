<script setup lang="ts">
import BoxOrderLog from "@/components/box/BoxOrderLog.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useStockStore } from "@/stores/stock"
import { useVariableStore } from "@/stores/variable"
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"

const store = useVariableStore()
const stockStore = useStockStore()
store.setTitle("주문현황")
const route = useRoute()
const orderList = ref()
const path = computed(() => {
  return route.path
})
onMounted(async () => {
  orderList.value = await stockStore.getOrderLog()
})
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <div class="flex flex-col justify-between flex-grow">
      <BoxOrderLog v-for="log in orderList" :log="log" :key="log.stockId" />
    </div>
  </div>
</template>
