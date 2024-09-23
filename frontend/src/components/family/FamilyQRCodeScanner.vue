<template>
    <div id="qr-code-scanner" class="flex flex-col items-center justify-center h-screen">
      <video ref="videoRef" class="w-full max-w-lg h-auto border-2 border-gray-300 rounded-md"></video>
      
      <!-- QR 코드 스캔 시작 버튼 -->
      <button 
        @click="startScanning" 
        class="mt-4 px-6 py-2 bg-blue-500 text-white font-semibold rounded-lg shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75"
      >
        QR 코드 스캔 시작
      </button>
  
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
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted, onBeforeUnmount } from 'vue';
  import QrScanner from 'qr-scanner'; // QrScanner 라이브러리 가져오기
  
  // 상태 값
  const videoRef = ref<HTMLVideoElement | null>(null);
  const scanning = ref(false);
  const showModal = ref(false); // 모달 표시 여부
  const qrData = ref<string | null>(null);
  let qrScanner: QrScanner | null = null;
  
  // QR 스캔 시작
  function startScanning() {
    scanning.value = true; // 스캔 상태를 true로 설정
  }
  
  // QR 코드 스캔 후 처리
  const handleScan = (result: QrScanner.ScanResult) => {
    qrData.value = result.data;
    console.log(qrData.value)
    showModal.value = true; // QR 코드 스캔 후 모달 표시
  };
  
  // 연결 확인
  function confirmConnection() {
    if (qrData.value) {
      // 여기서 실제로 요청을 보낼 수 있습니다.
      if (qrData.value.startsWith('http')) {
        window.location.href = qrData.value;
      } else {
        try {
          const parsedData = JSON.parse(qrData.value);
          console.log('Parsed data:', parsedData);
          // 가족 연결 API 호출 로직 추가 가능
        } catch (error) {
          console.error('QR code data parsing error:', error);
        }
      }
    }
    showModal.value = false; // 모달 닫기
  }
  
  // 연결 취소
  function cancelConnection() {
    showModal.value = false; // 모달 닫기
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
  /* Tailwind로 스타일링을 관리하므로 추가적인 CSS는 필요하지 않음 */
  </style>
  