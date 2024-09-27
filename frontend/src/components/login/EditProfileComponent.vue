<template>
  <div class="p-4">
    <!-- 이름 입력란 -->
    <div class="bg-gray-100 p-4 rounded-lg shadow">
      <div class="flex items-center space-x-2 mb-4">
        <span>✏️</span>
        <label for="name" class="font-bold">이름</label>
      </div>
      <input
        type="text"
        id="name"
        placeholder="이름을 입력하세요"
        v-model="user"
        class="w-full px-4 py-2 bg-gray-200 text-gray-600 rounded-lg placeholder-gray-400"
      />
    </div>

    <!-- 수정 완료 버튼 -->
    <button
      class="w-full px-4 py-3 bg-main-color text-white font-semibold rounded-lg mt-6"
      @click="sumbitchanges"
    >
      수정 완료
    </button>
    <!-- 수정완료 모달 -->
    <div v-if="showModal" class="modal">
      <img src="@/assets/common/완료 폭죽.png" />
      <span>용돈 정보 수정 완료</span>
    </div>
  </div>
</template>
<script setup lang="ts">
import axios from "axios"
import { ref } from "vue"
// ref() 유저 이름 변경시
const showModal = ref(false)
const user = ref("")
function sumbitchanges() {
  try {
    // 유저 아이디로 수정 필요
    const response = axios.post("api/v1/profile/1/update", {
      name: user.value,
    })
    console.log(user.value)

    showModal.value = true
    setTimeout(() => {
      showModal.value = false
    }, 1000)
    // console.log('이름 업데이트 성공', response);
  } catch (error) {
    console.log(error)
  }
}
</script>
<style scoped>
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  border-radius: 10px;
}
</style>
