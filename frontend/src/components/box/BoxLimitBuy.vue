<script setup lang="ts">
import SimplePinPadComponent from "@/components/login/SimplePinPadComponent.vue"
import { useStockStore } from "@/stores/stock"
import { computed, onMounted, onUnmounted, ref, watch } from "vue"
import { useRoute, useRouter } from "vue-router"

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
})

interface StockList {
  stockId: number
  stockItem: string
  updatedDate: string
  price: number
  gap: number
  ratio: number
}

const buyQuantity = ref(0)
const buyPrice = ref(props.price - 1)
const storeStock = useStockStore()
const myStock = ref()
const myStockB = ref()
const myStockI = ref()
const stockList = ref<StockList[]>([])
const router = useRouter()
const route = useRoute()
const stockId = idMap[route.params.stockName as string]

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

const handleBuy = async () => {
  await storeStock.postBuyOrder(stockId, buyPrice.value, buyQuantity.value)
  await router.push("/stock/home")
}

const showFailModal = ref(false)
const isPinpad = ref(false)
const pinpadOpen = () => {
  isPinpad.value = true
}
const remainingTime = ref(5)

const handleSuccess = () => {
  handleBuy()
}
const handleFail = () => {
  // 비밀번호 검증 실패 시 처리 로직

  showFailModal.value = true // 모달을 띄움

  // 5초 후 비밀번호 재설정 페이지로 이동
  const countdownInterval = setInterval(() => {
    remainingTime.value--
    if (remainingTime.value <= 0) {
      clearInterval(countdownInterval) // 카운트다운 종료
      router.push({ name: "WonAuthView" }) // 1원인증 페이지로 넘기기()
    }
  }, 1000)
}
</script>

<template>
  <div>
    <div class="flex flex-col m-4 bg-white rounded-lg shadow">
      <div class="flex justify-between">
        <div class="m-4">
          <p>매수 수량</p>
        </div>
        <div class="flex items-center justify-center m-4">
          <input
            class="w-12 mx-1 text-center bg-gray-200 rounded"
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
        <div class="flex items-center justify-center m-4">
          <input
            class="w-24 mx-1 text-center bg-gray-200 rounded"
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
        <div class="flex items-center justify-center m-4">
          <p>{{ totalBuyAmount }}알</p>
        </div>
      </div>

      <div class="flex justify-between">
        <div class="m-4">
          <p>매수 후 잔액</p>
        </div>
        <div class="flex items-center justify-center m-4">
          <p :class="postBuyAmt1 < 0 ? 'text-red-500' : ''">{{ postBuyAmt1.toLocaleString() }}</p>
        </div>
      </div>

      <div class="flex justify-between">
        <div class="m-4">
          <p>매수 후 투자 가능 금액</p>
        </div>
        <div class="flex items-center justify-center m-4">
          <p :class="postBuyAmt < 0 ? 'text-red-500' : ''">{{ postBuyAmt.toLocaleString() }}</p>
        </div>
      </div>

      <div class="flex justify-center">
        <button
          @click="openModal"
          class="p-1 px-3 m-4 text-white rounded-lg"
          :class="
            postBuyAmt < 0 || buyQuantity == 0 || postBuyAmt1 < 0
              ? 'cursor-not-allowed bg-red-200'
              : 'bg-red-500 cursor-pointer hover:bg-red-600'
          "
          :disabled="postBuyAmt < 0 || buyQuantity == 0 || postBuyAmt1 < 0"
        >
          매수
        </button>
      </div>
    </div>
    <!-- 모달 -->
    <div
      v-if="isModalOpen"
      class="fixed inset-0 z-10 flex items-center justify-center bg-black bg-opacity-50"
    >
      <div class="p-6 bg-white rounded-lg shadow-lg">
        <h2 class="mb-4 text-2xl font-semibold text-center">지정가 매수</h2>
        <div class="flex justify-between">
          <p class="m-4">매수 가격</p>
          <p class="m-4">{{ buyPrice.toLocaleString() }} 알</p>
        </div>
        <div class="flex justify-between">
          <p class="m-4">매수 수량</p>
          <p class="m-4">{{ buyQuantity }} 주</p>
        </div>
        <div class="flex justify-between">
          <p class="m-4">매수 총액</p>
          <p class="m-4">{{ totalBuyAmount.toLocaleString() }} 알</p>
        </div>
        <div class="flex justify-center">
          <button
            @click="pinpadOpen"
            class="p-1 px-3 m-4 text-white bg-red-500 rounded-lg cursor-pointer hover:bg-red-600"
          >
            매수
          </button>
          <button
            @click="closeModal"
            class="p-1 px-3 m-4 text-white bg-gray-300 rounded-lg cursor-pointer hover:bg-gray-400"
          >
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- 비밀번호 -->
  <div v-if="isPinpad">
    <div class="fixed inset-0 z-20">
      <SimplePinPadComponent
        @pin-success="handleSuccess"
        @pinFail="handleFail"
      ></SimplePinPadComponent>
    </div>
    <!-- 비밀번호 모달 -->
    <div
      v-if="showFailModal"
      class="fixed inset-0 z-30 flex items-center justify-center bg-gray-800 bg-opacity-75"
    >
      <div class="max-w-sm p-6 text-center bg-white rounded-lg">
        <p class="text-lg font-semibold text-gray-900">비밀번호 인증 실패</p>
        <p>{{ remainingTime }}초 후 비밀번호 재설정 페이지로 이동합니다.</p>
      </div>
    </div>
  </div>
</template>
