<script setup lang="ts">
import BoxAccount from "@/components/box/BoxAccount.vue"
import HeldAssetChart from "@/components/cart/HeldAssetChart.vue"
import HeldAssetLogChart from "@/components/cart/HeldAssetLogChart.vue"
import { useAssetStore } from "@/stores/asset"
import { onMounted, watch, ref } from "vue"
import type { User } from "@/stores/user"
import type { Analytics, TradeData } from "@/stores/asset"
import { useVariableStore } from "@/stores/variable"
import { useRouter } from "vue-router"

const assetStore = useAssetStore()
const varStore = useVariableStore()

const props = defineProps<{ user: User }>()

// 데이터 변환
const analytics = ref<Analytics | null>({
  입출금통장: null,
  예금: null,
  적금: null,
  대출: null,
  주식: null,
})

const accountHistory = ref<TradeData[]>([])
const router = useRouter()
function goRecommend() {
  router.push({ name: "MyRecommendView" })
}

// 유저가 변경될 때 비동기 작업을 처리하고 analytics 값을 갱신
watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await assetStore.getPort(newUser.userId)

      analytics.value = {
        입출금통장: assetStore.mainAccount,
        예금: assetStore.deposit,
        적금: assetStore.savings,
        대출: assetStore.loan,
        주식: assetStore.stock,
      }
      await assetStore.getAccountChartLog(newUser.userId)
      accountHistory.value = assetStore.logs
    }
  },
  { deep: true }
)

onMounted(async () => {
  varStore.setTitle("자산")
  await assetStore.getPort(props.user.userId)
  analytics.value = {
    입출금통장: assetStore.mainAccount,
    예금: assetStore.deposit,
    적금: assetStore.savings,
    대출: assetStore.loan,
    주식: assetStore.stock,
  }
  await assetStore.getAccountChartLog(props.user.userId)
  accountHistory.value = assetStore.logs
})
</script>
<template>
  <div class="grid">
    <BoxAccount :user="user" :analytics="analytics" />
    <div
      class="grid grid-flow-row grid-cols-1 gap-4 p-3 m-4 bg-white rounded-lg shadow border-8 border-green-300"
      @click="goRecommend"
      role="button"
    >
      <div class="grid grid-flow-row grid-cols-1 gap-6 px-3 py-3">
        <div class="flex">
          <img src="@/assets/asset/recommend.png" alt="main-account" style="width: 51px" />
          <div class="flex items-center justify-end w-full px-4 pe-2">
            <h1 class="flex-grow font-bold text-lg text-center">내게 맞는 상품 추천 &nbsp🏆</h1>
          </div>
        </div>
      </div>
    </div>
    <HeldAssetChart :analytics="analytics" />
    <HeldAssetLogChart :accountHistory="accountHistory" style="min-height: 350px" />
  </div>
</template>
<style scoped></style>
