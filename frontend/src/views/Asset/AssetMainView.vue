<script setup lang="ts">
import BoxAccount from "@/components/box/BoxAccount.vue"
import HeldAssetChart from "@/components/cart/HeldAssetChart.vue"
import HeldAssetLogChart from "@/components/cart/HeldAssetLogChart.vue"
import { useAssetStore } from "@/stores/asset"
import { onMounted, watch, ref } from "vue"

const assetStore = useAssetStore()

interface User {
  userId: number
  name: string
}

interface Analytics {
  입출금통장: Number | null
  예금: Number | null
  적금: Number | null
  대출: Number | null
  주식: Number | null
}

interface TradeData {
  accountId: number
  currentBalance: number
  tradePrice: number
  tradeTarget: string
  createdAt: string
}

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

// 유저가 변경될 때 비동기 작업을 처리하고 analytics 값을 갱신
watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await assetStore.getPort(newUser.userId)

      // 수정: ref로 선언된 analytics를 재활당하여 반응성을 보장
      analytics.value = {
        입출금통장: assetStore.mainAccount,
        예금: assetStore.deposit,
        적금: assetStore.savings,
        대출: assetStore.loan,
        주식: assetStore.stock,
      }
      await assetStore.getAccountLog(props.user.userId)
      accountHistory.value = assetStore.logs
    }
  },
  { deep: true }
)

onMounted(async () => {
  await assetStore.getPort(props.user.userId)
  // 수정: ref로 선언된 analytics 값을 재할당
  analytics.value = {
    입출금통장: assetStore.mainAccount,
    예금: assetStore.deposit,
    적금: assetStore.savings,
    대출: assetStore.loan,
    주식: assetStore.stock,
  }
  await assetStore.getAccountLog(props.user.userId)
  accountHistory.value = assetStore.logs
})
</script>
<template>
  <div class="grid">
    <BoxAccount :user="user" :analytics="analytics" />
    <HeldAssetChart :analytics="analytics" />
    <HeldAssetLogChart :accountHistory="accountHistory" style="min-height: 350px" />
  </div>
</template>
