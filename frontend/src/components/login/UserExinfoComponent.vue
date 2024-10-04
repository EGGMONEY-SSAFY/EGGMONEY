<template>
  <div class="flex flex-col items-center justify-center h-screen bg-gray-100">
    <div class="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
      <div class="flex items-center mb-6">
        <h2 class="text-xl font-bold text-gray-800">👪 사용자 유형 선택</h2>
      </div>

      <div class="bg-gray-100 rounded-lg p-4">
        <div class="bg-white rounded-lg p-4">
          <div
            class="flex flex-col items-center mb-6 cursor-pointer"
            :class="{
              'border-4 border-orange-500': role === '자녀',
              'border-2 border-gray-300': role !== '자녀',
            }"
            @click="selectRole('자녀')"
          >
            <img src="@/assets/common/부모 닭.png" alt="부모" class="w-24 h-24 mb-2" />
            <span class="text-lg font-bold text-gray-800">부모</span>
          </div>
          <hr class="my-4" />
          <div
            class="flex flex-col items-center cursor-pointer"
            :class="{
              'border-4 border-orange-500': role === '부모',
              'border-2 border-gray-300': role !== '부모',
            }"
            @click="selectRole('부모')"
          >
            <img src="@/assets/common/아기 닭.png" alt="자녀" class="w-24 h-24 mb-2" />
            <span class="text-lg font-bold text-gray-800">자녀</span>
          </div>
        </div>
      </div>

      <button
        class="w-full px-4 py-3 bg-orange-500 text-white font-semibold rounded-lg mt-6 hover:bg-orange-600"
        @click="submitExinfo"
      >
        회원가입 완료
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, type Ref } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"
import { useAuthStore } from "@/stores/auth"

const authStore = useAuthStore()
const router = useRouter()
const role = ref<String>("최초") as Ref<String>
const selectRole = (selectedRole: String) => {
  role.value = selectedRole
  console.log(role.value)
}
const submitExinfo = async () => {
  if (role.value === "최초") {
    alert("역할을 선택해주세요")
    return
  }
  const token = authStore.accessToken;
  //const token = "HpAwXfMaEpHRVBLX6CvO2-LUlcUMjy1EAAAAAQorDR4AAAGSPfkK3pCBbdpZdq0Z"
  try {
    const response = await axios.post(
      "/api/v1/profile/update/ExInfo",
      { role: role.value },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    )
    console.log(response)
    router.push("/won")
  } catch (error) {
    console.error("Error:", error)
  }
}
</script>
<style scoped></style>
