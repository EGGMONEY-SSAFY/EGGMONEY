<script setup lang="ts">
import NextButton from "@/components/button/NextButton.vue"
import IconExplanation from "@/components/icons/IconExplanation.vue"
import IconQuestionMark from "@/components/icons/IconQuestionMark.vue"
import InputMoney from "@/components/input/InputMoney.vue"
import { useFinStore } from "@/stores/fin"
import { useUserStore } from "@/stores/user"
import { onMounted, reactive, ref } from "vue"
import { useRouter } from "vue-router"

const reason = ref<string | null>(null)
const money = ref<number | null>(null)
const loanDate = ref<number>(1)
const dateList = reactive([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12])
const loanType = ref<string | null>(null)
const typeList = reactive(["원리금균등상환", "만기일시상환"])
const maxPrice = ref(0)
const router = useRouter()
const isModalVisible = ref(false)
const finStore = useFinStore()
const userStore = useUserStore()

const showModal = () => {
  isModalVisible.value = true
}
const hideModal = () => {
  isModalVisible.value = false
}
const isValid = ref(false)
const handleClick = () => {
  if (!reason.value) {
    alert("신청 사유를 입력해주세요.")
    return
  } else if (!money.value || money.value <= 0) {
    alert("대출 금액을 입력해주세요.")
    return
  } else if (!loanDate.value) {
    alert("대출 기간을 선택해주세요.")
    return
  } else if (!loanType.value) {
    alert("상환 방법을 선택해주세요.")
    return
  } else {
    isValid.value = true
    console.log("userID", userStore.user?.userId)
    if (userStore.user?.userId) {
      finStore.setLoanCreate(
        reason.value,
        money.value,
        loanDate.value,
        loanType.value,
        userStore.user?.userId
      )
    }

    router.push({
      name: "FinPinPadView",
    })
  }
}

const updateMoney = (value: number) => {
  money.value = value
  if(money.value && loanDate.value){
    maxPrice.value = money.value / loanDate.value
  }
}

const updateSelectedDate = (event: Event) => {
  const target = event.target as HTMLSelectElement
  loanDate.value = Number(target.value)
  if(money.value && loanDate.value){
    maxPrice.value = money.value / loanDate.value
  }
}

const updateSelectedType = (event: Event) => {
  const target = event.target as HTMLSelectElement
  if (!money.value) {
    alert("대출 금액을 입력해주세요.")

    return
  } else if (!loanDate.value) {
    alert("대출 기간을 선택해주세요.")
    return
  }

  loanType.value = target.value
  maxPrice.value = money.value / loanDate.value
}
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
            required
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
            required
            v-model="loanDate"
            @change="updateSelectedDate"
            class="border border-gray-300 rounded-lg p-1 mx-8 m-1 w-40 font-bold"
          >
            <option v-for="date in dateList" :key="date" :value="date">{{ date }}개월</option>
          </select>
        </div>

        <div>
          <div class="m-2 flex items-center">
            <div class="relative inline-block" @mouseenter="showModal" @mouseleave="hideModal">
              <IconQuestionMark></IconQuestionMark>
              <div
                v-if="isModalVisible"
                class="absolute left-6 -top-20 mt-2 w-48 p-4 bg-white border border-gray-300 shadow-lg rounded-lg z-50"
              >
                <p class="text-sm font-bold">원리금균등상환</p>
                <p class="text-sm text-gray-700">
                  대출금과 이자를 모두 포함하여 매달 같은 금액을 지불하는 것을 의미
                </p>
                <br />
                <p class="text-sm font-bold">만기일시상환</p>
                <p class="text-sm text-gray-700">
                  대출 기간이 끝날 때 대출금과 이자를 모두 지불하는 것을 의미
                </p>
              </div>
            </div>
            <div class="mx-2">상환 방법을 선택해주세요</div>
          </div>
          <select
            required
            v-model="loanType"
            @change="updateSelectedType"
            class="border border-gray-300 rounded-lg p-1 mx-8 m-1 w-40 font-bold"
          >
            <option v-for="type in typeList" :key="type" :value="type">{{ type }}</option>
          </select>
        </div>
        <div v-if="loanType == `원리금균등상환`">
          <div class="ml-8">
            매월
            <span class="bg-white rounded-lg p-1 w-40 px-12 font-bold"
              >{{ maxPrice.toFixed(0) }}
            </span>
            알을 상환 할 예정이에요
          </div>
        </div>
      </div>

      <div class="mt-8">
        <NextButton
          routeName="FinLoanJudgeView"
          content="부모님에게 신청하기"
          :disabled="isValid"
          @click="handleClick"
        ></NextButton>
      </div>
    </div>
  </div>
</template>
