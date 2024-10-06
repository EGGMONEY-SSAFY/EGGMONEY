<!-- 예금 생성 페이지
    -> 다음 버튼 아래로 내리기
    -> 돈의 단위마다 , 넣기(string인 경우만 가능한데 number형식이라 안됨)
-->

<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import InputMoney from "@/components/input/InputMoney.vue"
import { useFinStore } from "@/stores/fin"
import { useUserStore } from "@/stores/user"
import { computed, onMounted, ref } from "vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const userStore = useUserStore()
const finStore = useFinStore()

const productId = Number(route.query.productId)
const productName = ref("")
const depositRate = ref<number>(0)
const depositDate = ref<number>(0)

const money = ref(0)

const updateMoney = (value: number) => {
  money.value = value
}

const formattedMoney = computed(() => {
  return money.value.toLocaleString()
})

onMounted(() => {
  const selectedProduct = finStore.depositProducts.find(
    (product) => product.productId === productId
  )
  if (selectedProduct) {
    productName.value = selectedProduct.productName
    depositRate.value = Number(selectedProduct.depositRate)
    depositDate.value = Number(selectedProduct.depositDate)
  }
})

const router = useRouter()
const handleClick = () => {
  if (userStore.user?.userId) {
    finStore.setDepositCreateInfo(money.value, productId, userStore.user?.userId)
  }

  router.push({
    name: "FinDepositCreateDetailView",
  })
}
</script>

<template>
  <!-- 바로 밑 justify가 안먹음 -->
  <div class="h-[78vh] flex flex-col m-4 justify-around">
    <div class="flex justify-center items-center">
      <div class="flex justify-center m-2">
        <IconExplanation></IconExplanation>
      </div>
      <div class="text-gray-600 font-bold text-sm">
        선택하신 예금 상품은 <span class="font-bold text-base">{{ productName }}</span
        >입니다
      </div>
    </div>
    <div class="m-4 mb-16 pl-4">
      <div class="m-2">입금하실 금액을 선택해주세요</div>
      <div class="m-2">
        <span class="font-bold">{{ depositDate }}개월</span> 동안
        <span class="font-bold">연이율 {{ depositRate.toFixed(1) }}%</span>로
      </div>

      <div class="my-4"><InputMoney @updateMoney="updateMoney"></InputMoney> 알을</div>
      <div class="m-2">예금해 둘 예정이에요</div>
    </div>
    <div class="m-4 text-center">
      <NextButton routeName="FinDepositCreateDetailView" @click="handleClick"></NextButton>
    </div>
  </div>
</template>
