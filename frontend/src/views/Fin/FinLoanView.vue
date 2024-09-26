<script setup lang="ts">
import IconExplanation from "@/components/icons/IconExplanation.vue"

const name = "FinLoanCreateView"
</script>

<template>
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
    <div class="m-4">
      <router-link :to="{ name: name }">
        <button class="bg-orange-500 text-white font-bold py-2 w-full rounded-full">
          신청하기
        </button>
      </router-link>
    </div>
  </div>
</template>
