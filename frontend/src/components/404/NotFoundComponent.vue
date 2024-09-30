<template>
  <div class="flex flex-col justify-center items-center h-screen text-center">
    <img src="@/assets/all/404.png" alt="" />

    <button
      class="w-full px-4 py-3 bg-orange-500 text-white font-semibold rounded-lg mt-6 hover:bg-orange-600"
      @click="moveToHomePage"
    >
      홈으로
    </button>
  </div>
</template>
<script setup lang="ts">
import { useRouter } from "vue-router"

const router = useRouter()

const moveToHomePage = () => {
  router.push("/assets")
}
</script>
