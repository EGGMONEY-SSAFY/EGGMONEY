<template>
  <div class="flex flex-col items-center justify-center bg-gray-200 min-h-screen">
    <!-- 가족 이미지 섹션 -->
    <div class="bg-white px-4 pb-4 pt-4 rounded-lg shadow-lg w-80 max-w-xl mb-6 mx-4 mt-8">
      <img
        :src="familyImageUrl || familyDefaultImage"
        alt="가족 기본이미지"
        class="w-full h-48 object-cover mb-4"
      />
      <div class="flex flex-col justify-center items-center cursor-pointer">
        <div class="flex items-center mb-4">
          <h2 class="text-lg font-bold text-blue-700">&nbsp;착하게 살자&nbsp;</h2>
          <IconFamilyEdit />
          <br />
        </div>
        <div class="w-full flex justify-end ml-4 pr-2">
          <button class="px-2 py-2 bg-orange-500 text-white rounded-lg text-sm">가족삭제</button>
        </div>
      </div>
    </div>

    <!-- 구성원 목록 -->
    <div
      v-for="member in familyMembers"
      :key="member.id"
      class="bg-white p-4 rounded-lg shadow-lg w-80 max-w-xl mb-4 flex items-center"
    >
      <div class="bg-blue-700 p-4 rounded-lg mr-4">
        <div class="bg-white rounded-full p-2">
          <img
            :src="member.profileImageUrl"
            alt="프로필 이미지"
            class="w-12 h-12 rounded-full object-cover"
          />
        </div>
        <h2 class="text-white text-md font-bold mt-4 ml-2">{{ member.name }}</h2>
      </div>

      <div class="flex-1">
        <!-- <h2 class="text-md font-bold">{{ member.name }}</h2> -->
        <p class="text-md text-gray-700 font-bold mt-4 mb-4">
          &nbsp;구성원 역할 &nbsp;&nbsp;
          <span class="text-blue-700 font-bold">{{ member.role }}</span>
        </p>
        <button
          @click="openDeleteModal(member.id)"
          class="px-4 py-1 bg-red-500 text-white rounded-lg text-sm mt-12 ml-28"
        >
          삭제
        </button>
      </div>
    </div>
    <DeleteFamilyComponent
      v-if="selectedMemberId !== null"
      @confirmDelete="deleteSelectedMember"
      @cancelDelete="closeDeleteModal"
    />
  </div>
</template>
<script setup lang="ts">
import axios from "axios"
import { onMounted, ref } from "vue"
import familyDefaultImage from "@/assets/family/가족 기본이미지.png"
import parentDefaultImage from "@/assets/family/부모 기본이미지.png"
import daughterDefaultImage from "@/assets/family/딸 기본이미지.png"
import DeleteFamilyComponent from "./DeleteFamilyComponent.vue"
import IconFamilyEdit from "../icons/IconFamilyEdit.vue"
interface FamilyMembers {
  id: number
  name: string
  role: string
  profileImageUrl: string
}

const familyImageUrl = ref<string>("")
// const familyMembers = ref<FamilyMembers[]>([])
const familyMembers = ref<FamilyMembers[]>([
  {
    id: 1,
    name: "김엄마",
    role: "부모님",
    profileImageUrl: parentDefaultImage, // 더미 이미지 URL
  },
  {
    id: 2,
    name: "김아빠",
    role: "부모님",
    profileImageUrl: parentDefaultImage, // 더미 이미지 URL
  },
  {
    id: 3,
    name: "김자녀",
    role: "자녀",
    profileImageUrl: daughterDefaultImage, // 더미 이미지 URL
  },
])

const selectedMemberId = ref<number | null>(null)

const openDeleteModal = (memberId: number) => {
  selectedMemberId.value = memberId
}

const closeDeleteModal = () => {
  selectedMemberId.value = null
}

const fetchFamilyData = async () => {
  try {
    const familyImageResponse = await axios.get("http://localhost:8080/api/family/image")
    familyImageUrl.value = familyImageResponse.data.imageUrl

    const familyMembersResponse = await axios.get("http://localhost:8080/api/family/members")
    familyMembers.value = familyMembersResponse.data.members
  } catch (error) {
    console.error("가족 정보 업데이트 실패", error)
  }
}

const deleteSelectedMember = async () => {
  if (selectedMemberId.value !== null) {
    try {
      await axios.delete(`http://localhost:8080/api/family/member/${selectedMemberId.value}`)
      fetchFamilyData()
    } catch (error) {
      console.error("가족 맴버 삭제 실패", error)
    } finally {
      closeDeleteModal()
    }
  }
}
onMounted(() => {
  fetchFamilyData()
})
</script>
<style lang=""></style>
