<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import IconQuestionMark from "@/components/icons/IconQuestionMark.vue"
import InputMoney from "@/components/input/InputMoney.vue"
import { reactive, ref } from "vue"
import { useRouter } from "vue-router"

const reason = ref("")
const money = ref(0)
const loanDate = ref(0)
const dateList = reactive([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12])
const loanType = ref("")
const typeList = reactive(["원리금균등상환", "만기일시상환"])
const loanMoney = ref(0)

const router = useRouter()
const handleClick = () => {
  router.push({
    name: "FinView",
  })

  

}

const updateMoney = (value: number) => {
  money.value = value
}

const updateLoanMoney = (value: number) => {
  loanMoney.value = value
}
const updateSelectedDate = (event: Event) => {
  const target = event.target as HTMLSelectElement
  loanDate.value = Number(target.value)
}

const updateSelectedType = (event: Event) => {
  const target = event.target as HTMLSelectElement
  loanType.value = target.value
}
console.log(reason.value)
</script>

<template>
  <div class="m-4">
    <div class="m-4 flex items-center justify-center">
      <div class="m-2 flex">
        <IconExplanation></IconExplanation>
      </div>
      <div class="text-gray-600 font-bold text-sm">대출 신청서를 작성해보아요</div>
    </div>

    <div class="h-[70vh] flex flex-col justify-between">
      <div class="flex flex-col gap-6">
        <div class="flex flex-col gap-3">
          <div class="mx-2 flex items-center">
            <IconQuestionMark></IconQuestionMark>
            <div class="mx-2">신청 사유가 무엇인가요?</div>
          </div>
          <textarea
            type="text"
            id="loanReason"
            placeholder="신청사유를 입력해주세요"
            v-model="reason"
            class="w-5/6 h-28 p-4 mx-8 text-gray-600 rounded-lg placeholder-gray-400"
          ></textarea>
        </div>

        <div>
          <div class="m-2 flex items-center">
            <IconQuestionMark></IconQuestionMark>
            <div class="mx-2">원하는 대출 금액을 입력해주세요</div>
          </div>
          <div class="mx-8"><InputMoney @updateMoney="updateMoney"></InputMoney>알</div>
        </div>

        <div>
          <div class="m-2 flex items-center">
            <IconQuestionMark></IconQuestionMark>
            <div class="mx-2">대출 기간을 설정해주세요</div>
          </div>
          <select
            v-model="loanDate"
            @change="updateSelectedDate"
            class="border border-gray-300 rounded-lg p-1 mx-8 m-1 w-40 font-bold"
          >
            <option v-for="date in dateList" :key="date" :value="date">{{ date }}개월</option>
          </select>
        </div>

        <div>
          <div class="m-2 flex items-center">
            <IconQuestionMark></IconQuestionMark>
            <div class="mx-2">상환 방법을 선택해주세요</div>
          </div>
          <select
            v-model="loanType"
            @change="updateSelectedType"
            class="border border-gray-300 rounded-lg p-1 mx-8 m-1 w-40 font-bold"
          >
            <option v-for="type in typeList" :key="type" :value="type">{{ type }}</option>
          </select>
        </div>

        <div v-if="loanType == `원리금균등상환`">
          <div class="ml-8">
            매월 <InputMoney @updateLoanMoney="updateLoanMoney"></InputMoney>알을 상환 예정이에요
          </div>
        </div>
      </div>

      <div class="mt-8">
        <NextButton
          routeName="FinView"
          content="부모님에게 신청하기"
          @click="handleClick"
        ></NextButton>
      </div>
    </div>
  </div>
</template>
