<script setup lang="ts">
import { useUserStore } from "@/stores/user"
import { RouterLink } from "vue-router"

const userStore = useUserStore()
const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
})
const isParent = userStore.user?.role === "부모" ? true : false
const name = "FinDepositCreateView"
</script>

<template>
  <div class="flex justify-between m-4 bg-white rounded-lg shadow">
    <RouterLink
      :product="product"
      :to="{
        name: `${name}`,
        query: {
          productId: product.productId,
        },
      }"
      class="m-2 flex justify-center w-full items-center"
      :class="{ 'pointer-events-none': isParent }"
    >
      <div class="flex flex-col w-full mx-8 my-4 gap-y-2">
        <div class="flex justify-between mb-3">
          <span class="text-xl font-bold">{{ props.product.productName }}</span>
        </div>
        <div class="flex items-end justify-between">
          <div class="font-bold text-main-color">{{ props.product.depositDate }}개월</div>
          <div class="text-sm text-gray-600">간 지정된 금액을 예금합니다</div>
        </div>
        <div class="flex items-end justify-between">
          <span class="font-bold text-main-color"
            >연 {{ props.product.depositRate?.toFixed(1) }}%</span
          >
          <span class="text-sm text-gray-600">의 이율로 돌려드려요</span>
        </div>
      </div>
    </RouterLink>
  </div>
</template>
