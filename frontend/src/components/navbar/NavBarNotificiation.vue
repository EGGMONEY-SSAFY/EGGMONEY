<script setup lang="ts">
import { useAuthStore } from "@/stores/auth"
import axios from "axios"
import { onMounted, ref } from "vue"

import BoxNotification from "../notification/BoxNotification.vue"

interface UserNotificiation {
  userid: number
  sendUserid: number
  notificationType: string
  message: string
  isRead?: boolean
}

const token = useAuthStore().accessToken
const notifications = ref<UserNotificiation[]>([])
const showNoti = ref<UserNotificiation[]>([])
const selectedType = ref<string>("전체")
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
    await axios.get('/notification/list',{
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },})
    notifications.value = response.data
    showNoti.value = notifications.value
    console.log(response)
  } catch (error) {}
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
      (noti: UserNotificiation) => noti.notificationType === type
    )
  }
}
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
      :class="{ 'bg-main-color text-white': selectedType === '전체', 'px-4': true }"
      class="px-4"
    >
      전체
    </button>
    <button
      type="button"
      class="px-4"
      :class="{ 'bg-main-color text-white': selectedType === '예금', 'px-4': true }"
      @click="
        isActive(
          '예금'
          //선택되었을때
        )
      "
    >
      예금
    </button>
    <button
      type="button"
      :class="{ 'bg-main-color text-white': selectedType === '적금', 'px-4': true }"
      class="px-4"
      @click="
        isActive(
          '적금'
          //선택되었을때
        )
      "
    >
      적금
    </button>
    <button
      type="button"
      :class="{ 'bg-main-color text-white': selectedType === '대출', 'px-4': true }"
      class="px-4"
      @click="
        isActive(
          '대출'
          //선택되었을때
        )
      "
    >
      대출
    </button>
  </div>
  <div v-if="showNoti.length > 0">
    <div v-for="(noti, index) in showNoti" :key="index">
      <BoxNotification :noti="noti" />
    </div>
  </div>
</template>
