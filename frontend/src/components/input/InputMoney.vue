<script setup lang="ts">
import { isElementAccessExpression } from "typescript"
import { computed, ref } from "vue"

const money = ref(0)
const props = defineProps({
  maxPrice: {
    type: Number,
    default: 100000000,
  },
})

const max = ref(0)
const emit = defineEmits(["updateMoney"])
if (Number(props.maxPrice) != 0) {
  max.value = Number(props.maxPrice)
}

const preventNegativeMoney = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.valueAsNumber < 0) {
    money.value = 0
  } else if (input.valueAsNumber > max.value) {
    money.value = max.value
  } else {
    money.value = input.valueAsNumber
    emit("updateMoney", money.value)
  }
}
</script>

<template>
  <input
    class="bg-white mx-1 p-0.5 w-32 text-center rounded-md font-bold"
    type="number"
    v-model.number="money"
    placeholder="숫자를 입력하세요"
    @input="preventNegativeMoney"
    step="1000"
  />
</template>
