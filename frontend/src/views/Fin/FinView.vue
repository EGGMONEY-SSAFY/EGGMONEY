<script setup lang="ts">
import BoxProduct from "@/components/box/BoxProduct.vue"
import NavBarFinTab from "@/components/navbar/navBarTab/NavBarFinTab.vue"
import { useFinStore } from "@/stores/fin"
import { onMounted, ref } from "vue"
const finStore = useFinStore()

import axios from "axios"

// const DEPOSIT_PRODUCT_API_URL = "/api/v1/fin/deposit/product"
// const products = reactive<Products[]>([])

// interface Products {
//   productId: number
//   productName: string
//   depositRate: number
//   depositDate: number
// }

// axios({
//   method: "GET",
//   url: `${DEPOSIT_PRODUCT_API_URL}`,
// })
//   .then((res) => {
//     products.join(res.data)
//     console.log(res.data)
//   })
//   .catch((err) => {
//     console.error(err)
//   })

onMounted(() => {
  finStore.getDepositProduct()
})
console.log(finStore.products)
</script>

<template>
  <div class="">
    <NavBarFinTab />
    <BoxProduct v-for="product in finStore.products" :key="product.productId" :product="product" />
  </div>
</template>
