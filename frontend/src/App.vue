<script setup lang="ts">
import { RouterView } from "vue-router"
import NavBar from "./components/navbar/NavBar.vue"
import NavBarTop from "./components/navbar/NavBarTop.vue"
</script>

<template>
  <div class="flex justify-center bg-gray-800">
    <div class="main-container bg-gray-200">
      <NavBarTop />
      <!-- dddddssssssds -->
      <RouterView class="mt-12 mb-24" />
      <NavBar />
    </div>
  </div>
</template>

<style scoped>
.main-container {
  width: 100%;
  height: 100%;
  min-height: 101vh;
}

@media (min-width: 393px) {
  .main-container {
    max-width: 393px;
  }
}
</style>
