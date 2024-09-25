<template>
  <div class="p-6 min-h-screen bg-gray-100">
    <div class="space-y-8">
      <!-- 가족 생성 버튼 -->
      <div
        @click="goToCreateFamily"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/가족 생성.png" alt="가족 생성 아이콘" class="w-16 h-16" />
        <p class="text-2xl font-semibold">가족 생성</p>
      </div>

      <!-- 내 가족 버튼 -->
      <div
        @click="goToMyFamily"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/내 가족.png" alt="내 가족 아이콘" class="w-16 h-16" />
        <p class="text-2xl font-semibold">내 가족</p>
      </div>

      <!-- 가족 초대 버튼 -->
      <div
        @click="goToFamilyInvite"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/가족 초대.png" alt="가족 초대 아이콘" class="w-16 h-16" />
        <p class="text-2xl font-semibold">가족 초대</p>
      </div>

      <!-- 가족 연결 버튼 -->
      <div
        @click="goToFamilyConnection"
        class="flex items-center space-x-6 cursor-pointer bg-white hover:bg-gray-100 p-8 rounded-lg shadow-md transition-transform transform hover:scale-105"
      >
        <img src="@/assets/family/가족 연결.png" alt="가족 연결 아이콘" class="w-16 h-16" />
        <p class="text-2xl font-semibold">가족 연결</p>
      </div>
    </div>

    <!-- 가족 생성 모달 창 -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
    >
      <div
        class="bg-white p-6 rounded-lg shadow-lg text-center w-full max-w-xs sm:max-w-sm mx-4"
      >
        <h1 class="text-lg font-bold">가족 프로필 사진 등록</h1>
        <!-- 이미지를 클릭하면 파일 업로드 창이 열리도록 설정 -->
        <div @click="triggerFileUpload" class="cursor-pointer">
          <img
            v-if="imageBase64"
            :src="imageBase64"
            alt="가족 프로필 이미지"
            class="w-24 h-24 rounded-full object-cover mx-auto"
          />
          <img
            v-else
            src="@/assets/family/가족 이미지.png"
            alt="프로필 사진 선택"
            class="w-24 h-24 rounded-full mx-auto"
          />
        </div>

        <!-- 파일 선택 input 요소는 숨김 -->
        <input
          ref="fileInput"
          type="file"
          accept="image/*"
          @change="handleFileUpload"
          class="hidden"
        />
        <!-- 가족 소개 입력 필드 -->
        <input
          type="text"
          v-model="familyDescription"
          placeholder="가족 소개"
          class="p-2 border border-gray-300 rounded mt-4 w-full"
        />

        <!-- 가족 생성 버튼 -->
        <button
          class="px-4 py-2 bg-orange-500 text-white font-semibold rounded-lg mt-4"
          @click="createFamily"
        >
          가족 생성
        </button>
        <button class="px-4 py-2 bg-red-500 text-white font-semibold rounded-lg mt-4" @click="closeModal">
        닫기
      </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();
const showModal = ref(false);
const familyDescription = ref("");
const imageBase64 = ref<string>("");
const fileInput = ref<HTMLInputElement | null>(null);
const router = useRouter();

// 파일 업로드 트리거
function triggerFileUpload() {
  fileInput.value?.click();
}

// 파일 업로드 처리
function handleFileUpload(event: Event) {
  const target = event.target as HTMLInputElement;
  const file = target.files?.[0];
  if (file) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      if (typeof reader.result === "string") {
        imageBase64.value = reader.result;
      }
    };
  }
}

// 모달 열기
function goToCreateFamily() {
  showModal.value = true;
}

// 모달 닫기
function closeModal() {
  showModal.value = false;
}

// 가족 생성 처리
async function createFamily() {
  // if (!imageBase64.value || !familyDescription.value) {
  //   alert("이미지와 가족 소개를 입력해주세요.");
  //   return;
  // }

  // const familyData = {
  //   familyDescription: familyDescription.value,
  //   familyImage: imageBase64.value,
  // };

  // try {
  //   const token = authStore.accessToken;
  //   console.log(token);
  //   await axios.post("http://localhost:8080/api/v1/family/create", familyData, {
  //     headers: {
  //       Authorization: `Bearer ${token}`,
  //       "Content-Type": "application/json",
  //     },
  //   });
    //alert("가족 생성 완료!");
    showModal.value = false;
    router.push("/family/create");
  // } catch (error) {
  //   console.error("가족 생성 오류:", error);
  //   alert("가족 생성에 실패했습니다.");
  // }
}

// 페이지 이동 함수들
function goToMyFamily() {
  router.push("/family/my-family");
}

function goToFamilyInvite() {
  router.push("/family/family-invite");
}

function goToFamilyConnection() {
  router.push("/family/family-connection");
}
</script>

<style scoped></style>
