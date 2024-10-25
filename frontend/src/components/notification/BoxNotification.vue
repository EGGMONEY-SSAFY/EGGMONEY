<script setup lang="ts">
import IconRightArrow from "@/components/icons/IconRightArrow.vue"
import RemindIcon from "@/assets/notification/리마인드알림.png"
import RejectIcon from "@/assets/notification/요청거절.png"
import ApproveIcon from "@/assets/notification/요청승인.png"
import MoneyIcon from "@/assets/notification/월급.png"
import StockCallIcon from "@/assets/notification/주식 매수.png"
import StockPutIcon from "@/assets/notification/주식 매도.png"
import ModalNotification from "./ModalNotification.vue"
import { ref } from "vue"
import { useAuthStore } from "@/stores/auth"
import axios from "axios"
type CustomNotificationType =
  | "출금요청"
  | "출금승인"
  | "출금거절"
  | "대출요청"
  | "대출승인"
  | "대출거절"
  | "대출상환"
  | "적금납부"
  | "적금만기"
  | "예금만기"
  | "지정가매수체결"
  | "지정가매도체결"
  | "투자비율변경"
  | "용돈변경"

const selectedNoti = ref<UserNotificiation | null>(null)
const showNoti = ref(false)
const Icons = {
  출금요청: RemindIcon,
  출금승인: ApproveIcon,
  출금거절: RejectIcon,
  대출요청: MoneyIcon,
  대출승인: ApproveIcon,
  대출거절: RejectIcon,
  대출상환: RemindIcon,
  적금납부: RemindIcon,
  적금만기: RemindIcon,
  예금만기: RemindIcon,
  지정가매수체결: StockCallIcon,
  지정가매도체결: StockPutIcon,
  투자비율변경: RemindIcon,
  용돈변경: RemindIcon,
}
interface UserNotificiation {
  userid: number
  sendUserid: number
  notificationType: CustomNotificationType
  message: string
  read: boolean // 추가: 알림이 읽혔는지 여부
  notificationId: number // 추가: 알림 ID
}
const dicicons: Record<string, number> = { 전체: 0, 자산: 1, 금융: 2, 증권: 3, 기타: 4 }
// type분류
// 자산 == '출금요청','출금승인','출금거절'
// 금융 == '대출요청','대출승인','대출거절','대출상환','적금납부','적금만기','예금만기'
// 증권 == '지정가매수체결', '지정가매도체결'
// 기타 == '투자비율변경', '용돈변경'
const props = defineProps<{
  noti: UserNotificiation
}>()
const markAsRead = async (notificationId: number) => {
  try {
    const token = useAuthStore().accessToken // 인증 토큰 가져오기
    await axios.post(
      `/api/v1/notification/${notificationId}/read`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    // 알림을 읽음으로 표시한 후 UI 업데이트
    props.noti.read = true
  } catch (error) {
    console.error(error)
  }
}
const showModal = () => {
  selectedNoti.value = props.noti
  markAsRead(props.noti.notificationId)
  showNoti.value = true
}
const closeModal = () => {
  showNoti.value = false
}
</script>

<template>
  <div
    @click="showModal"
    class="flex justify-between items-center p-4 bg-white rounded-lg shadow-md mb-4 cursor-pointer"
    :class="{
      'opacity-50': props.noti.read, // 이미 읽은 알림은 투명도와 블러 적용
      'bg-gray-200': props.noti.read, // 이미 읽은 알림 배경색 변경
    }"
  >
    <div class="flex items-center space-x-4">
      <!-- 알림 타입에 맞는 아이콘 표시 -->
      <img :src="Icons[props.noti.notificationType]" alt="" class="w-12 h-12" />
      <div>
        <h2 class="text-lg font-bold">{{ noti.notificationType }}</h2>
        <p class="text-gray-600">{{ noti.message }}</p>
      </div>
    </div>
    <IconRightArrow class="w-6 h-6 text-gray-400" />
  </div>

  <!-- 모달 컴포넌트를 표시 -->
  <ModalNotification v-if="showNoti" :noti="noti" @close="closeModal" />
</template>
