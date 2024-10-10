<template>
  <div class="flex flex-col items-center justify-center bg-gray-200 min-h-screen">
    <!-- 가족 이미지 섹션 -->
    <div class="bg-white px-4 pb-4 pt-4 rounded-lg shadow-lg w-80 max-w-xl mb-6 mx-4 mt-8">
      <div @click="triggerFileUpload" class="cursor-pointer">
        <img
          :src="familyImageUrl || familyDefaultImage"
          alt="가족 기본이미지"
          class="w-full h-48 object-cover mb-4"
        />
      </div>
      <!-- 숨겨진 파일 입력 -->
      <input
        ref="fileInput"
        type="file"
        accept="image/*"
        @change="uploadAndSaveFamilyInfo"
        class="hidden"
      />

      <div class="flex flex-col justify-center items-center cursor-pointer">
        <div class="flex items-center mb-4" @click="editIntro = true">
          <h2 v-if="!editIntro" class="text-lg font-bold text-blue-700">{{ familyInfo.intro }}</h2>
          <input
            v-else
            v-model="editedIntro"
            @blur="uploadAndSaveFamilyInfo"
            @keyup.enter="uploadAndSaveFamilyInfo"
            class="text-lg font-bold text-blue-700"
          />
          <IconFamilyEdit />
          <br />
        </div>
        <div class="w-full flex justify-end ml-4 pr-2">
          <button
            v-if="userStore.user && userStore.user.role === '부모'"
            @click="deleteFamily"
            class="px-2 py-2 bg-orange-500 text-white rounded-lg text-sm"
          >
            가족삭제
          </button>
        </div>
      </div>
    </div>

    <!-- 구성원 목록 -->
    <div
      v-for="member in familyMembers"
      :key="member.userId"
      class="bg-white p-4 rounded-lg shadow-lg w-80 max-w-xl mb-4 flex items-center"
    >
      <div class="bg-blue-700 p-4 rounded-lg mr-4">
        <div class="bg-white rounded-full p-2">
          <img
            :src="
              member.profileImageUrl ||
              (member.role === '부모' ? parentDefaultImage : daughterDefaultImage)
            "
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
          v-if="userStore.user && userStore.user.role === '부모'"
          @click="openDeleteModal(member.userId)"
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
import { useAuthStore } from "@/stores/auth"
import { useRouter } from "vue-router"
import { useUserStore } from "@/stores/user"
const userStore = useUserStore()
interface FamilyInfo {
  intro: string
  profileImageUrl: string
}
interface FamilyMembers {
  userId: number
  name: string
  role: string
  profileImageUrl: string
}
const editedIntro = ref<string>("")
const editIntro = ref<boolean>(false)
const editImage = ref<boolean>(false)
const authStore = useAuthStore()
const token = authStore.accessToken
const familyImageUrl = ref<string>("")
const familyInfo = ref<FamilyInfo>({
  intro: "",
  profileImageUrl: "",
})
const fileInput = ref<HTMLInputElement | null>(null)
const familyMembers = ref<FamilyMembers[]>([])

const selectedMemberId = ref<number | null>(null)
const router = useRouter()
const openDeleteModal = (memberId: number) => {
  selectedMemberId.value = memberId
}

const closeDeleteModal = () => {
  selectedMemberId.value = null
}
const triggerFileUpload = () => {
  fileInput.value?.click()
}
const fetchFamilyData = async () => {
  try {
    const familyResponse = await axios.get("/api/v1/family/searchFamily", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    console.log(familyResponse)
    if (!familyResponse.data) {
      alert("가족 정보가 존재하지 않습니다.")
      router.back() // 이전 페이지로 이동
    } else {
      familyInfo.value.intro = familyResponse.data.intro // 가훈 할당
      familyImageUrl.value = familyResponse.data.profileImageUrl // 가족 이미지 할당
      familyMembers.value = familyResponse.data.members // 가족 구성원 할당
    }
  } catch (error) {
    console.error("가족 정보 업데이트 실패", error)
    router.back() // 오류 발생 시 이전 페이지로 이동
  }
}
const deleteFamily = async () => {
  try {
    await axios.post(
      "/api/v1/family/delete",
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    alert("가족 삭제 완료")
    // 삭제 후 새로고침
    fetchFamilyData()
  } catch (error) {
    console.error("가족 삭제 실패", error)
  }
}

const deleteSelectedMember = async () => {
  console.log(selectedMemberId.value)
  if (selectedMemberId.value !== null) {
    try {
      await axios.post(
        `/api/v1/family/delete/member`,
        { memberId: selectedMemberId.value },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      fetchFamilyData()
    } catch (error) {
      console.error("가족 맴버 삭제 실패", error)
    } finally {
      closeDeleteModal()
    }
  }
}
const uploadAndSaveFamilyInfo = async (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0]
  let imageUrl = familyImageUrl.value
  if (familyImageUrl.value == null) {
    imageUrl = ""
  }

  if (file) {
    const formData = new FormData()
    formData.append("file", file)

    try {
      const response = await axios.post("/api/v1/family/upload-profile", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
      })

      imageUrl = response.data.imageUrl
    } catch (error) {
      console.error("이미지 업로드 실패", error)
      return
    }
  }
  console.log(imageUrl)
  try {
    await axios.post(
      `/api/v1/family/update`,
      {
        intro: editedIntro.value,
        profileImageUrl: imageUrl || null,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    familyInfo.value.intro = editedIntro.value || ""
    familyImageUrl.value = imageUrl || ""
    editIntro.value = false
    fetchFamilyData()
  } catch (error) {
    console.error("가족 정보", error)
  }
}
onMounted(() => {
  fetchFamilyData()
})
</script>
<style lang=""></style>

<!-- try{
  //   // 가훈 및 가족 정보 찾기 
  //   const familyResponse = await axios.get("/api/v1/family/searchFamily", {
  //     headers: {
  //       Authorization: `Bearer ${token}`,
  //     },
  //   });
  //   // 가족 정보가 없으면 이전 페이지로 돌아가기
  //   if (!familyResponse.data ) {
  //     alert("가족 정보가 존재하지 않습니다.");
  //     router.back(); // 이전 페이지로 이동
  //   } else {
  //     familyInfo.value = familyResponse.data;
  //   }
  // }catch (error) {
  //   console.error("가족 정보 업데이트 실패", error);
  //   router.back();
  // }
  // try {
  //   // familyImageUrl.value = familyImageResponse.data.imageUrl

  //   const familyMembersResponse = await axios.get(
  //     "/api/v1/family/searchMember",
  //     {
  //       headers: {
  //         Authorization: `Bearer ${token}`, // 토큰이 필요한 경우 추가
  //       },
  //     }
  //   )
   
  //     familyMembers.value = familyMembersResponse.data;
    
  // } catch (error) {
  //   console.error("가족 정보 업데이트 실패", error);

  // } -->
<!-- 
  {
    userId: 1,
    name: "김엄마",
    role: "부모님",
    profileImageUrl: parentDefaultImage, // 더미 이미지 URL
  },
  {
    userId: 2,
    name: "김아빠",
    role: "부모님",
    profileImageUrl: parentDefaultImage, // 더미 이미지 URL
  },
  {
    userId: 100,
    name: "김자녀",
    role: "자녀",
    profileImageUrl: daughterDefaultImage, // 더미 이미지 URL
  }, -->
<!-- const onImageChange = async (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0]
  if (file) {
    const formData = new FormData()
    formData.append("file", file)

    try {
      const response = await axios.post("/api/v1/family/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
      })
      familyImageUrl.value = response.data.imageUrl // 업로드된 이미지 URL 저장
      fetchFamilyData() // 이미지 업데이트 후 가족 정보 업데이트 실행
    } catch (error) {
      console.error("이미지 업로드 실패", error)
    }
  }
}
const updateFamilyInfo = async () => {
  try {
    await axios.post(
      `/api/v1/family/update`,
      {
        intro: editedIntro.value,
        // profileImageUrl:familyImageUrl.value,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    familyInfo.value.intro = editedIntro.value
    editIntro.value = false
    fetchFamilyData()
  } catch (error) {
    console.error("가족 정보 업데이트 실패", error)
  }
} -->
