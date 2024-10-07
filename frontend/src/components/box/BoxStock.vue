<script setup lang="ts">
import { RouterLink } from "vue-router"
import IconRightArrow from "@/components/icons/IconRightArrow.vue"

const props = defineProps({
  stock: Object,
})

const nameMap: Record<string, string> = {
  KOSPI: "코스피",
  KOSDAQ: "코스닥",
  AUTOMOTIVE: "자동차",
  SEMICONDUCTOR: "반도체",
  HEALTHCARE: "헬스케어",
  BANKING: "은행",
  ENERGY_CHEMICAL: "에너지화학",
  STEEL: "철강",
  CONSTRUCTION: "건설",
  TRANSPORTATION: "운송",
  MEDIA_ENTERTAINMENT: "미디어",
  IT: "IT",
  UTILITIES: "유틸리티",
}
</script>

<template>
  <div class="flex justify-between m-4 bg-white rounded-lg shadow">
    <div class="flex self-center w-full m-6 font-bold">
      {{ nameMap[props.stock?.stockItem] }}
    </div>
    <div class="flex flex-col items-center justify-center w-full">
      <div>{{ props.stock?.price }}</div>
      <div class="text-xs" :class="props.stock?.gap > 0 ? 'text-red-500' : 'text-blue-500'">
        {{ props.stock?.gap > 0 ? "+" + props.stock?.gap : props.stock?.gap }} ({{
          props.stock?.ratio
        }}%)
      </div>
    </div>
    <RouterLink
      :to="`/stock/detail/${props.stock?.stockItem}`"
      class="flex items-center justify-center p-6"
    >
      <IconRightArrow class="size-6" />
    </RouterLink>
  </div>
</template>
