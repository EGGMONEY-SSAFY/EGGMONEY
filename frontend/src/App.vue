<script setup lang="ts">
import { RouterView } from "vue-router"
import NavBar from "./components/navbar/NavBar.vue"
import NavBarTop from "./components/navbar/NavBarTop.vue"
import { useFinStore } from "./stores/fin"
import { useAuthStore } from "@/stores/auth"
import { onMounted } from "vue"
const finStore = useFinStore()

const authStore = useAuthStore()
onMounted(() => {
  authStore.loadTokens()
})
</script>

<template>
  <!-- teddd2225dddddddddd22225-->
  <div class="flex justify-center bg-gray-800">
    <div
      class="main-container bg-gray-200"
      :class="{ 'bg-yellow-50': finStore.isYellowPage, 'bg-gray-200': !finStore.isYellowPage }"
    >
      <NavBarTop />
      <RouterView class="mt-12 mb-20" />
      <NavBar />
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
