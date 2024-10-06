<script setup lang="ts">
import BoxSavingsProduct from "@/components/box/BoxSavingsProduct.vue"

import { useFinStore } from "@/stores/fin"
import { onMounted, ref } from "vue"

const finStore = useFinStore()
onMounted(() => {
  if (finStore.savingsProducts.length === 0) {
    finStore.getSavingsProduct()
  }
})
</script>

<template>
  <div class="">
    <BoxSavingsProduct
      v-for="product in finStore.savingsProducts"
      :key="product.id"
      :product="product"
      :savingsDate="product.savingsDate"
      :savingsRate="product.savingsRate"
      :maxPrice="product.maxPrice"
    />
  </div>
</template>
