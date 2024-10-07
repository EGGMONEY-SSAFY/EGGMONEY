<script setup lang="ts">
import { onMounted, ref, watch } from "vue"
import { useUserStore, type User } from "@/stores/user"
import BoxLoanListJudge from "@/components/box/BoxLoanListJudge.vue"
import { useFinStore } from "@/stores/fin"
import NotFoundComponent from "@/components/404/NotFoundComponent.vue"

const finStore = useFinStore()
const userStore = useUserStore()
const user = ref<User | null>(null)

watch(
  () => userStore.user,
  async (newUser) => {
    if (newUser) {
      await finStore.getUserLoanList(newUser.userId)
    }
  },
  { deep: true }
)

onMounted(async () => {
  if (userStore.user) {
    await finStore.getUserLoanList(userStore.user.userId)
  }
})
</script>
<template>
  <div v-if="userStore.user?.role === '부모'">
    <div v-if="finStore.loanList && userStore.user" class="grid grid-flow-row">
      <BoxLoanListJudge
        :user="userStore.user"
        :loan="loan"
        v-for="loan in finStore.loanList"
        :key="loan.loanId"
      />
    </div>
    <div v-else>
      <div class="grid justify-center mt-20">
        <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
      </div>
      <h1 class="text-xl text-center pt-10 font-bold pb-5">대출 신청 내역이 없습니다.</h1>
    </div>
  </div>
  <div v-else>
    <NotFoundComponent></NotFoundComponent>
  </div>
</template>
