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
const a = import.meta.env.VITE_AES_KEY
console.log(a)
const AES_KEY = CryptoJS.enc.Utf8.parse("EGGMONEY12345678") // 16자리 고정된 AES 키
const IV = CryptoJS.enc.Utf8.parse("abcdefg123456789")
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
      // AES 암호화
      const encrypted = CryptoJS.AES.encrypt(family.toString(), AES_KEY, {
        iv: IV,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7,
      })

      // IV + 암호화된 데이터
      const encryptedFamilyId = IV.toString(CryptoJS.enc.Hex) + ":" + encrypted.toString()
      console.log("암호화된 데이터 (IV 포함):", encryptedFamilyId)

      // 복호화 테스트
      const parts = encryptedFamilyId.split(":")
      const ivFromEncrypted = CryptoJS.enc.Hex.parse(parts[0])
      const encryptedData = parts[1]

      const decrypted = CryptoJS.AES.decrypt(encryptedData, AES_KEY, {
        iv: ivFromEncrypted,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7,
      }).toString(CryptoJS.enc.Utf8)

      console.log("복호화된 데이터:", decrypted)

      familyId.value = encryptedFamilyId
    } else {
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
