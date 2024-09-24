<script setup lang="ts">
import BoxProduct from "@/components/box/BoxProduct.vue"
import { useFinStore } from "@/stores/fin"
import { onMounted, ref } from "vue"

const finStore = useFinStore()
onMounted(() => {
  if (finStore.depositProducts.length === 0) {
    finStore.getDepositProduct()
  }
})
</script>

<template>
  <div>
    <BoxProduct
      v-for="product in finStore.depositProducts"
      :key="product.productId"
      :product="product"
      :date="product.depositDate"
      :rate="product.depositRate"
      fin="예금"
    />
  </div>
</template>
