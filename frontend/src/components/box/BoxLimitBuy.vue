<script setup lang="ts">
import { computed, ref } from "vue"

const props = defineProps({
  price: {
    type: Number,
    default: 0,
  },
  userData: {
    type: Object,
  },
})

const buyQuantity = ref(0)
const buyPrice = ref(props.price - 1)
const totalBuyAmount = computed(() => {
  return buyQuantity.value * buyPrice.value
})

const preventNegativeQuantity = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.valueAsNumber < 0) {
    buyQuantity.value = 0
  }
}

const preventNegativePrice = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.valueAsNumber < 0) {
    buyPrice.value = 0
  } else if (input.valueAsNumber >= props.price) {
    buyPrice.value = props.price - 1
  }
}

const postBuyAmt = computed(() => {
  return props.userData?.["투자가능금액"] - totalBuyAmount.value
})
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
        <p>매수 가격</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <input
          class="bg-gray-200 mx-1 w-24 text-center rounded"
          type="number"
          v-model.number="buyPrice"
          placeholder="숫자를 입력하세요"
          @input="preventNegativePrice"
        />
        <p>알</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>총 매수액</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p>{{ totalBuyAmount }}알</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>매수 후 투자 가능 금액</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p :class="postBuyAmt < 0 ? 'text-red-500' : ''">{{ postBuyAmt.toLocaleString() }}</p>
      </div>
    </div>

    <div class="flex justify-center">
      <button
        class="m-4 rounded-lg p-1 px-3 text-white"
        :class="
          postBuyAmt < 0 || buyQuantity == 0
            ? 'cursor-not-allowed bg-red-200'
            : 'bg-red-500 cursor-pointer hover:bg-red-600'
        "
        :disabled="postBuyAmt < 0"
      >
        매수
      </button>
    </div>
  </div>
</template>
