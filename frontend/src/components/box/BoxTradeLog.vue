z
<script setup lang="ts">
import { onMounted, ref } from "vue"
import IconChashed from "@/components/icons/IconChashed.vue"

interface Log {
  stockId: number
  tradeDate: string
  tradeType: string
  price: number
  amount: number
  totalPrice: number
}
const nameMap: Record<number, string> = {
  1: "코스피",
  2: "코스닥",
  3: "자동차",
  4: "반도체",
  5: "헬스케어",
  6: "은행",
  7: "에너지화학",
  8: "철강",
  9: "건설",
  10: "운송",
  11: "미디어",
  12: "IT",
  13: "유틸리티",
}
const props = defineProps<{
  log: Log
}>()

const { stockId, tradeDate, tradeType, price, amount, totalPrice } = props.log
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
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="flex justify-between m-4">
      <p class="font-bold">{{ nameMap[stockId] }}</p>
      <div class="flex items-center justify-center gap-2">
        <p class="text-sm text-gray-500">{{ isDetail ? displayDateDetail : displayDate }}</p>
        <IconChashed class="cursor-pointer size-5" @click="toggleDetail" />
      </div>
    </div>
    <hr />
    <div class="flex justify-between m-4">
      <p>{{ type }}가</p>
      <p>{{ price }} 알</p>
    </div>
    <div class="flex justify-between m-4">
      <p>{{ type }} 수량</p>
      <p>{{ amount }} 주</p>
    </div>
    <div class="flex justify-between m-4">
      <p>{{ type }} 총액</p>
      <p :class="isBuy ? 'text-red-500' : 'text-blue-500'">{{ totalPrice }} 알</p>
    </div>
  </div>
</template>
