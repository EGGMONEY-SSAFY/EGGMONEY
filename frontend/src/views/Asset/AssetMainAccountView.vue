<script setup lang="ts">
import BoxAccountLogs from "@/components/box/BoxAccountLogs.vue"
import BoxMainAccount from "@/components/box/BoxMainAccount.vue"
import { useAssetStore } from "@/stores/asset"
import { useVariableStore } from "@/stores/variable"
import { onMounted, ref, watch } from "vue"

const varStore = useVariableStore()

interface User {
  userId: number
  name: string
  realAccount: string
  bank: string
}

interface TradeData {
  accountId: number
  currentBalance: number
  tradePrice: number
  tradeTarget: string
  createdAt: string
}

const assetStore = useAssetStore()
const balance = ref<Number | null>(0)
const accountHistory = ref<TradeData[]>([])

const props = defineProps<{ user: User }>()

watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await assetStore.getPort(newUser.userId)
      balance.value = assetStore.mainAccount
      await assetStore.getAccountLog(props.user.userId)
      accountHistory.value = assetStore.logs
    }
  },
  { deep: true }
)

onMounted(async () => {
  varStore.setTitle("계좌 내역")
  await assetStore.getPort(props.user.userId)
  balance.value = assetStore.mainAccount
  await assetStore.getAccountLog(props.user.userId)
  accountHistory.value = assetStore.logs
})
</script>
<template>
  <div>
    <BoxMainAccount :user="props.user" :balance="balance" />
    <BoxAccountLogs :history="accountHistory" />
  </div>
</template>
