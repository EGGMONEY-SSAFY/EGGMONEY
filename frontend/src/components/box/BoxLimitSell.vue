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

const Quantity = ref(props.Quantity)
const sellQuantity = ref(0)
const sellPrice = ref(props.price + 1)

const totalSellAmount = computed(() => {
  return sellQuantity.value * sellPrice.value
})

const preventNegativeQuantity = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.valueAsNumber < 0) {
    sellQuantity.value = 0
  } else if (input.valueAsNumber > Quantity.value) {
    sellQuantity.value = Quantity.value
  }
}

const preventNegativePrice = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.valueAsNumber < props.price + 1) {
    sellPrice.value = props.price + 1
  }
}
</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="flex justify-between">
      <div class="m-4">
        <p>보유 수량</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p>{{ Quantity }}</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>매도 수량</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <input
          class="w-12 mx-1 text-center bg-gray-200 rounded"
          type="number"
          v-model.number="sellQuantity"
          placeholder="숫자를 입력하세요"
          @input="preventNegativeQuantity"
        />
        <p>주</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>매도 가격</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <input
          class="w-24 mx-1 text-center bg-gray-200 rounded"
          type="number"
          v-model.number="sellPrice"
          placeholder="숫자를 입력하세요"
          @input="preventNegativePrice"
        />
        <p>알</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>총 매도액</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p>{{ totalSellAmount }}</p>
      </div>
    </div>

    <div class="flex justify-center">
      <div
        class="p-1 px-3 m-4 text-white bg-blue-500 rounded-lg cursor-pointer hover:bg-blue-200 hover:text-black"
      >
        매도
      </div>
    </div>
  </div>
</template>
