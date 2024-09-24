<template>
    <div id="qr-code-scanner" class="flex flex-col items-center justify-center h-screen">
      <video ref="videoRef" class="w-full max-w-lg h-auto border-2 border-gray-300 rounded-md"></video>
      
      
  
      <!-- 모달 (가족 연결 확인) -->
      <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white p-6 rounded-lg shadow-lg text-center">
          <p class="text-lg font-semibold mb-4">가족 연결하시겠습니까?</p>
          <div class="flex justify-center space-x-4">
            <button
              @click="confirmConnection"
              class="px-4 py-2 bg-green-500 text-white font-semibold rounded-lg hover:bg-green-600"
            >
              연결
            </button>
            <button
              @click="cancelConnection"
              class="px-4 py-2 bg-red-500 text-white font-semibold rounded-lg hover:bg-red-600"
            >
              취소
            </button>
          </div>
        </div>
      </div>
      <div v-if="failshowModal" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white p-6 rounded-lg shadow-lg text-center">
          <p class="text-lg font-semibold mb-4">가족 연결에 실패 했습니다</p>
          <div class="flex justify-center space-x-4">
            
            <button
              @click="closefail"
              class="px-4 py-2 bg-red-500 text-white font-semibold rounded-lg hover:bg-red-600"
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted, onBeforeUnmount } from 'vue';
  import QrScanner from 'qr-scanner'; // QrScanner 라이브러리 가져오기
  import axios from'axios';
import { useRouter } from 'vue-router';
// import {useAuthStore}from'@/stores/auth';
  // 상태 값
  const videoRef = ref<HTMLVideoElement | null>(null);
  const showModal = ref(false); // 모달 표시 여부
  const failshowModal = ref(false);
  const qrData = ref<string | null>(null);
  let qrScanner: QrScanner | null = null;
  const router= useRouter();

  // QR 코드 스캔 후 처리
  const handleScan = (result: QrScanner.ScanResult) => {
    qrData.value = result.data;
    console.log(qrData.value)
    showModal.value = true; // QR 코드 스캔 후 모달 표시
  };
  function closefail(){
    failshowModal.value=false;
  }
  // 연결 확인
async function confirmConnection()  {
    if (qrData.value) {
      // 여기서 실제로 요청을 보낼 수 있습니다.
      if (qrData.value.startsWith('http')) {
        
     
        try {
          const response=await axios.post (qrData.value,null,{
          headers:{
            // Authorization:`Bearer${authStore.getAccessToken}`,
            'Content-Type':'application/json',
            withCredentials: true,
          }
        });  
          if(response.data.status==='success'){
            router.push("/family/family-connection/success")
          } else{
            failshowModal.value=true;

          }
        } catch (error) {
          console.error('QR code data parsing error:', error);
          failshowModal.value=true;
        }
      }
    }
    showModal.value = false; // 모달 닫기
  }
  
  // 연결 취소
  function cancelConnection() {
    showModal.value = false; 
  }
  
  onMounted(() => {
    const videoElem = videoRef.value;
    if (videoElem) {
      qrScanner = new QrScanner(videoElem, (result) => handleScan(result), { preferredCamera: 'environment' });
      qrScanner.start(); // QR 스캐너 시작
    }
  });
  
  onBeforeUnmount(() => {
    if (qrScanner) {
      qrScanner.destroy();
    }
  });
  </script>
  
  <style scoped>
  
  </style>
  