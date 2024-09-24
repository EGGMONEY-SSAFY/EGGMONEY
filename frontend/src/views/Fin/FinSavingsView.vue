<script setup lang="ts">
import BoxProduct from "@/components/box/BoxProduct.vue"
import NavBarFinTab from "@/components/navbar/navBarTab/NavBarFinTab.vue"
import { useFinStore } from "@/stores/fin"
import { onMounted, ref } from "vue"

const finStore = useFinStore()
onMounted(() => {
  if (finStore.savingsProducts.length === 0) {
    finStore.getSavingsProduct() // Fetch data only if products are empty
  }
})
</script>

<template>
  <div class="">
    <NavBarFinTab />
    <BoxProduct
      v-for="product in finStore.savingsProducts"
      :key="product.productId"
      :product="product"
      fin="적금"
    />
  </div>
</template>
