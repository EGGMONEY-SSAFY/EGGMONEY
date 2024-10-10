<script setup lang="ts">
import BoxUserInfo from "@/components/box/BoxUserInfo.vue"
import HeldStocksChart from "@/components/cart/HeldStocksChart.vue"
import { useVariableStore } from "@/stores/variable"
import BoxStock from "@/components/box/BoxStock.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useStockStore } from "@/stores/stock"
import { onMounted, ref } from "vue"
import { useUserStore } from "@/stores/user"
import type { User } from "@/stores/user"

interface StockList {
  stockId: number
  stockItem: string
  updatedDate: string
  price: number
  gap: number
  ratio: number
}
const store = useVariableStore()
const storeStock = useStockStore()
const stockList = ref<StockList[]>([])

onMounted(async () => {
  const fetchedStockPrice = await storeStock.getStockPrice()
  stockList.value = fetchedStockPrice
  store.setTitle("증권")
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
</script>

<template>
  <div>
    <NavBarTab />
    <div v-if="userStore.user && userStore.user.role === `부모`" class="flex justify-between p-3">
      <select
        v-model="userSelect"
        class="block p-2 text-sm text-black bg-white border border-gray-200 rounded-lg focus:ring-gray-500 focus:border-gray-500"
      >
        <option
          v-for="child in userStore.children"
          :value="child"
          :key="child.userId"
          class="text-black"
        >
          {{ child.name }}
        </option>
      </select>
    </div>
    <BoxUserInfo :userId="userSelect?.userId"/>
    <HeldStocksChart />
    <div v-if="stockList">
      <div v-for="stock in stockList" :key="stock.stockItem">
        <BoxStock :stock="stock" />
      </div>
    </div>
  </div>
</template>
