<script setup lang="ts">
import { useUserStore } from "@/stores/user"
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import type { User } from "@/stores/user"

const userStore = useUserStore()
const router = useRouter()

const userSelect = ref<User | null>(null)

// 가족 탭으로 이동
const goFamilyTab = () => {
  router.push({ name: "AllView" })
}
// 출금요청심사 탭으로 이동
const goWithdrawalTab = () => {
  router.push({ name: "AllView" })
}

onMounted(async () => {
  // 유저 조회해서 유저 정보(역할, 자식 목록) 가져오기
  await userStore.getUser(3)

  //  자녀가 로그인한 경우
  if (userStore.user && userStore.user.role === "자녀") {
    userSelect.value = userStore.user
  }

  // 부모가 로그인한 경우
  else {
    console.log("부모 로그인")
    // 자식이 없다면 null, 자식이 있다면 첫 번째 자식으로 userSelect
    if (userSelect.value == null && userStore.children.length > 0) {
      console.log("자식 1명 이상")
      userSelect.value =
        userStore.children && userStore.children.length > 0 ? userStore.children[0] : null
    } else {
      console.log("가족 미구성")
    }
  }
})
</script>

<template>
  <div class="grid grid-cols-1 grid-flow-row p-5">
    <!-- 등록된 가족이 있는 경우 -->
    <div v-if="userStore.children.length > 0">
      <!-- 부모일 경우 아이 Select Box -->
      <div v-if="userStore.user && userStore.user.role === `부모`" class="p-3 flex justify-between">
        <select
          v-model="userSelect"
          class="bg-white border border-gray-200 text-black text-sm rounded-lg focus:ring-gray-500 focus:border-gray-500 block p-2"
        >
          <option
            v-for="child in userStore.children"
            :value="child"
            :key="child.userId"
            class="text-black"
          >
            {{ child.name }}
          </option>
        </select>
        <button
          class="text-sm bg-main-color rounded-full text-white text-lg py-1 px-3"
          @click="goWithdrawalTab"
          v-if="userStore.user.role === '부모'"
        >
          출금요청보기
        </button>
      </div>
      <div class="" v-if="userSelect != null">
        <!-- Main Body -->
        <RouterView :user="userSelect" />
      </div>
    </div>

    <!-- 등록된 가족이 없는 경우 -->
    <div v-else class="pt-20 text-center grid grid-cols-1 grid-flow-row">
      <h1 class="text-xl font-bold text-red-700">가족을 등록해 주세요.</h1>
      <div>
        <img src="@/assets/asset/link.png" alt="link" class="w-full p-12" />
      </div>
      <button class="bg-main-color rounded-full text-white text-lg py-2 mx-10" @click="goFamilyTab">
        등록하러가기
      </button>
    </div>
  </div>
</template>
<style>
select {
  background-color: white;
  color: black;
}
select option:hover {
  background-color: gray;
  color: white;
}
</style>
