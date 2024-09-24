<script setup lang="ts">
import BoxProduct from "@/components/box/BoxProduct.vue"
import NavBarFinTab from "@/components/navbar/navBarTab/NavBarFinTab.vue"
import { useFinStore } from "@/stores/fin"
import { onMounted, ref } from "vue"

const finStore = useFinStore()
onMounted(() => {
  finStore.getDepositProduct()
})
</script>

<template>
  <div class="">
    <NavBarFinTab />
    <BoxProduct
      v-for="product in finStore.depositProducts"
      :key="product.productId"
      :product="product"
      fin="예금"
    />
  </div>
</template>
