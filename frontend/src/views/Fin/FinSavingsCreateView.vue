<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import InputMoney from "@/components/input/InputMoney.vue"
import { useFinStore } from "@/stores/fin"
import { useUserStore } from "@/stores/user"
import { onMounted, ref } from "vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const finStore = useFinStore()
const userStore = useUserStore()

const productId = Number(route.query.productId)
const productName = ref("")
const savingsRate = ref(0)
const savingsDate = ref(0)
const maxPrice = ref(0)

onMounted(() => {
  const selectedProduct = finStore.savingsProducts.find((product) => product.id === productId)
  if (selectedProduct) {
    productName.value = selectedProduct.productName
    savingsRate.value = Number(selectedProduct.savingsRate)
    savingsDate.value = Number(selectedProduct.savingsDate)
    maxPrice.value = Number(selectedProduct.maxPrice)
  }
})

const money = ref(0)

const updateMoney = (value: number) => {
  money.value = value
}

const router = useRouter()
const handleClick = () => {
  console.log(userStore.user?.userId)
  if (userStore.user?.userId) {
    console.log("setSavings", money.value)
    finStore.setSavingsCreateInfo(money.value, productId, userStore.user?.userId)
    console.log(finStore.savingsCreateInfo)
  }

  router.push({
    name: "FinSavingsCreateDetailView",
    query: {
      productId: productId,
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
        달마다 <InputMoney @updateMoney="updateMoney" :maxPrice="maxPrice"></InputMoney> 알을
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
