<script setup lang="ts">
import SimplePinPadComponent from "@/components/login/SimplePinPadComponent.vue"
import { useStockStore } from "@/stores/stock"
import { computed, onUnmounted, ref, watch } from "vue"
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
  Quantity: {
    type: Number,
    default: 0,
  },
})

const Quantity = ref(props.Quantity)
const sellQuantity = ref(0)
const sellPrice = ref(props.price + 1)
const router = useRouter()
const route = useRoute()
const stockId = idMap[route.params.stockName as string]
const storeStock = useStockStore()

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

const handleSell = async () => {
  await storeStock.postSellOrder(stockId, sellPrice.value, sellQuantity.value)
  router.push("/stock/home")
}
const showFailModal = ref(false)
const isPinpad = ref(false)
const pinpadOpen = () => {
  isPinpad.value = true
}
const remainingTime = ref(5)

const handleSuccess = () => {
  handleSell()
}
const handleFail = () => {
  // 비밀번호 검증 실패 시 처리 로직
  console.log("비밀번호 검증 실패")

  showFailModal.value = true // 모달을 띄움

  // 5초 후 비밀번호 재설정 페이지로 이동
  // TODO: 비밀번호 재설정 페이지 name 알아내서 변경하기.
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
      <button
        @click="openModal"
        class="p-1 px-3 m-4 text-white rounded-lg"
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
      class="fixed inset-0 z-10 flex items-center justify-center bg-black bg-opacity-50"
    >
      <div class="p-6 bg-white rounded-lg shadow-lg">
        <h2 class="mb-4 text-2xl font-semibold text-center">지정가 매도</h2>
        <div class="flex justify-between">
          <p class="m-4">매도 가격</p>
          <p class="m-4">{{ sellPrice.toLocaleString() }} 알</p>
        </div>
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
            @click="pinpadOpen"
            class="p-1 px-3 m-4 text-white bg-blue-500 rounded-lg cursor-pointer hover:bg-blue-600"
          >
            매도
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
