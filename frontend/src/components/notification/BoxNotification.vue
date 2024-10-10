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

const showNoti = ref(false)
const Icons = [RemindIcon, RejectIcon, ApproveIcon, MoneyIcon, StockCallIcon, StockPutIcon]
const dicicons: Record<string, number> = { 전체: 0, 대출: 1, 예금: 2, 적금: 3 }
// 대출 == '대출요청','대출승인','대출거절','대출상환'
// 적금 == '적금납부','적금만기'
// 예금 == '예금만기'
const props = defineProps({
  noti: {
    type: Object,
    required: true,
  },
})
// interface UserNotificiation{
//   userid: number;
//   sendUserid: number;
//   notificationType: string;
//   message: string;
//   isRead?: boolean;
// }
const showModal = () => {
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
  >
    <div class="flex items-center space-x-4">
      <!-- 알림 타입에 맞는 아이콘 표시 -->
      <img :src="Icons[dicicons[noti.notificationType]]" alt="" class="w-12 h-12" />
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
