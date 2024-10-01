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

const router = useRouter()
const authStore = useAuthStore()
const familyId = ref("1")
const qrCode = ref<string | null>(null)
async function getfamilyId() {
  // const token = authStore.accessToken;
  const token = "tj4_dpG22TJV31lgnRgrxWH79rnno3p_AAAAAQoqJZAAAAGSQUODIpCBbdpZdq0Z"
  try {
    const response = await axios.get("http://localhost:8080/api/v1/profile/search", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
    console.log(response.data.family)
    familyId.value = response.data.family
  } catch (error) {
    console.error("유저정보 로드 오류", error)
  }
}
// Function to generate QR code
async function generateQRCode() {
  if (familyId.value != null) {
    try {
      qrCode.value = await QRCode.toDataURL(
        `http://localhost:8080/api/v1/family/${familyId.value}/join`
      )
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
