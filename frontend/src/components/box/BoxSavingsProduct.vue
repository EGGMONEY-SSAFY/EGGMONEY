<script setup lang="ts">
import { RouterLink } from "vue-router"

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
})

console.log(props.product.maxPrice)
const name = "FinSavingsCreateView"
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <RouterLink
      :product="product"
      :to="{
        name: `${name}`,
        query: {
          productId: product.productId,
          productName: product.productName,
          savingsRate: product.savingsRate,
          savingsDate: product.savingsRate,
          maxPrice: product.maxPrice,
        },
      }"
      class="m-2 flex justify-center w-full items-center"
    >
      <!-- fin/savings/create, fin/deposit/create로 변환-->
      <div class="my-4 mx-8 w-full flex flex-col gap-y-2">
        <div class="flex justify-between mb-3">
          <span class="font-bold text-xl">{{ product.productName }}</span>
        </div>
        <div class="flex justify-between items-end">
          <div class="text-main-color font-bold">{{ product.savingsDate }}개월</div>
          <div class="text-gray-600 text-sm">간 매달 지정된 금액을 적금합니다</div>
        </div>
        <div class="flex justify-between items-end">
          <span class="text-main-color font-bold">연 {{ product.savingsRate?.toFixed(1) }}%</span>
          <span class="text-gray-600 text-sm">의 이율로 돌려드려요</span>
        </div>
      </div>
    </RouterLink>
  </div>
</template>
