<script setup lang="ts">
import { onMounted, ref, watch } from "vue"
import type { User } from "@/stores/user"
import { useVariableStore } from "@/stores/variable"
import { useAssetStore } from "@/stores/asset"
import BoxWithdrawalListItem from "@/components/box/BoxWithdrawalListItem.vue"

const varStore = useVariableStore()
const assetStore = useAssetStore()
const props = defineProps<{ user: User }>()

watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await assetStore.getWithdrawalList(newUser.userId)
      console.log(newUser.name)
    }
  },
  { deep: true }
)

onMounted(async () => {
  varStore.setTitle("출금요청 내역")
  await assetStore.getWithdrawalList(props.user.userId)
})
</script>
<template>
  <div v-if="assetStore.withdrawalList" class="grid grid-flow-row">
    <BoxWithdrawalListItem
      :user="props.user"
      :withdrawal="withdrawal"
      v-for="withdrawal in assetStore.withdrawalList"
      :key="withdrawal.applyee.userId"
    />
  </div>
  <div v-else>
    <div class="grid justify-center mt-20">
      <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
    </div>
    <h1 class="text-xl text-center pt-10 font-bold pb-5">출금요청 내역이 없습니다 ❕</h1>
  </div>
</template>
