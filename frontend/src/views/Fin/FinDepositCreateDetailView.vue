<!-- 예금 가입 시 이용약관 페이지 -->
<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import NotFoundComponent from "@/components/404/NotFoundComponent.vue"
import { useFinStore, type depositCreateInfo } from "@/stores/fin"
import { computed, onMounted, ref } from "vue"
import { useRouter } from "vue-router"
import { useUserStore } from "@/stores/user"

const router = useRouter()
const finStore = useFinStore()
const userStore = useUserStore()
const createInfo = ref<depositCreateInfo | null>(null)
createInfo.value = finStore.depositCreateInfo
const productId = Number(createInfo.value?.depositProductId)
const productName = ref("")
const depositRate = ref(0)
const depositDate = ref(0)

onMounted(() => {
  const depositProduct = finStore.depositProducts.find((product) => product.productId === productId)
  if (depositProduct) {
    productName.value = depositProduct.productName
    depositRate.value = Number(depositProduct.depositRate)
    depositDate.value = Number(depositProduct.depositDate)
  }
})

const repayment = computed(() => {
  if (productId !== null) {
    const depositMoney = Number(createInfo.value?.depositMoney)

    return Math.round(
      depositMoney + (((depositMoney * depositRate.value) / 100) * depositDate.value) / 12
    ).toLocaleString()
  }

  return "0" // Default repayment value when data is invalid
})
const handleClick = () => {
  router.push({
    name: "FinPinPadView",
  })
}
</script>

<template>
  <div class="m-4 mb-16">
    <!--  -->
    <div v-if="userStore.user?.role === '자녀'" class="h-[78vh] flex flex-col m-4 justify-around">
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
            <span v-if="createInfo?.depositMoney" class="font-bold">{{
              createInfo.depositMoney.toLocaleString()
            }}</span>
            알을 예금해 둘 예정이에요
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

    <div v-else>
      <NotFoundComponent></NotFoundComponent>
    </div>
  </div>
</template>
