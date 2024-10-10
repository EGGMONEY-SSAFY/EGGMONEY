<script setup lang="ts">
import { useStockStore } from "@/stores/stock"
import { useUserStore, type User } from "@/stores/user";
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"

const nameMap: Record<string, string> = {
  KOSPI: "코스피",
  KOSDAQ: "코스닥",
  AUTOMOTIVE: "자동차",
  SEMICONDUCTOR: "반도체",
  HEALTHCARE: "헬스케어",
  BANKING: "은행",
  ENERGY_CHEMICAL: "에너지화학",
  STEEL: "철강",
  CONSTRUCTION: "건설",
  TRANSPORTATION: "운송",
  MEDIA_ENTERTAINMENT: "미디어",
  IT: "IT",
  UTILITIES: "유틸리티",
}

interface StockList {
  stockId: number
  stockItem: string
  updatedDate: string
  price: number
  gap: number
  ratio: number
}

const myStock = ref()
const myStockB = ref()
const myStockI = ref()
const storeStock = useStockStore()
const stockList = ref<StockList[]>([])
const route = useRoute()
const name = route.params.stockName as string


const userSelect = ref<User | null>(null)
const userStore = useUserStore()
onMounted(async () => {
  // 유저 조회해서 유저 정보(역할, 자식 목록) 가져오기

  // console.log("가져온 familyId:", userStore.familyId)

  //  자녀가 로그인한 경우
  if (userStore.user && userStore.user.role === "자녀") {
    userSelect.value = userStore.user
  }

  // 부모가 로그인한 경우
  else {
    // console.log("부모 로그인")
    // 자식이 없다면 null, 자식이 있다면 첫 번째 자식으로 userSelect
    if (userSelect.value == null && userStore.children.length > 0) {
      // console.log("자식 1명 이상")
      userSelect.value =
        userStore.children && userStore.children.length > 0 ? userStore.children[0] : null
    } else {
      // console.log("가족 미구성")
    }
  }
})





onMounted(async () => {
  const fetchedStockPrice = await storeStock.getStockPrice()
  stockList.value = fetchedStockPrice
  myStock.value = await storeStock.getMoneyInfo(userSelect.value?.userId)
  myStockB.value = myStock.value.balance.toLocaleString()
  myStockI.value = myStock.value.investablePrice.toLocaleString()
})

const matchingStock = computed(() => {
  return stockList.value.find((stock) => stock.stockItem === name)
})
</script>

<template>
  <div class="flex flex-col gap-2 m-4 bg-white rounded-lg shadow">
    <div class="mx-6 mt-4">
      <span>현재 잔액 : </span>
      <span class="font-bold">{{ myStockB }} 알</span>
    </div>
    <div class="mx-6">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">{{ myStockI }} 알</span>
    </div>
    <div class="mx-6 mb-4">
      <span>{{ nameMap[name] }} 가격 : </span>
      <div v-if="matchingStock" class="inline">
        <span class="font-bold">{{ matchingStock.price.toLocaleString() }} 알</span>
      </div>
    </div>
  </div>
</template>
