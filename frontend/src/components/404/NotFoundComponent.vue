<template>
  <div class="mt-52 mb-4 flex flex-col justify-center items-center text-center">
    <img
  class="w-3/4 mx-4"  
  src="@/assets/all/404.png"
  alt=""
/>

    <button
      class="w-3/4 px-3 py-2 bg-orange-500 text-white font-semibold rounded-full mt-20 hover:bg-orange-600"
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
