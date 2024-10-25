<template>
  <div class="flex flex-col items-center justify-center mb-4 text-center mt-52">
    <img class="w-3/4 mx-4" src="@/assets/all/404.png" alt="" />

    <button
      class="w-3/4 px-3 py-2 font-semibold text-white bg-orange-500 rounded-full hover:bg-orange-600"
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
  router.push("/asset")
}
</script>
