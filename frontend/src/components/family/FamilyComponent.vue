<template>
  <div class="p-6 min-h-screen bg-gray-100">
    <div class="space-y-8">
      <div
        @click="goToCreateFamily"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/가족 생성.png" alt="가족 생성 아이콘" class="w-16 h-16" />
        <!-- 이미지 크기 증가 -->
        <p class="text-2xl font-semibold">가족 생성</p>
      </div>

      <div
        @click="goToMyFamily"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/내 가족.png" alt="내 가족 아이콘" class="w-16 h-16" />
        <!-- 이미지 크기 증가 -->
        <p class="text-2xl font-semibold">내 가족</p>
      </div>

      <div
        @click="goToFamilyInvite"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/가족 초대.png" alt="가족 초대 아이콘" class="w-16 h-16" />
        <!-- 이미지 크기 증가 -->
        <p class="text-2xl font-semibold">가족 초대</p>
      </div>

      <div
        @click="goToFamilyConnection"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/가족 연결.png" alt="가족 연결 아이콘" class="w-16 h-16" />
        <!-- 이미지 크기 증가 -->
        <p class="text-2xl font-semibold">가족 연결</p>
      </div>
    </div>
    <!-- 모달 창 -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
    >
      <div class="bg-white p-6 rounded-lg shadow-lg text-center">
        <h1 class="text-lg font-bold">가족 프로필 사진 등록</h1>
        <input
          type="file"
          @change="handleFileUpload"
          class="p-2 border border-gray-300 rounded mt-4 w-full"
        />
        <input
          type="text"
          v-model="familyDescription"
          placeholder="가족 소개"
          class="p-2 border border-gray-300 rounded mt-4 w-full"
        />
        <button
          class="px-4 py-2 bg-orange-500 text-white font-semibold rounded-lg mt-4"
          @click="createFamily"
        >
          가족 생성
        </button>
        <button
          class="px-4 py-2 bg-red-500 text-white font-semibold rounded-lg mt-4"
          @click="closeModal"
        >
          닫기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios"
import { ref } from "vue"
import { useRouter } from "vue-router"
import { useAuthStore } from "@/stores/auth"
const authStore = useAuthStore()
const showModal = ref(false)
const familyDescription = ref("")
const imageBase64 = ref<string>("")
const router = useRouter()
function handleFileUpload(event: Event) {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  if (file) {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => {
      if (typeof reader.result === "string") {
        imageBase64.value = reader.result
      }
    }
  }
}
// 페이지 이동 함수들
function goToCreateFamily() {
  showModal.value = true
}
function closeModal() {
  showModal.value = false
}
async function createFamily() {
  if (!imageBase64.value || !familyDescription.value) {
    alert("이미지와 가족 소개를 입력해주세요.")
    return
  }

  const familyData = {
    //familyDescription: familyDescription.value,
    //familyImage: imageBase64.value,
    intro: "hi1",
  }

  try {
    const token = authStore.accessToken
    console.log(token)
    await axios.post("http://localhost:8080/api/v1/family/create", familyData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
        //withCredentials: true,1
      },
    })
    alert("가족 생성 완료!")
    showModal.value = false
  } catch (error) {
    console.error("가족 생성 오류:", error)
    alert("가족 생성에 실패했습니다.")
  }
}
function goToMyFamily() {
  router.push("/family/my-family")
}

function goToFamilyInvite() {
  router.push("/family/family-invite")
}

function goToFamilyConnection() {
  router.push("/family/family-connection")
}
</script>

<style scoped></style>
