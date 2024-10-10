<script setup lang="ts">
import { useUserStore, type User } from "@/stores/user"
import { ref } from "vue"
import { useRouter } from "vue-router"
import { useFinStore, type Loan } from "@/stores/fin"
import IconRightArrow from "@/components/icons/IconRightArrow.vue"
import { useAuthStore } from "@/stores/auth"
import axios from "axios"
import SCANQrComponent from "../family/SCANQrComponent.vue"

const props = defineProps<{ user: User; loan: Loan }>()
const router = useRouter()
const finStore = useFinStore()
const userStore = useUserStore()
const isModalOpen = ref(false)
const isJudge = ref(false)
const loanJudge = ref<null | string>(null)
const loanRate = ref(0)
const loanReason = ref("")
const isSCAN=ref(false);
// 여기에 LoanJudgeView.vue에서 user정보를 불러와서,
// (token에 있는것을) 대출리스트를 다시 받아와야한다.

// 모달을 여는 함수
function openModal() {
  isModalOpen.value = true
}

// 모달을 닫는 함수
// function closeModal() {
//   isModalOpen.value = false
// }
function handleSuccess() {
  console.log("승인이 성공적으로 처리되었습니다.")
  isSCAN.value = false;
  judge()
}

function handleFail() {
  isSCAN.value = false;
  console.log("승인에 실패하였습니다.")
}
async function sendWithJudge(judge: string) {
  isJudge.value = true
  loanJudge.value = judge  
}

async function sendqr(){
  isSCAN.value = true;
  try{
    const response = await axios.post(
        "/api/v1/family/approvenoti",
        {
          headers: {
            Authorization: `Bearer ${useAuthStore().accessToken}`,
            "Content-Type": "application/json",
          },
        }
      )
  }catch (error) {
      console.error("알림 전송 중 오류 발생:", error);

    }
}

async function judge() {
  if (loanJudge.value && userStore.user) {
    await finStore.setLoanJudgeInfo(
      props.loan.loanId,
      loanJudge.value,
      userStore.user.userId,
      loanReason.value,
      loanRate.value
    )
    router.push({ name: "FinPinPadView" })
  }
  // closeModal()
  // window.location.reload()
}

const numberInput = ref<number | null>(null)

function saveNumber() {
  if (numberInput.value !== null && numberInput.value >= 0 && numberInput.value <= 10) {
    loanRate.value = numberInput.value
  } else {
    loanRate.value = 0
  }
}

const reason = ref<null | string>(null)

function saveReason() {
  if (reason.value !== null) {
    loanReason.value = reason.value
  } else {
    loanReason.value = ""
  }
  console.log(loanReason.value)
}

const formatExpireDate = (expireDate?: string) => {
  if (!expireDate) return "" // expireDate가 없는 경우 빈 문자열 반환
  const date = new Date(expireDate)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, "0") // 월은 0부터 시작하므로 +1
  const day = String(date.getDate()).padStart(2, "0")
  return `${year}년 ${month}월 ${day}일`
}

function goLoanDetail(loanId: number) {
  router.push({ name: "AssetLoanListItem", params: { loanId: loanId } })
}
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow grid py-2 gap-2 px-4 pb-5" v-if="!isSCAN">
    <div v-if="user.role === '부모'" class="text-center my-2 font-bold">{{ loan.userName }}</div>
    <hr v-if="user.role === '부모'" class="border-black" />
    <div class="mt-3 flex justify-between">
      <h1
        class="border-red-500 border-2 rounded-lg text-red-500 px-3 py-1 inline-flex"
        v-if="props.loan.loanStatus === 'REFUSAL'"
      >
        거절
      </h1>
      <div class="flex justify-between w-full" v-if="props.loan.loanStatus === 'APPROVAL'">
        <h1
          class="border-green-700 border-2 rounded-lg text-green-700 px-3 py-1 inline-flex my-auto"
        >
          승낙
        </h1>
        <div
          class="grid grid-flow-col p-1 border-2 border-gray-500 rounded-lg"
          role="button"
          tabindex="0"
          v-if="loan.loanId"
          @click="goLoanDetail(props.loan.loanId)"
        >
          <h1 class="px-1 my-auto text-sm font-semibold">상세보기</h1>
          <IconRightArrow class="size-6" />
        </div>
      </div>
      <h1
        class="border-2 border-main-color rounded-lg text-main-color px-3 py-1 inline-flex"
        v-if="props.loan.loanStatus === 'PROGRESS'"
      >
        진행중
      </h1>
      <h1
        class="border-gray-500 border-2 rounded-lg text-gray-500 px-3 py-1 inline-flex"
        v-if="props.loan.loanStatus === 'EXPIRED'"
      >
        완납
      </h1>

      <div class="flex justify-between" v-if="loan.loanStatus === 'PROGRESS'">
        <h1
          class="bg-main-color rounded-lg text-white font-bold px-3 py-1 inline-flex border-main-color border-2"
          role="button"
          tabindex="0"
          v-if="user.role === '부모'"
          @click="openModal"
        >
          심사하기
        </h1>
      </div>
    </div>
    <div v-if="loan" class="flex justify-between mt-2 pe-5 text-end">
      <h1 class="font-bold">대출액</h1>
      <h1 class="text-wrap">{{ loan?.loanAmount?.toLocaleString() ?? 0 }}</h1>
    </div>
    <div class="flex justify-between mt-2 pe-5 text-end">
      <h1 class="font-bold">신청일</h1>
      <h1 v-if="loan.createdAt" class="text-wrap">{{ formatExpireDate(loan.createdAt) }}</h1>
    </div>
    <div
      class="flex justify-between mt-2 pe-5 text-end"
      v-if="loan?.updatedAt && props.loan.loanStatus != 'PROGRESS'"
    >
      <h1 v-if="props.loan.loanStatus === 'REFUSAL'" class="font-bold">거절일</h1>
      <h1 v-if="props.loan.loanStatus === 'APPROVAL'" class="font-bold">승인일</h1>
      <h1 v-if="loan.loanStatus === 'EXPIRED'" class="font-bold">상환일</h1>
      <h1 v-if="loan.updatedAt" class="text-wrap">{{ formatExpireDate(loan.updatedAt) }}</h1>
    </div>

    <div v-if="loan.loanStatus !== 'REFUSAL'" class="flex justify-between mt-2 pe-5 text-end">
      <h1 class="font-bold">대출 이율</h1>
      <h1 v-if="loan.loanRate" class="text-wrap">{{ loan.loanRate?.toFixed(2) }} %</h1>
      <h1 v-else class="text-gray-300">심사를 해주세요</h1>
    </div>
    <div v-if="loan.loanStatus !== 'REFUSAL'" class="flex justify-between pe-5 text-end mt-2">
      <h1 class="font-bold">대출 잔액</h1>
      <h1 class="text-wrap">{{ loan.balance.toLocaleString() }}</h1>
    </div>
    <div class="flex justify-between pe-5 text-end mt-2">
      <h1 class="font-bold">신청 사유</h1>
      <h1 class="text-wrap">{{ loan.loanReason }}</h1>
    </div>
    <div class="flex justify-between pe-5 text-end mt-2" v-if="props.loan.loanStatus === 'REFUSAL'">
      <h1 class="w-28 text-left font-bold">거절 사유</h1>
      <h1 class="text-wrap">{{ props.loan.refuseReason }}</h1>
    </div>
  </div>
  <!-- 모달 -->
  <div
    v-if="isModalOpen"
    class="fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50"
  >
    <div class="p-6 bg-white rounded-lg shadow-lg w-80" v-if="userStore.user?.userId">
      <h2 class="mb-4 text-xl font-bold text-center">대출 심사</h2>

      <div class="flex flex-col gap-2">
        <div>요청 자녀 : {{ loan.userName }}</div>
        <div>
          요청 금액 : <span class="">{{ loan.loanAmount?.toLocaleString() }}</span
          >알
        </div>
        <div class="mb-4 text-wrap">
          요청 이유 : <span class="text-sm text-wrap"> {{ loan.loanReason }}</span>
        </div>
      </div>

      <div v-if="loanJudge === 'REFUSAL'">
        <div v-if="loanJudge === 'REFUSAL'" class="items-center">
          <div class="mt-3 mb-1">거절 이유</div>
          <input
            id="reason"
            v-model="reason"
            type="text"
            class="w-full px-3 py-2 leading-tight text-gray-700 border rounded shadow appearance-none focus:outline-none focus:shadow-outline"
            @input="saveReason"
            placeholder="거절 이유를 입력해주세요."
          />
        </div>
      </div>
      <div v-if="isJudge && loanJudge === 'APPROVAL'">
        <div v-if="loanJudge === 'APPROVAL'" class="flex items-center">
          <label for="loanRate" class="block m-2 font-bold text-gray-700">대출 이자: </label>
          <input
            id="number-input"
            v-model="numberInput"
            type="number"
            step="0.5"
            min="0"
            max="10"
            class="px-3 py-2 leading-tight text-gray-700 border rounded shadow appearance-none focus:outline-none focus:shadow-outline"
            @input="saveNumber"
          />
        </div>
      </div>

      <div class="flex justify-end">
        <!-- v-if로 조건 걸어두기(거절, 승인 누르면 버튼 사라지고, 심사버튼 등장해야함) -->
        <div v-if="!isJudge">
          <button
            class="px-4 py-2 mr-4 text-white bg-gray-500 rounded"
            @click="sendWithJudge('REFUSAL')"
          >
            거절
          </button>
          <button
            class="px-4 py-2 text-white bg-blue-500 rounded"
            @click="sendWithJudge('APPROVAL')"
          >
            승낙
          </button>
        </div>

        


        <!-- v-else -->
        <div v-else>
          <button
            v-if="loanJudge"
            @click="sendqr()"
            class="bg-main-color text-white px-4 py-2 mt-5 rounded justify-center"
          >
            심사하기
          </button>
        </div>
      </div>
    </div>
  </div>
  <SCANQrComponent @approveSuccess="handleSuccess" @approveFail="handleFail" v-if="isSCAN"/>
</template>
