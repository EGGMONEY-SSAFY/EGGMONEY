<script setup lang="ts">
import { RouterLink, useRoute } from "vue-router"
import { computed } from "vue"
import IconStock from "../icons/IconStock.vue"
import IconStockSelected from "../icons/IconStockSelected.vue"

const route = useRoute()
const isActive = computed(() => {
  return route.path.includes("/stock")
})
</script>

<template>
  <RouterLink to="/stock/home" class="flex flex-col justify-center items-center mt-2">
    <component :is="isActive ? IconStockSelected : IconStock" />
    <p class="text-sm mt-1">증권</p>
  </RouterLink>
</template>
