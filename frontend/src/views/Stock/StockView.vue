<script setup lang="ts">
import BoxItem from "@/components/box/boxItem.vue"
import NavBarTab from "@/components/navbar/NavBarTab.vue"
import StockChart from "@/components/StockChart.vue";
import { useVariableStore } from "@/stores/variable"



const store = useVariableStore()
store.setTitle("증권")
</script>

<template>
  <div>
    <NavBarTab />
    <BoxItem />
    <StockChart/>
  </div>
</template>
