<script setup lang="ts">
import { RouterLink } from "vue-router"

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
})
const name = "FinDepositCreateView"
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
          depositDate: product.depositDate,
          depositRate: product.depositRate,
        },
      }"
      class="m-2 flex justify-center w-full items-center"
    >
      <div class="my-4 mx-8 w-full flex flex-col gap-y-2">
        <div class="flex justify-between mb-3">
          <span class="font-bold text-xl">{{ props.product.productName }}</span>
        </div>
        <div class="flex justify-between items-end">
          <div class="text-main-color font-bold">{{ props.product.depositDate }}개월</div>
          <div class="text-gray-600 text-sm">간 지정된 금액을 예금합니다</div>
        </div>
        <div class="flex justify-between items-end">
          <span class="text-main-color font-bold"
            >연 {{ props.product.depositRate?.toFixed(1) }}%</span
          >
          <span class="text-gray-600 text-sm">의 이율로 돌려드려요</span>
        </div>
      </div>
    </RouterLink>
  </div>
</template>
