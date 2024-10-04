<!-- 예금 가입 시 이용약관 페이지 -->
<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import { useRoute, useRouter } from "vue-router"

const router = useRouter()
const route = useRoute()

const productId = Number(route.query.productId)
const productName = route.query.productName
const depositRate = Number(route.query.depositRate)
const depositDate = Number(route.query.depositDate)
const money = Number(route.query.money)

const repayment = (money + (((money * depositRate) / 100) * depositDate) / 12).toFixed(0)

const handleClick = () => {
  router.push({
    name: "FinPinPadView", // 추후 간편 비밀번호로 변경되어야 한다.
    query: {
      money: money,
      productId: productId,
      productName: productName,
      depositDate: depositDate,
      depositRate: depositRate,
    },
  })
}
</script>

<template>
  <div class="m-4">
    <!--  -->
    <div class="h-[78vh] flex flex-col m-4 justify-around">
      <div class="flex justify-center items-center">
        <div class="flex justify-center m-2">
          <IconExplanation></IconExplanation>
        </div>
        <div class="text-gray-600 font-bold text-sm">선택하신 상품은 {{ productName }}입니다</div>
      </div>
      <div class="m-4">
        <div class="font-bold">※ 상품정보</div>
        <div class="bg-white m-2 p-3 rounded-lg shadow">
          <div class="m-2 text-sm">해당 상품은</div>
          <div class="m-2 text-sm">
            <span class="font-bold">{{ depositDate }}개월</span> 동안 연이율
            <span class="font-bold">{{ depositRate.toFixed(1) }}%</span>로
          </div>

          <div class="m-2 text-sm">
            <span class="font-bold">{{ money }}</span> 알을 예금해 둘 예정이에요
          </div>
        </div>
      </div>
      <div class="m-4">
        <div class="font-bold">※ 해지정보</div>
        <div class="bg-white m-2 p-3 rounded-lg shadow">
          <div class="m-2 text-sm">해당 상품의 <span class="font-bold">만기일 도달 전</span>에</div>
          <div class="m-2 text-sm">상품을 해지하시면</div>
          <div class="m-2 text-sm">
            해지일까지의 금액에
            <span class="font-bold">{{ (depositRate - 2.0).toFixed(1) }}%</span>가
          </div>
          <div class="m-2 text-sm">적용된 금액을 지급합니다.</div>
        </div>
      </div>

      <div class="m-4 text-red-500">
        ※ 예상 만기액은 <span class="font-bold text-red-500">{{ repayment }}원</span> 입니다.
      </div>

      <!-- 다음으로 넘어가는 버튼 -->
      <div class="mt-4 text-center">
        <NextButton routeName="FinPinPadView" @click="handleClick"></NextButton>
      </div>
    </div>
  </div>
</template>
