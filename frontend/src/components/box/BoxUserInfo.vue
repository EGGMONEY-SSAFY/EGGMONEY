<script setup lang="ts">
import { useStockStore } from "@/stores/stock"
import { useUserStore, type User } from "@/stores/user"
import { onMounted, ref } from "vue"

const StockStore = useStockStore()
const myStock = ref()
const myStockB = ref(0)
const myStockI = ref(0)
const myStockV = ref(0)
const props = defineProps({
  userId: Number
})
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
  myStock.value = await StockStore.getMoneyInfo(userSelect.value?.userId)
  myStockB.value = await myStock.value.balance
  myStockI.value = await myStock.value.investablePrice
  myStockV.value = await myStock.value.totalStockPrice
})
</script>

<template>
  <div class="flex flex-col gap-2 m-4 bg-white rounded-lg shadow">
    <div class="mx-6 mt-4">
      <span>현재 잔액 : </span>
      <span class="font-bold">{{ myStockB.toLocaleString() }} 알</span>
    </div>
    <div class="mx-6">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">{{ myStockI.toLocaleString() }} 알</span>
    </div>
    <div class="mx-6 mb-4">
      <span>총 주식 금액 : </span>
      <span class="font-bold">{{ myStockV.toLocaleString() }} 알</span>
    </div>
  </div>
</template>
