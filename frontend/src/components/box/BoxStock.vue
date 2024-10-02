<script setup lang="ts">
import { RouterLink } from "vue-router"
import IconRightArrow from "@/components/icons/IconRightArrow.vue"

const props = defineProps({
  stockprice: Object,
})

const nameMap: Record<string, string> = {
  KOSPI: "코스피",
  KOSDAQ: "코스닥",
  AUTOMOTIVE: "자동차",
  BANKING: "은행",
  CONSTRUCTION: "건설",
  ENERGY_CHEMICAL: "에너지화학",
  HEALTHCARE: "헬스케어",
  IT: "IT",
  MEDIA_ENTERTAINMENT: "미디어",
  SEMICONDUCTOR: "반도체",
  STEEL: "철강",
  TRANSPORTATION: "운송",
  UTILITIES: "유틸리티",
}
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <div class="m-6 flex self-center font-bold w-full">
      {{ nameMap[props.stockprice?.stockItem] }}
    </div>
    <div class="flex flex-col justify-center items-center w-full">
      <div>{{ props.stockprice?.price }}</div>
      <div class="text-xs" :class="props.stockprice?.gap > 0 ? 'text-red-500' : 'text-blue-500'">
        {{ props.stockprice?.gap > 0 ? "+" + props.stockprice?.gap : props.stockprice?.gap }} ({{
          props.stockprice?.ratio
        }}%)
      </div>
    </div>
    <RouterLink
      :to="`/stock/detail/${props.stockprice?.stockItem}`"
      class="m-6 flex justify-center items-center gap-4"
    >
      <IconRightArrow class="size-6" />
    </RouterLink>
  </div>
</template>
