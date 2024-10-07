<script setup lang="ts">
import { useRoute, useRouter } from "vue-router"
import { computed, onUnmounted, ref, watch } from "vue"
import { useStockStore } from "@/stores/stock"

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

watch(isModalOpen, (newValue) => {
  if (newValue) {
    document.body.classList.add("overflow-hidden")
  } else {
    document.body.classList.remove("overflow-hidden")
  }
})

onUnmounted(() => {
  document.body.classList.remove("overflow-hidden")
})

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
const storeStock = useStockStore()
const router = useRouter()
const route = useRoute()
const stockId = idMap[route.params.stockName as string]

const totalSellAmount = computed(() => {
  return sellQuantity.value * props.price
})

const preventNegativeQuantity = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.valueAsNumber < 0) {
    sellQuantity.value = 0
  } else if (input.valueAsNumber > Quantity.value) {
    sellQuantity.value = Quantity.value
  }
}

const handleSell = async () => {
  storeStock.postSellCurrent(stockId, sellQuantity.value)
  router.go(0)
}
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="flex justify-between">
      <div class="m-4">
        <p>보유 수량</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p>{{ Quantity }} 주</p>
      </div>
    </div>

    <div class="flex justify-between">
      <div class="m-4">
        <p>매도 수량</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <input
          class="bg-gray-200 mx-1 w-12 text-center rounded"
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
        <p>총 매도액</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p>{{ totalSellAmount.toLocaleString() }} 알</p>
      </div>
    </div>

    <div class="flex justify-center">
      <button
        @click="openModal"
        class="m-4 rounded-lg p-1 px-3 text-white"
        :class="
          sellQuantity > 0
            ? 'bg-blue-500 hover:bg-blue-600 cursor-pointer'
            : 'cursor-not-allowed bg-blue-200'
        "
        :disabled="sellQuantity === 0"
      >
        매도
      </button>
    </div>
    <!-- 모달 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white w-1/3 p-6 rounded-lg shadow-lg">
        <h2 class="text-2xl font-semibold mb-4 text-center">현재가 매도</h2>
        <div class="flex justify-between">
          <p class="m-4">매도 수량</p>
          <p class="m-4">{{ sellQuantity }} 주</p>
        </div>
        <div class="flex justify-between">
          <p class="m-4">매도 총액</p>
          <p class="m-4">{{ totalSellAmount.toLocaleString() }} 알</p>
        </div>
        <div class="flex justify-center">
          <button
            @click="handleSell"
            class="m-4 rounded-lg p-1 px-3 text-white cursor-pointer bg-blue-500 hover:bg-blue-600"
          >
            매도
          </button>
          <button
            @click="closeModal"
            class="m-4 rounded-lg p-1 px-3 text-white bg-gray-300 cursor-pointer hover:bg-gray-400"
          >
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
