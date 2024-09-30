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

const authStore = useAuthStore()
const familyId = ref("1")
const qrCode = ref<string | null>(null)
async function getfamilyId() {
  // const token = authStore.accessToken;
  const token = "HpAwXfMaEpHRVBLX6CvO2-LUlcUMjy1EAAAAAQorDR4AAAGSPfkK3pCBbdpZdq0Z"
  try {
    const response = await axios.get("http://localhost:8080/api/v1/profile/", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
    console.log(response)
  } catch (error) {
    console.error("유저정보 로드 오류", error)
  }
}
// Function to generate QR code
async function generateQRCode() {
  try {
    qrCode.value = await QRCode.toDataURL(
      `http://localhost:8080/api/v1/family/${familyId.value}/join`
    )
  } catch (error) {
    console.error("QR code generation error:", error)
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
