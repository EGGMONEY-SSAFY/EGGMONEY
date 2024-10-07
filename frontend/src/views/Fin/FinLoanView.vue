<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import router from "@/router"
import { useFinStore } from "@/stores/fin"
import { useUserStore } from "@/stores/user"
import { onMounted, ref, onUnmounted } from "vue"

const finStore = useFinStore()
const userStore = useUserStore()

const role = ref("")
onMounted(() => {
  finStore.isYellowPage = true
})

onUnmounted(() => {
  finStore.isYellowPage = false
})

const handleClick = () => {
  router.push({
    name: "FinLoanCreateView",
  })
}

const handleClickParent = () => {
  router.push({
    name: "FinLoanJudgeView",
  })
}
const handleListClick = () => {
  router.push({ name: "AssetLoanListView" })
}
</script>

<template>
  <div v-if="userStore.user && userStore.user.role === '자녀'">
    <div class="bg-yellow-50">
      <div class="h-[78vh] flex flex-col justify-around">
        <div class="m-4 flex items-center justify-center">
          <div class="m-2 flex">
            <IconExplanation></IconExplanation>
          </div>
          <div class="text-gray-600 font-bold text-sm">급하게 돈을 구할 수 있어요</div>
        </div>
        <div class="flex justify-center">
          <img src="@/assets/fin/loan.png" alt="로딩중" />
        </div>
        <div class="flex justify-center items-center">
          <div class="grid gap-3 text-center">
            <div>대출이 필요한 경우,</div>
            <div>부모님께 돈을 요청할 수 있어요</div>
            <div>대출금은 납기 기한 내에</div>
            <div>갚아야한다는 걸 명심하세요!</div>
          </div>
        </div>
        <div class="m-8 gap-4 flex justify-between">
          <NextButton content="내역보기" @click="handleListClick"></NextButton>
          <NextButton content="신청하기" @click="handleClick"></NextButton>
        </div>
      </div>
    </div>
  </div>
  <div v-else-if="userStore.user && userStore.user.role === '부모'">
    <div class="bg-yellow-50">
      <div class="h-[78vh] flex flex-col justify-around">
        <div class="m-4 flex items-center justify-center">
          <div class="m-2 flex">
            <IconExplanation></IconExplanation>
          </div>
          <div class="text-gray-600 font-bold text-sm">자녀의 대출 신청을 심사할 수 있어요</div>
        </div>
        <div class="flex justify-center">
          <img src="@/assets/fin/loanParent.png" alt="로딩중" />
        </div>
        <div class="flex justify-center items-center">
          <div class="grid gap-3 text-center">
            <div>자녀가 신청한 대출을 확인해주세요</div>
            <div>이유, 기간, 금액을 보시고 심사해주세요.</div>
            <div>자녀는 빌린 돈을</div>
            <div>일정 기간동안 상환할 것입니다.</div>
          </div>
        </div>
        <div class="m-4">
          <NextButton content="심사하기" @click="handleClickParent"></NextButton>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <div class="grid justify-center mt-20">
      <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
    </div>
  </div>
</template>
