<template>
  <div id="invite-qr-code">
    <div v-if="qrCode">
      <img :src="qrCode" alt="QR 코드" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue"
// @ts-ignore
import QRCode from "qrcode"
import axios from "axios"
import { useAuthStore } from "@/stores/auth"
import { useRouter } from "vue-router"
// @ts-ignore
import CryptoJS from "crypto-js"
const router = useRouter()
const authStore = useAuthStore()
const familyId = ref<string | null>(null)
const qrCode = ref<string | null>(null)
const ASE_KEY = import.meta.env.VITE_ASE_KEY
async function getfamilyId() {
  const token = authStore.accessToken

  try {
    const response = await axios.get("/api/v1/profile/search", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
    const family = response.data.family.familyId
    // 가족 ID가 있는 경우 AES 암호화 후 할당
    if (family) {
      const encryptedFamilyId = CryptoJS.AES.encrypt(family.toString(), ASE_KEY).toString()
      familyId.value = encryptedFamilyId
    } else {
      // 가족 ID가 없으면 이전 페이지로 이동
      router.back()
    }
  } catch (error) {
    console.error("유저정보 로드 오류", error)
  }
}
// Function to generate QR code
async function generateQRCode() {
  if (familyId.value != null) {
    try {
      qrCode.value = await QRCode.toDataURL(`${familyId.value}`)
    } catch (error) {
      console.error("QR code generation error:", error)
    }
  } else {
    router.push("/family")
  }
}

onMounted(async () => {
  await getfamilyId()
  await generateQRCode()
})
</script>

<style scoped>
/* Add your styles here */
</style>
