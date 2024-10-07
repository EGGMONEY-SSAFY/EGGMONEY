<script setup lang="ts">
import { useStockStore } from "@/stores/stock"
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"

const idMap: Record<string, number> = {
  KOSPI: 1,
  KOSDAQ: 2,
  AUTOMOTIVE: 3,
  SEMICONDUCTOR: 4,
  HEALTHCARE: 5,
  BANKING: 6,
  ENERGY_CHEMICAL: 7,
  STEEL: 8,
  CONSTRUCTION: 9,
  TRANSPORTATION: 10,
  MEDIA_ENTERTAINMENT: 11,
  IT: 12,
  UTILITIES: 13,
}

const isModalOpen = ref(false)

const openModal = () => {
  isModalOpen.value = true
}

const closeModal = () => {
  isModalOpen.value = false
}

const props = defineProps({
  price: {
    type: Number,
    default: 0,
  },
})

interface StockList {
  stockId: number
  stockItem: string
  updatedDate: string
  price: number
  gap: number
  ratio: number
}

const route = useRoute()
const buyQuantity = ref(0)
const buyPrice = ref(props.price - 1)
const storeStock = useStockStore()
const myStock = ref()
const myStockB = ref()
const myStockI = ref()
const stockList = ref<StockList[]>([])

onMounted(async () => {
  const fetchedStockPrice = await storeStock.getStockPrice()
  stockList.value = fetchedStockPrice
  myStock.value = await storeStock.getMoneyInfo()
  myStockB.value = myStock.value.balance
  myStockI.value = myStock.value.investablePrice
})

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
  return myStockI.value - totalBuyAmount.value
})

const postBuyAmt1 = computed(() => {
  return myStockB.value - totalBuyAmount.value
})
</script>

<template>
  <div>
    <div class="bg-white m-4 rounded-lg shadow flex flex-col">
      <div class="flex justify-between">
        <div class="m-4">
          <p>매수 수량 {{}}</p>
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
          <p>매수 후 잔액</p>
        </div>
        <div class="m-4 flex justify-center items-center">
          <p :class="postBuyAmt1 < 0 ? 'text-red-500' : ''">{{ postBuyAmt1.toLocaleString() }}</p>
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
          @click="openModal"
          class="m-4 rounded-lg p-1 px-3 text-white"
          :class="
            postBuyAmt < 0 || buyQuantity == 0 || postBuyAmt1 < 0
              ? 'cursor-not-allowed bg-red-200'
              : 'bg-red-500 cursor-pointer hover:bg-red-600'
          "
          :disabled="postBuyAmt < 0"
        >
          매수
        </button>
      </div>
    </div>
    <!-- 모달 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white w-1/3 p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-semibold mb-4 text-center">지정가 매수</h2>
        <p class="mb-4">This is a modal body. You can add your content here.</p>
        <button @click="closeModal" class="text-gray-500 hover:text-gray-700">X</button>
      </div>
    </div>
  </div>
</template>
