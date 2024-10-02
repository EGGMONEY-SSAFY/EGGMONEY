<script setup lang="ts">
import { onMounted, ref } from "vue"
import IconChashed from "../icons/IconChashed.vue"

interface Log {
  stockItem: string
  tradeDate: string
  tradeType: string
  price: number
  amount: number
  totalPrice: number
}
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
const props = defineProps<{
  log: Log
}>()

const { stockItem, tradeDate, tradeType, price, amount, totalPrice } = props.log
const formattedDate = new Date(tradeDate)
const isDetail = ref(false)
const isBuy = ref(false)
const type = ref("매도")

onMounted(() => {
  if (tradeType === "BUY") {
    isBuy.value = true
    type.value = "매수"
  }
})

const toggleDetail = () => {
  isDetail.value = !isDetail.value
}

const displayDate = formattedDate.toLocaleString("ko-KR", {
  month: "2-digit",
  day: "2-digit",
  hour: "2-digit",
  minute: "2-digit",
  hour12: false,
})

const displayDateDetail = formattedDate.toLocaleString("ko-KR", {
  year: "2-digit",
  month: "2-digit",
  day: "2-digit",
  hour: "2-digit",
  minute: "2-digit",
  second: "2-digit",
  hour12: false,
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="m-4 flex justify-between">
      <p class="font-bold">{{ nameMap[stockItem] }}</p>
      <div class="flex justify-center items-center gap-2">
        <p class="text-sm text-gray-500">{{ isDetail ? displayDateDetail : displayDate }}</p>
        <IconChashed class="size-5 cursor-pointer" @click="toggleDetail" />
      </div>
    </div>
    <hr />
    <div class="m-4 flex justify-between">
      <p>{{ type }}가</p>
      <p>{{ price }} 알</p>
    </div>
    <div class="m-4 flex justify-between">
      <p>{{ type }}수량</p>
      <p>{{ amount }} 주</p>
    </div>
    <div class="m-4 flex justify-between">
      <p>{{ type }}총액</p>
      <p :class="isBuy ? 'text-red-500' : 'text-blue-500'">{{ totalPrice }} 알</p>
    </div>
  </div>
</template>
