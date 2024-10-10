<script setup lang="ts">
import BoxDepositProduct from "@/components/box/BoxDepositProduct.vue"
import BoxSavingsProduct from "@/components/box/BoxSavingsProduct.vue"
import { useVariableStore } from "@/stores/variable"
import axios from "axios"
import { ref, onMounted, watch } from "vue"
import type { depositProducts, savingsProducts } from "@/stores/fin"
import type { User } from "@/stores/user"
import { useAssetStore } from "@/stores/asset"
import IconExplanation from "@/components/icons/IconExplanation.vue"

const varStore = useVariableStore()
const recommendDeposit = ref<depositProducts | null>(null)
const recommendSavings = ref<savingsProducts | null>(null)
const assetStore = useAssetStore()
const props = defineProps<{ user: User }>()

const total_money = ref<number>(0)
const deposit_money = ref<number>(0)
const savings_money = ref<number>(0)
const stock_money = ref<number>(0)

const depositRate = ref<number>(0)
const savingsRate = ref<number>(0)
const stockRate = ref<number>(0)
const bestRate = ref<number>(0)

// 사용자 추천 데이터 조회
const getRecommend = function (
  deposit_ratio: Number,
  savings_ratio: Number,
  stock_ratio: Number
): Promise<void> {
  return axios({
    method: "post",
    url: `https://oracle1.mypjt.xyz/api/v1/recommend`,
    data: {
      deposit_ratio: deposit_ratio,
      savings_ratio: savings_ratio,
      stock_ratio: stock_ratio,
    },
  })
    .then((res) => {
      getDepsoitProduct(res.data.recommend.deposit_recommendation)
      getSavingsProduct(res.data.recommend.savings_recommendation)
    })
    .catch((err) => {
      console.error(err)
    })
}

// 단일 예금 조회
const getDepsoitProduct = function (id: number): Promise<void> {
  return axios({
    method: "get",
    url: `/api/v1/fin/deposit/product/${id}`,
  })
    .then((res) => {
      recommendDeposit.value = res.data
      console.log(res.data)
    })
    .catch((err) => {
      console.error(err)
    })
}

// 단일 적금 조회
const getSavingsProduct = function (id: number): Promise<void> {
  return axios({
    method: "get",
    url: `/api/v1/fin/savings/product/${id}`,
  })
    .then((res) => {
      recommendSavings.value = res.data
    })
    .catch((err) => {
      console.error(err)
    })
}

watch(
  () => props.user,
  async (newUser) => {
    recommendDeposit.value = null
    if (newUser) {
      await assetStore.getPort(newUser.userId)
      deposit_money.value = assetStore.deposit ? Number(assetStore.deposit) : 0
      savings_money.value = assetStore.savings ? Number(assetStore.savings) : 0
      stock_money.value = assetStore.stock ? Number(assetStore.stock) : 0
      total_money.value = savings_money.value + deposit_money.value + stock_money.value
      // total_money가 0이 아닌 경우에만 요청
      if (total_money.value > 0) {
        depositRate.value = Number((deposit_money.value / total_money.value).toFixed(2))
        savingsRate.value = Number((savings_money.value / total_money.value).toFixed(2))
        stockRate.value = Number((stock_money.value / total_money.value).toFixed(2))
        bestRate.value = Math.max(depositRate.value, savingsRate.value, stockRate.value)
        await getRecommend(depositRate.value, savingsRate.value, stockRate.value)
      } else {
        bestRate.value = 0
        await getRecommend(0, 0, 0)
      }
    }
  },
  { deep: true }
)

onMounted(async () => {
  varStore.setTitle("내게 맞는 상품")
  await assetStore.getPort(props.user.userId)
  deposit_money.value = assetStore.deposit ? Number(assetStore.deposit) : 0
  savings_money.value = assetStore.savings ? Number(assetStore.savings) : 0
  stock_money.value = assetStore.stock ? Number(assetStore.stock) : 0
  total_money.value = savings_money.value + deposit_money.value + stock_money.value

  // total_money가 0인 경우
  if (total_money.value > 0) {
    depositRate.value = Number((deposit_money.value / total_money.value).toFixed(2))
    savingsRate.value = Number((savings_money.value / total_money.value).toFixed(2))
    stockRate.value = Number((stock_money.value / total_money.value).toFixed(2))
    bestRate.value = Math.max(depositRate.value, savingsRate.value, stockRate.value)
    await getRecommend(depositRate.value, savingsRate.value, stockRate.value)
  } else {
    bestRate.value = 0
    await getRecommend(0, 0, 1)    
  }
})
</script>

<template>
  <div
    v-if="props.user"
    class="grid grid-flow-row grid-cols-1 justify-between mt-4 m-3 bg-white rounded-lg shadow p-3"
  >
    <div class="my-auto">
      <div class="flex items-center">
        <IconExplanation></IconExplanation>
        <h1 class="ps-3">{{ props.user.name }} 회원님과 유사한 사용자들의</h1>
      </div>
      <h1 class="ps-7 pt-1">
        <span class="underline underline-offset-2">가입 상품</span>은 아래와 같아요!
      </h1>
    </div>
    <hr class="mt-4" />
    <div class="mt-4 px-3 flex-grow items-center font-bold text-xl justify-between pb-2">
      <h1>❓ &nbsp;내 유형은?</h1>
      <div class="mt-6 text-xl font-bold flex items-center justify-center" v-if="bestRate === 0">
        <img src="@/assets/asset/type_newbie.png" alt="newbie" style="width: 50px; height: 50px" />
        <h1 class="text-green-700 ps-4">초보자</h1>
      </div>
      <div
        class="mt-6 text-xl font-bold flex items-center justify-center"
        v-if="bestRate != 0 && bestRate === depositRate"
      >
        <img src="@/assets/asset/type_deposit.png" alt="newbie" style="width: 50px; height: 50px" />
        <h1 class="text-blue-700 ps-4">안전 제일</h1>
      </div>
      <div
        class="mt-6 text-xl font-bold flex items-center justify-center"
        v-if="bestRate != 0 && bestRate === savingsRate"
      >
        <img src="@/assets/asset/type_savings.png" alt="newbie" style="width: 50px; height: 50px" />
        <h1 class="text-main-color ps-4">꾸준한 저축가</h1>
      </div>
      <div
        class="mt-6 text-xl font-bold flex items-center justify-center"
        v-if="bestRate != 0 && bestRate === stockRate"
      >
        <img src="@/assets/asset/type_stock.png" alt="newbie" style="width: 50px; height: 50px" />
        <h1 class="text-red-500 ps-4">미래의 투자가</h1>
      </div>
    </div>
  </div>
  <div
    v-if="recommendDeposit && recommendSavings"
    class="grid grid-flow-row grid-cols-1 justify-center"
  >
    <div>
      <div
        class="bg-blue-500 m-4 text-lg font-bold text-white rounded-xl p-2 ps-4 items-center flex"
      >
        <img src="@/assets/asset/account_2.png" alt="newbie" style="width: 40px; height: 40px" />
        <h1 class="text-white ps-5">예금 추천 상품</h1>
      </div>
      <BoxDepositProduct v-if="recommendDeposit" :product="recommendDeposit" />
    </div>
    <div>
      <div
        class="bg-yellow-600 m-4 text-lg font-bold text-white rounded-xl p-2 ps-4 items-center flex"
      >
        <img src="@/assets/asset/savings_2.png" alt="newbie" style="width: 40px; height: 40px" />
        <h1 class="text-white ps-5">적금 추천 상품</h1>
      </div>
      <BoxSavingsProduct
        v-if="recommendSavings"
        :product="recommendSavings"
        :savingsDate="recommendSavings.savingsDate"
        :savingsRate="recommendSavings.savingsRate"
        :maxPrice="recommendSavings.maxPrice"
      />
    </div>
  </div>
  <div v-else class="pt-20 grid grid-cols-1 grid-flow-row justify-center gap-3 p-3">
    <div>
      <img src="@/assets/asset/loading.gif" alt="">
      <h1></h1>
    </div>
  </div>
</template>
