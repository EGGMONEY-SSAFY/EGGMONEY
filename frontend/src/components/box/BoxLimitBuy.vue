<script setup lang="ts">
import { computed, ref } from "vue"

const props = defineProps({
  price: {
    type: Number,
    default: 0,
  },
  Quantity: {
    type: Number,
    default: 0,
  },
})

const buyQuantity = ref(0)
const totalBuyAmount = computed(() => {
  return buyQuantity.value * props.price
})

const preventNegativeQuantity = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.valueAsNumber < 0) {
    buyQuantity.value = 0
  }
}
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="flex justify-between">
      <div class="m-4">
        <p>매수 수량</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <input
          class="bg-gray-200 mx-1 w-12 text-center rounded"
          type="number"
          v-model.number="buyQuantity"
          placeholder="숫자를 입력하세요"
          @input="preventNegativeQuantity"
        />
        <p>주</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>총 매수액</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p>{{ totalBuyAmount.toLocaleString() }} 알</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>매수 후 투자 가능 금액</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p>sef</p>
      </div>
    </div>

    <div class="flex justify-center">
      <div
        class="bg-red-500 m-4 rounded-lg p-1 px-3 text-white cursor-pointer hover:bg-red-200 hover:text-black"
      >
        매수
      </div>
    </div>
  </div>
</template>
