<script setup lang="ts">
import { useAuthStore } from "@/stores/auth"
import axios from "axios"
import { onMounted, ref } from "vue"

import BoxNotification from "../notification/BoxNotification.vue"
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
interface UserNotificiation {
  userid: number
  sendUserid: number
  notificationType: CustomNotificationType
  message: string
  isRead?: boolean
  read: boolean // 추가: 알림이 읽혔는지 여부
  notificationId: number // 추가: 알림 ID
}

const token = useAuthStore().accessToken
const notifications = ref<UserNotificiation[]>([])
const showNoti = ref<UserNotificiation[]>([])
const selectedType = ref<string>("전체")
const typeMapping: Record<CustomNotificationType, string> = {
  출금요청: "자산",
  출금승인: "자산",
  출금거절: "자산",
  대출요청: "금융",
  대출승인: "금융",
  대출거절: "금융",
  대출상환: "금융",
  적금납부: "금융",
  적금만기: "금융",
  예금만기: "금융",
  지정가매수체결: "증권",
  지정가매도체결: "증권",
  투자비율변경: "기타",
  용돈변경: "기타",
}
const fetchNotifications = async () => {
  try {
    const response =
      // {
      //   data: [
      //     { userid: 1, sendUserid: 2, notificationType: "대출", message: "갚아라" },
      //     { userid: 1, sendUserid: 2, notificationType: "적금", message: "적금만기해지" },
      //     { userid: 1, sendUserid: 2, notificationType: "전체", message: "주식 1주 매수 성공" },
      //     { userid: 1, sendUserid: 2, notificationType: "적금", message: "적금 중도 해지" },
      //   ],
      // }
      await axios.get("api/v1/notification/list", {
        headers: {
          Authorization: `Bearer ${token}`,
          // "Content-Type": "application/json",
        },
      })
    notifications.value = response.data
    showNoti.value = notifications.value
    console.log(response)
  } catch (error) {
    console.log(error)
  }
}
onMounted(() => {
  fetchNotifications()
})

// 알림 필터링 함수
const isActive = (type: string) => {
  selectedType.value = type // 선택된 타입 업데이트
  if (type === "전체") {
    showNoti.value = notifications.value // 전체 알림
  } else {
    showNoti.value = notifications.value.filter(
      (noti: UserNotificiation) => typeMapping[noti.notificationType] === type
    )
  }
}
// 프론트 필터=notificationType분류
// 자산 == '출금요청','출금승인','출금거절'
// 금융 == '대출요청','대출승인','대출거절','대출상환','적금납부','적금만기','예금만기'
// 증권 == '지정가매수체결', '지정가매도체결'
// 기타 == '투자비율변경', '용돈변경'
</script>

<template>
  <div class="flex justify-center pt-4 pb-2">
    <button
      type="button"
      @click="
        isActive(
          '전체'
          // 전체선택 필터 없이
          //선택되었을때
        )
      "
      :class="{ 'bg-main-color rounded-full text-white': selectedType === '전체', 'px-4': true }"
      class="px-4"
    >
      전체
    </button>
    <button
      type="button"
      class="px-4"
      :class="{ 'bg-main-color rounded-full text-white': selectedType === '자산', 'px-4': true }"
      @click="
        isActive(
          '자산'
          //선택되었을때
        )
      "
    >
      자산
    </button>
    <button
      type="button"
      :class="{ 'bg-main-color rounded-full text-white': selectedType === '금융', 'px-4': true }"
      class="px-4"
      @click="
        isActive(
          '금융'
          //선택되었을때
        )
      "
    >
      금융
    </button>

    <button
    type="button"
    :class="{ 'bg-main-color rounded-full text-white': selectedType === '증권', 'px-4': true }"
          class="px-4"
    @click="isActive('증권')"
  >
    증권
  </button>

    <button
      type="button"
      :class="{ 'bg-main-color rounded-full text-white': selectedType === '기타', 'px-4': true }"
      class="px-4"
      @click="
        isActive(
          '기타'
          //선택되었을때
        )
      "
    >
      기타
    </button>
  </div>
  <div v-if="showNoti.length > 0">
    <div v-for="(noti, index) in showNoti" :key="index">
      <BoxNotification
        :noti="{
          userid: noti.userid,
          sendUserid: noti.sendUserid,
          notificationType: noti.notificationType,
          message: noti.message,
          read: noti.read,
          notificationId: noti.notificationId,
        }"
      />
    </div>
  </div>
</template>
