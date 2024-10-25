<script setup lang="ts">
import BoxAccountLogs from "@/components/box/BoxAccountLogs.vue"
import BoxMainAccount from "@/components/box/BoxMainAccount.vue"
import BoxSavings from "@/components/box/BoxSavings.vue"
import BoxSavingsLogs from "@/components/box/BoxSavingsLogs.vue"
import { useAssetStore } from "@/stores/asset"
import { useFinStore } from "@/stores/fin"
import type { Savings, SavingsLogs } from "@/stores/fin"
import { onMounted, ref, watch } from "vue"
import type { User } from "@/stores/user"
import { useVariableStore } from "@/stores/variable"

const varStore = useVariableStore()
const userSavings = ref<Savings | null>(null)
const savingsHistory = ref<SavingsLogs[] | null>(null)
const props = defineProps<{ user: User }>()

watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await finStore.getUserSavings(newUser.userId)
      if (finStore.savings != null) {
        if (typeof finStore.savings === "object") {
          userSavings.value = finStore.savings as Savings
        }
      } else {
        userSavings.value = null
      }
    }
  },
  { deep: true }
)
const finStore = useFinStore()

onMounted(async () => {
  varStore.setTitle("적금 내역")
  await finStore.getUserSavings(props.user.userId)
  if (finStore.savings != null) {
    if (typeof finStore.savings === "object" && "savingsDate" in finStore.savings) {
      userSavings.value = finStore.savings as Savings
    }
  }
  if (userSavings.value?.savingsId) {
    await finStore.getUserSavingsLogs(userSavings.value?.savingsId)
    savingsHistory.value = finStore.savingsLogs
  }
})
</script>
<template>
  <div v-if="userSavings">
    <BoxSavings :user="props.user" :savings="userSavings" />
    <BoxSavingsLogs :history="savingsHistory" />
  </div>
  <div v-else>
    <div class="grid justify-center mt-20">
      <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
    </div>
    <h1 class="text-xl text-center pt-10 font-bold">현재 적금 계좌가 없습니다 ❕</h1>
  </div>
</template>
