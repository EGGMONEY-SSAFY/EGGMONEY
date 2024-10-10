<script setup lang="ts">
import { onMounted, ref, watch } from "vue"
import { useUserStore, type User } from "@/stores/user"
import BoxLoanListJudge from "@/components/box/BoxLoanListJudge.vue"
import { useFinStore } from "@/stores/fin"
import NotFoundComponent from "@/components/404/NotFoundComponent.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import axios from "axios"

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

interface LoanData {
  loan_type: string
  avg_loan_amount: number
  avg_loan_rate: number
}

const avgLoanData = ref<LoanData[]>([])

// 대출 평균 데이터 조회
const getAvgLoanData = function (): Promise<void> {
  return axios({
    method: "get",
    url: `https://oracle1.mypjt.xyz/api/v1/avg_loan`,
  })
    .then((res) => {
      avgLoanData.value = res.data
    })
    .catch((err) => {
      console.error(err)
    })
}

onMounted(async () => {
  if (userStore.user) {
    await finStore.getUserLoanList(userStore.user.userId)
    await getAvgLoanData()
    console.log(avgLoanData.value)
  }
})
</script>
<template>
  <div v-if="userStore.user?.role === '부모'">
    <!-- 평균 대출 이율 추천 박스 -->
    <div v-if="avgLoanData" class="grid grid-flow-row grid-cols-1 px-4 mt-2">
      <div class="py-3 bg-yellow-50 rounded-xl">
        <div class="flex my-auto ps-3">
          <IconExplanation></IconExplanation>
          <h1 class="text-sm ps-2">
            이용자들의 <span class="underline underline-offset-2">평균 이율</span>은 아래와 같아요!
          </h1>
        </div>
        <div v-for="(data, index) in avgLoanData" :key="index" class="grid px-3 mt-3 rounded-lg">
          <div class="flex justify-between px-2 pe-4">
            <h1
              class="p-1 px-2 text-sm text-white bg-blue-500 rounded-xl"
              v-if="data.loan_type === 'LUMPSUM'"
            >
              원리금균등상환
            </h1>
            <h1 class="p-1 px-2 text-sm text-white bg-green-700 rounded-xl" v-else>만기일시상환</h1>
            <h1
              class="text-base font-bold text-black underline underline-offset-2"
              v-if="data.avg_loan_rate"
            >
              {{ data.avg_loan_rate.toFixed(2) }} %
            </h1>
          </div>
        </div>
      </div>
    </div>
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
      <h1 class="pt-10 pb-5 text-xl font-bold text-center">대출 신청 내역이 없습니다.</h1>
    </div>
  </div>
  <div v-else>
    <NotFoundComponent></NotFoundComponent>
  </div>
</template>
