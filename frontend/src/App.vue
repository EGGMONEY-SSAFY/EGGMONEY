<script setup lang="ts">
import { RouterView, useRoute, useRouter } from "vue-router"
import NavBar from "./components/navbar/NavBar.vue"
import NavBarTop from "./components/navbar/NavBarTop.vue"
import { useFinStore } from "./stores/fin"
import { useAuthStore } from "@/stores/auth"
import { onMounted, ref } from "vue"
import { useUserStore } from "./stores/user"
const finStore = useFinStore()
const route = useRoute() // 현재 경로를 가져옴12322222222222
const authStore = useAuthStore()
const router = useRouter()
const userStore = useUserStore()
const isLoading = ref(true)

onMounted(async () => {
  console.log("App.vue Loaded")
  try {
    await authStore.loadTokens(router)
    await userStore.getUser()
    console.log("App.vue 작업 완료")
  } catch (error) {
    console.error("에러 발생:", error)
    // 에러 처리 로직 추가
  } finally {
    isLoading.value = false
  }
})
</script>

<template>
  <!-- test224fss4sssssssㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ -->
  <!-- <div class="flex justify-center bg-gray-800">
    <div
      class="bg-gray-200 main-container"
      :class="{ 'bg-yellow-50': finStore.isYellowPage, 'bg-gray-200': !finStore.isYellowPage }"
    >
      <NavBarTop />
      <RouterView class="mt-12 mb-20" />
      <NavBar />
    </div>
  </div> -->

  <div class="flex justify-center bg-gray-800">
    <div v-if="isLoading">
      <p>Loading...</p>
    </div>
    <div
      class="bg-gray-200 main-container"
      :class="{ 'bg-yellow-50': finStore.isYellowPage, 'bg-gray-200': !finStore.isYellowPage }"
      v-else
    >
      <!-- /main 또는 /login일 경우 상단바와 하단바 숨김 -->
      <NavBarTop v-if="route.path !== '/main' && route.path !== '/login'" />
      <RouterView :class="{ 'mt-12 mb-20': route.path !== '/main' && route.path !== '/login' }" />
      <NavBar v-if="route.path !== '/main' && route.path !== '/login'" />
    </div>
  </div>
</template>

<style scoped>
.main-container {
  width: 100%;
  height: 100%;
  min-height: 100.1vh;
}

@media (min-width: 393px) {
  .main-container {
    max-width: 393px;
  }
}
</style>
