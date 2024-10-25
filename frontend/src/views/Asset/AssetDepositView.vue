<script setup lang="ts">
import BoxDeposit from "@/components/box/BoxDeposit.vue"
import { onMounted, ref, watch } from "vue"
import type { User } from "@/stores/user"
import { useFinStore } from "@/stores/fin"
import type { Deposit } from "@/stores/fin"
import { useVariableStore } from "@/stores/variable"

const varStore = useVariableStore()
const finStore = useFinStore()
const userDeposit = ref<Deposit | null>(null)
const props = defineProps<{ user: User }>()

watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await finStore.getUserDeposit(newUser.userId)
      if (finStore.deposit != null) {
        if (typeof finStore.deposit === "object" && "depositProduct" in finStore.deposit) {
          userDeposit.value = finStore.deposit as Deposit
        }
      } else {
        userDeposit.value = null
      }
    }
  },
  { deep: true }
)

onMounted(async () => {
  varStore.setTitle("예금 내역")
  await finStore.getUserDeposit(props.user.userId)
  if (finStore.deposit != null) {
    if (typeof finStore.deposit === "object" && "depositProduct" in finStore.deposit) {
      userDeposit.value = finStore.deposit as Deposit
    }
  }
})
</script>
<template>
  <div v-if="userDeposit">
    <BoxDeposit :user="props.user" :deposit="userDeposit" />
  </div>
  <div v-else>
    <div class="grid justify-center mt-20">
      <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
    </div>
    <h1 class="text-xl text-center pt-10 font-bold">현재 예금 계좌가 없습니다 ❕</h1>
  </div>
</template>
