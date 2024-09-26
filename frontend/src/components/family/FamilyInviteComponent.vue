<template>
  <div class="flex flex-col items-center justify-center h-screen bg-gray-200">
    <div
      class="bg-white p-6 rounded-lg shadow-lg flex flex-col items-center justify-center space-y-4"
    >
      <img
        src="../../assets/family/가족 초대 이미지.png"
        alt="초대 이미지"
        class="w-48 h-48 object-cover"
      />
      <h1 class="text-lg font-bold text-center text-gray-800">가족을 초대할 수 있어요!</h1>
      <p class="text-center text-gray-700">
        QR코드가 생성되었어요!
        <br />
        <span class="text-orange-500 font-bold">자녀를 초대해 보세요!</span>
      </p>
      <FamilyInviteQRCodeComponent />
    </div>
  </div>
</template>
<script setup lang="ts">
import FamilyInviteQRCodeComponent from "./FamilyInviteQRCodeComponent.vue"
</script>
<style></style>
