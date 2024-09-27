<script setup lang="ts">
import BoxDepositProduct from "@/components/box/BoxDepositProduct.vue"
import { useFinStore } from "@/stores/fin"
import { onMounted } from "vue"

const finStore = useFinStore()
onMounted(() => {
  if (finStore.depositProducts.length === 0) {
    finStore.getDepositProduct()
  }
})
</script>

<template>
  <div>
    <BoxDepositProduct
      v-for="product in finStore.depositProducts"
      :key="product.productId"
      :product="product"
    />
  </div>
</template>
