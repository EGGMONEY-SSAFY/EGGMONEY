<template>
  <div class="fixed inset-0 z-50 flex items-center justify-center">
    <div class="bg-black bg-opacity-50 absolute inset-0"></div>
    <div class="bg-gray-900 text-white p-8 rounded-lg shadow-lg w-80 relative z-50">
      <div class="flex justify-between items-center">
        <img :src="NotiModal" alt="Notification" class="w-10 h-10" />
        <button @click="$emit('close')" class="text-white font-bold">닫기</button>
      </div>
      <h2 class="text-2xl text-white font-extrabold mt-4">{{ noti.notificationType }}</h2>
      <p class="text-white mt-2">{{ noti.message }}</p>
      <div class="mt-6 space-y-4">
        <button class="bg-red-500 text-white w-full py-2 rounded-lg" @click="goToCategory">
          요청 확인하러 가기
        </button>
        <button @click="$emit('close')" class="bg-white text-black w-full py-2 rounded-lg">
          닫기
        </button>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import NotiModal from "@/assets/notification/모달을 그냥 피그마에서 뜯어옴.png"
import { useRouter } from "vue-router"
const props = defineProps({
  noti: {
    type: Object,
    required: true,
  },
})
const router = useRouter()

// notificationType에 따라 페이지 이동 처리
const goToCategory = () => {
  const categoryRoutes: Record<string, string> = {
    출금요청: "/asset",
    출금승인: "/asset",
    출금거절: "/asset",
    대출요청: "/fin",
    대출승인: "/fin",
    대출거절: "/fin",
    대출상환: "/fin",
    적금납부: "/fin",
    적금만기: "/fin",
    예금만기: "/fin",
    지정가매수체결: "/stock/home",
    지정가매도체결: "/stock/home",
    투자비율변경: "/all",
    용돈변경: "/all",
  }

  const route = categoryRoutes[props.noti.notificationType]
  if (route) {
    router.push(route)
  } else {
    console.error("알 수 없는 notificationType입니다.")
  }
}
</script>
