<script setup lang="ts">
import { RouterLink } from "vue-router"

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
})
const name = "FinSavingsCreateView"
</script>

<template>
  <div class="flex justify-between m-4 bg-white rounded-lg shadow">
    <RouterLink
      :product="product"
      :to="{
        name: `${name}`,
        query: {
          productId: product.id,
        },
      }"
      class="flex items-center justify-center w-full m-2"
    >
      <!-- fin/savings/create, fin/deposit/create로 변환-->
      <div class="flex flex-col w-full mx-8 my-4 gap-y-2">
        <div class="flex justify-between mb-3">
          <span class="text-xl font-bold">{{ props.product.productName }}</span>
        </div>
        <div class="flex items-end justify-between">
          <div class="font-bold text-main-color">{{ props.product.savingsDate }}개월</div>
          <div class="text-sm text-gray-600">간 매달 지정된 금액을 적금합니다</div>
        </div>
        <div class="flex items-end justify-between">
          <span class="font-bold text-main-color"
            >연 {{ props.product.savingsRate?.toFixed(1) }}%</span
          >
          <span class="text-sm text-gray-600">의 이율로 돌려드려요</span>
        </div>
      </div>
    </RouterLink>
  </div>
</template>
