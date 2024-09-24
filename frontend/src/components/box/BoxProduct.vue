<script setup lang="ts">
import { RouterLink } from "vue-router"

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
  fin: String,
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <RouterLink to="/" class="m-2 flex justify-center w-full items-center">
      <div class="my-4 mx-8 w-full flex flex-col gap-y-2">
        <div class="flex justify-between mb-3">
          <span class="font-bold text-xl">{{ product.productName }}</span>
        </div>
        <div class="flex justify-between items-end">
          <div class="text-main-color font-bold">{{ product.depositDate }}개월</div>
          <div class="text-gray-600 text-sm">간 지정된 금액을 {{ fin }}합니다</div>
        </div>
        <div class="flex justify-between items-end">
          <span class="text-main-color font-bold">연 {{ product.depositRate }}%</span>
          <span class="text-gray-600 text-sm">의 이율로 돌려드려요</span>
        </div>
      </div>
    </RouterLink>
  </div>
</template>
