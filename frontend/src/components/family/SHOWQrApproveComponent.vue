<template>
    <div id="qr-code-scanner" class="flex flex-col items-center justify-center">
      <div id="qr-reader" style="width: 250px; height: 250px"></div>
      <!-- 스캔 영역 크기 조정 -->
      <!-- 모달 (가족 연결 확인) -->
      <div
        v-if="showModal"
        class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
      >
        <div class="bg-white p-6 rounded-lg shadow-lg text-center">
          <p class="text-lg font-semibold mb-4">승인하시겠습니까?</p>
          <div class="flex justify-center space-x-4">
            <button
              @click="confirmConnection"
              class="px-4 py-2 bg-green-500 text-white font-semibold rounded-lg hover:bg-green-600"
            >
              승인
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
      <div
        v-if="failshowModal"
        class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
      >
        <div class="bg-white p-6 rounded-lg shadow-lg text-center">
          <p class="text-lg font-semibold mb-4">승인에 실패 했습니다</p>
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
  import { ref, onMounted, onBeforeUnmount } from "vue"
  import { Html5Qrcode } from "html5-qrcode"
  import axios from "axios"
  import { useRouter } from "vue-router"
  // @ts-ignore
  import CryptoJS from "crypto-js"
  const AES_KEY = CryptoJS.enc.Utf8.parse(import.meta.env.VITE_ASE_KEY) // 16자리 고정된 AES 키
  const IV = CryptoJS.enc.Utf8.parse(import.meta.env.VITE_IV)
  import { useAuthStore } from "@/stores/auth"
import { useUserStore } from "@/stores/user"
  const emit = defineEmits<{
  (event: "approveSuccess"): void
  (event: "approveFail"): void
}>()
  // 상태 값
  const showModal = ref(false) // 모달 표시 여부
  const failshowModal = ref(false)
  const qrData = ref<string | null>(null)
  const router = useRouter()
  const authStore = useAuthStore()
  

  // QR 코드 스캔 후 처리
  const handleScanSuccess = (decodedText: string, decodedResult: any) => {
    console.log("스캔된 데이터:", decodedText) // QR 코드에서 읽어온 데이터를 출력
  
    try {
      const parts = decodedText.split(":")
      const ivFromEncrypted = CryptoJS.enc.Hex.parse(parts[0])
      const encryptedData = parts[1]
  
      const decrypted = CryptoJS.AES.decrypt(encryptedData, AES_KEY, {
        iv: ivFromEncrypted,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7,
      }).toString(CryptoJS.enc.Utf8)
  
      console.log("복호화된 데이터:", decrypted)
      qrData.value = decrypted
    } catch (error) {
      console.error("QR 코드 데이터 복호화 중 에러 발생:", error)
    }
    showModal.value = true // QR 코드 스캔 후 모달 표시
  }
  
  function closefail() {
    failshowModal.value = false
  }
  
  // 연결 확인
  async function confirmConnection() {
    if (qrData.value) {

      try {
        const response = await axios.post(
          "/api/v1/family/checkFamilyLeader",
          {
            headers: {
              Authorization: `Bearer ${authStore.accessToken}`,
              "Content-Type": "application/json",
            },
          }
        )
        if (response.data.status === "success") {
            emit("approveSuccess")
        } else {
            emit("approveFail")
          failshowModal.value = true
        }
      } catch (error) {
        console.error("QR code data parsing error:", error)
        failshowModal.value = true
      }
    }
    // }
    showModal.value = false // 모달 닫기
  }
  
  // 연결 취소
  function cancelConnection() {
    showModal.value = false
  }
  
  // QR 스캐너 초기화 및 종료 처리
  let html5QrCode: Html5Qrcode | null = null
  
  onMounted(() => {
    html5QrCode = new Html5Qrcode("qr-reader")
  
    html5QrCode.start(
      { facingMode: "environment" }, // 후면 카메라 사용
      { fps: 10, qrbox: 250 },
      handleScanSuccess,
      (errorMessage) => {
        // console.error("QR 코드 스캔 오류:", errorMessage);
      }
    )
  })
  
  onBeforeUnmount(() => {
    if (html5QrCode) {
      html5QrCode
        .stop()
        .then(() => {
          console.log("QR 스캐너가 중지되었습니다.")
        })
        .catch((err) => {
          console.error("QR 스캐너 중지 중 에러 발생:", err)
        })
    }
  })
  </script>
  
  <style scoped>
  #qr-reader {
    position: relative;
    width: 250px;
    height: 250px;
    overflow: hidden;
  }
  </style>
  