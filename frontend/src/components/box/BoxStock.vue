<script setup lang="ts">
import { RouterLink } from "vue-router"
import IconRightArrow from "@/components/icons/IconRightArrow.vue"

const props = defineProps({
  stock: String,
  price: { type: Number, default: 1 },
  prePrice: { type: Number, default: 1 },
})
const gap = props.price - props.prePrice
const rate = (gap / props.price) * 100
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <div class="m-6 flex self-center text-xl font-bold">{{ props.stock }}</div>
    <div class="flex flex-col justify-center items-center ml-24">
      <div>{{ props.price.toLocaleString() }}알</div>
      <div class="text-xs" :class="gap > 0 ? 'text-red-500' : 'text-blue-500'">
        {{ gap > 0 ? "+" + gap : gap }} ({{ Math.round(rate * 100) / 100 }}%)
      </div>
    </div>
    <RouterLink
      :to="`/stock/detail/${props.stock}`"
      class="m-6 flex justify-center items-center gap-4"
    >
      <IconRightArrow class="size-6" />
    </RouterLink>
  </div>
</template>
