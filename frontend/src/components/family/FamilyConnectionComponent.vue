<template>
    <div class="flex flex-col items-center justify-center h-screen bg-gray-100">
        <div class="bg-white p-6 rounded-lg shadow-lg flex flex-col items-center justify-center space-y-6">

      <img src="../../assets/family/가족 초대 이미지.png" alt="초대 이미지" class="w-48 h-48 object-cover" />
      

      <h1 class="text-lg font-bold text-center">가족에 가입할 수 있어요!</h1>
      <p class="text-center text-gray-700">QR코드를 촬영해 주세요!</p>
        
      <div class="w-60 h-48 flex items-center justify-center">
        <FamilyQRCodeScanner/>
      </div>
      </div>
    </div>
  </template>
<script setup lang="ts">
import FamilyQRCodeScanner from './FamilyQRCodeScanner.vue';


</script>
<style>
    
</style>