<script setup lang="ts">
import { useUserStore } from "@/stores/user"
import { useVariableStore } from "@/stores/variable"
import { ref, onMounted, computed } from "vue"
import AssetMainView from "@/views/Asset/AssetMainView.vue"
const varStore = useVariableStore()
const userStore = useUserStore()

interface User {
  userId: number
  name: string
}

const userSelect = ref<User | null>(null)

onMounted(async () => {
  varStore.setTitle("자산")
  // 유저 조회해서 유저 정보(역할, 자식 목록) 가져오기
  await userStore.getUser(3)

  //  자녀가 로그인한 경우
  if (userStore.role === "자녀") {
    userSelect.value = userStore.user
    console.log(userSelect.value)
  }

  // 부모가 로그인한 경우
  else {
    console.log("부모 로그인")
    // 자식이 없다면 null, 자식이 있다면 첫 번째 자식으로 userSelect
    if (userStore.children.length > 0) {
      console.log("자식 1명 이상")
      userSelect.value =
        userStore.children && userStore.children.length > 0 ? userStore.children[0] : null
      console.log(userSelect.value)
      console.log(userStore.children)
    } else {
      console.log("가족 미구성")
    }
  }
})
</script>

<template>
  <div>
    <!-- 등록된 가족이 있는 경우 -->
    <div v-if="userStore.familyId != null">
      <!-- 부모일 경우 아이 Select Box -->
      <div v-if="userStore.role === `부모`" class="p-3">
        <select v-model="userSelect">
          <option v-for="child in userStore.children" :value="child" :key="child.userId">
            {{ child.name }}
          </option>
        </select>
      </div>
      <div class="" v-if="userSelect != null">
        <!-- Main Body -->
        <!-- <AssetsMainView :user="userSelect" /> -->
      </div>
    </div>
    <!-- 등록된 가족이 없는 경우 -->
    <div v-else>
      <h1>가족을 등록해 주세요.</h1>
    </div>
  </div>
</template>
