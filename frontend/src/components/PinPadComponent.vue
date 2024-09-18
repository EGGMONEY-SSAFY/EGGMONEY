<template>
    <div class="pin-pad">
      <img :src="pinPadImage" v-for="(number, index) in pinPad" :key="index" @click="onPinPadClick(index)" />
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  
  const pinPad = ref<number[]>([]);
  const pinPadImage = ref<string>('');
  
  const fetchPinPad = async () => {
    const response = await axios.get('/api/pinpad');
    pinPad.value = response.data.pinPad;
    pinPadImage.value = await decryptImage(response.data.encryptedImage);
  };
  
  const decryptImage = async (encryptedImage: string): Promise<string> => {
    // 이미지 복호화 로직 (JavaScript에서 복호화 처리 필요)
    return encryptedImage;
  };
  
  const onPinPadClick = (index: number) => {
    axios.post('/api/pinpad/submit', { index });
  };
  
  onMounted(fetchPinPad);
  </script>
  
  <style scoped>
  .pin-pad {
    display: flex;
    flex-wrap: wrap;
  }
  </style>
  