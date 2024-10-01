<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { onMounted, ref, watch } from "vue"
import type { User } from "@/stores/user"
import type { Loan } from "@/stores/fin"
import { useVariableStore } from "@/stores/variable"
import BoxLoanListItem from "@/components/box/BoxLoanListItem.vue"

const varStore = useVariableStore()

const props = defineProps<{ user: User }>()
const myLoanList = ref<Loan[] | null>(null)

watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await finStore.getUserLoanList(newUser.userId)
      if (finStore.loanList != null) {
        if (typeof finStore.loanList === "object") {
          myLoanList.value = finStore.loanList as Loan[]
        }
      } else {
        myLoanList.value = null
      }
    }
  },
  { deep: true }
)
const finStore = useFinStore()

onMounted(async () => {
  varStore.setTitle("대출 내역")
  await finStore.getUserLoanList(props.user.userId)
  if (finStore.loanList != null) {
    if (typeof finStore.loanList === "object") {
      myLoanList.value = finStore.loanList as Loan[]
    }
  }
})
</script>
<template>
  <div v-if="myLoanList" class="grid grid-flow-row">
    <BoxLoanListItem
      :user="props.user"
      :loan="data"
      v-for="data in myLoanList"
      :key="data.loanId"
    />
  </div>
  <div v-else>
    <div class="grid justify-center mt-20">
      <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
    </div>
    <h1 class="text-xl text-center pt-10 font-bold pb-5">현재 대출이 없습니다 ❕</h1>
  </div>
</template>
