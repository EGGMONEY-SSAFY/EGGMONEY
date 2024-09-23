<template>
  <div id="invite-qr-code">
    <h1>가족 초대 시스템</h1>
    <div v-if="qrCode">
      <p>가족 초대 QR 코드:</p>
      <img :src="qrCode" alt="QR 코드" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
// @ts-ignore
import QRCode from 'qrcode';

const familyId = ref('1'); 
const qrCode = ref<string | null>(null);

// Function to generate QR code
async function generateQRCode() {
  try {
    qrCode.value = await QRCode.toDataURL(`http://localhost:8080/invite?familyId=${familyId.value}`);
    
  } catch (error) {
    console.error('QR code generation error:', error);
  }
}

// Automatically generate QR code when component is mounted
onMounted(() => {
  generateQRCode();
  
});
</script>

<style scoped>
/* Add your styles here */
</style>

