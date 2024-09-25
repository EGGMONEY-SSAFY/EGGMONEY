<template>
    <div>
      <!-- <button @click="showModal = true" class="bg-blue-500 text-white px-4 py-2 rounded">로그인</button> -->
      
      <!-- 모달 -->
      <div  class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
        <div class="bg-white p-6 rounded-lg shadow-lg">
        
          <LoginComponent/>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  import LoginComponent from '@/components/login/LoginComponent.vue';
  

  </script>
  
<style>
    
</style>