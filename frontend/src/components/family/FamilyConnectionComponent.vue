<template>
  <div class="flex flex-col items-center justify-center bg-gray-200">
    <div
      class="bg-white p-6 rounded-lg shadow-lg flex flex-col items-center justify-center w-72 max-w-lg mt-20 mb-4"
    >
      <img
        src="../../assets/family/가족 초대 이미지.png"
        alt="초대 이미지"
        class="w-48 mt-4 mb-4 object-cover"
      />

      <h1 class="text-lg font-bold text-center mt-4">가족에 가입할 수 있어요</h1>
      <p class="text-center text-orange-500 font-bold mt-2 mb-4">QR코드 촬영하기 !</p>

      <div class="w-60 mb-4 flex items-center justify-center">
        <FamilyQRCodeScanner />
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import FamilyQRCodeScanner from "./FamilyQRCodeScanner.vue"
</script>
<style></style>
