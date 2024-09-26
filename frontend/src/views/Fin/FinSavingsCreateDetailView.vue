<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import { useRoute, useRouter } from "vue-router"

const router = useRouter()
const route = useRoute()

const productId = Number(route.query.productId)
const productName = route.query.productName
const savingsRate = Number(route.query.savingsRate)
const savingsDate = Number(route.query.savingsDate)
const money = Number(route.query.money)

const repayment = (
  money * savingsDate +
  (((money * savingsRate) / 100) * savingsDate) / 12
).toFixed(0)

const handleClick = () => {
  router.push({
    name: "FinView", // 추후 간편 비밀번호로 변경되어야 한다.
    query: {
      money: money,
      productId: productId,
      productName: productName,
      savingsDate: savingsDate,
      savingsRate: savingsRate,
    },
  })
}
</script>

<template>
  <div class="m-4">
    <!--  -->
    <div class="flex flex-col m-4 justify-between">
      <div class="flex justify-center items-center">
        <div class="flex justify-center m-2">
          <IconExplanation></IconExplanation>
        </div>
        <div class="text-gray-600 font-bold text-sm">선택하신 상품은 {{ productName }}입니다</div>
      </div>
      <div class="mb-0 m-4">
        <div class="font-bold">※ 상품정보</div>
        <div class="bg-white m-2 p-3 rounded-lg shadow">
          <div class="m-2 text-sm">해당 상품은</div>
          <div class="m-2 text-sm">
            <span class="font-bold">{{ savingsDate }}개월</span> 동안 연이율
            <span class="font-bold">{{ savingsRate.toFixed(1) }}%</span>로
          </div>
          <div class="m-2 text-sm">
            <span class="font-bold">{{ money }}</span> 알을 예금해 둘 예정이에요
          </div>
        </div>
      </div>

      <div class="mt-2 m-4">
        <div class="font-bold">※ 적금미납입</div>
        <div class="bg-white m-2 p-3 rounded-lg shadow">
          <div class="m-2 text-sm">해당 상품의 <span class="font-bold">적금 납입일</span>에</div>
          <div class="m-2 text-sm">{{ money }}알을 납입하지 않으시면</div>
          <div class="m-2 text-sm">만기일이 <span class="font-bold">한달</span> 연장됩니다.</div>
        </div>
      </div>

      <div class="mt-2 m-4">
        <div class="font-bold">※ 해지정보</div>
        <div class="bg-white m-2 p-3 rounded-lg shadow">
          <div class="m-2 text-sm">해당 상품의 <span class="font-bold">만기일 도달 전</span>에</div>
          <div class="m-2 text-sm">상품을 해지하시면</div>
          <div class="m-2 text-sm">
            해지일까지의 금액에
            <span class="font-bold">{{ (savingsRate - 2.0).toFixed(1) }}%</span>가
          </div>
          <div class="m-2 text-sm">적용된 금액을 지급합니다.</div>
        </div>
      </div>
    </div>

    <div class="m-4 mb-7">
      <div class="m-4 text-red-500">
        ※ 예상 만기액은 <span class="font-bold text-red-500">{{ repayment }}원</span> 입니다.
      </div>
    </div>

    <!-- 다음으로 넘어가는 버튼 : FinView를 간편비밀번호로 넘기고, 해당 값들은 route.query에 들어있다.-->
    <div class="bottom-2 text-center">
      <NextButton routeName="FinView" @click="handleClick"></NextButton>
    </div>
  </div>
</template>
