<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import InputMoney from "@/components/input/InputMoney.vue"
import { ref } from "vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
// interface Product {
//   productId: number
//   productName: string
//   depositRate: number
//   depositDate: number
// }

const productId = route.query.productId
const productName = route.query.productName
const savingsRate = Number(route.query.savingsRate)
const savingsDate = Number(route.query.savingsDate)
const maxPrice = Number(route.query.maxPrice) / savingsDate

const money = ref(0)

const updateMoney = (value: number) => {
  money.value = value
}

const router = useRouter()
const handleClick = () => {
  router.push({
    name: "FinSavingsCreateDetailView", // 추후 간편 비밀번호로 변경되어야 한다.
    query: {
      money: money.value,
      productId: productId,
      productName: productName,
      savingsDate: savingsDate,
      savingsRate: savingsRate,
      maxPrice: maxPrice,
    },
  })
}
</script>

<template>
  <!--  -->
  <div class="flex flex-col m-4 h-[75vh]">
    <div class="flex justify-center items-center">
      <div class="flex justify-center m-2">
        <IconExplanation></IconExplanation>
      </div>
      <div class="text-gray-600 font-bold text-sm">선택하신 상품은 {{ productName }}입니다</div>
    </div>

    <div class="m-4">
      <div class="m-4">입금하실 금액을 선택해주세요</div>
      <div class="m-4">
        <span class="font-bold">{{ savingsDate }}개월</span> 동안 연이율
        <span class="font-bold"> {{ savingsRate.toFixed(1) }}%</span>로
      </div>

      <div class="m-4">
        달마다 <InputMoney @updateMoney="updateMoney" :max-price="maxPrice"></InputMoney> 알을
      </div>
      <div class="m-4">적금할 예정이에요</div>
    </div>

    <!-- 다음으로 넘어가는 버튼 : FinView를 간편비밀번호로 넘기고, 해당 값들은 route.query에 들어있다.-->
    <div class="mt-auto">
      <div class="text-center">
        <NextButton routeName="FinSavingsCreateDetailView" @click="handleClick"></NextButton>
      </div>
    </div>
  </div>
</template>
