<template>
  <div class="fixed inset-0 z-50 flex items-center justify-center">
    <div class="bg-black bg-opacity-50 absolute inset-0"></div>
    <div class="bg-gray-900 text-white p-8 rounded-lg shadow-lg w-80 relative z-50">
      <div class="flex justify-between items-center">
        <img :src="NotiModal" alt="Notification" class="w-10 h-10" />
        <button @click="$emit('close')" class="text-white font-bold">닫기</button>
      </div>
      <h2 class="text-2xl font-bold mt-4">{{ noti.notificationType }}</h2>
      <p class="mt-2">{{ noti.message }}</p>
      <div class="mt-6 space-y-4">
        <button class="bg-red-500 text-white w-full py-2 rounded-lg">요청 확인하러 가기</button>
        <button @click="$emit('close')" class="bg-white text-black w-full py-2 rounded-lg">
          닫기
        </button>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import NotiModal from "@/assets/notification/모달을 그냥 피그마에서 뜯어옴.png"
const props = defineProps({
  noti: {
    type: Object,
    required: true,
  },
})
</script>
