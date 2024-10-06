<script setup lang="ts">
import { useRouter } from "vue-router"
import IconRightArrow from "../icons/IconRightArrow.vue"
import IconFamily from "../icons/IconEvent.vue"

// Vue Router 사용
const router = useRouter()

// 내 가족 클릭 이벤트 핸들러
const goToFamily = () => {
  router.push("/event")
}

// const goToFamilyConnect = () => {
//   router.push("/family/family-connection")
// }
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="text-lg flex items-center mt-3">
          <IconFamily class="size-6 ml-4" />&nbsp; 이벤트
        </p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="goToFamily">
      <div class="my-4 mx-8">
        <p>스피드 퀴즈</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <!-- <div class="flex justify-between mb-3 cursor-pointer" @click="goToFamilyConnect">
      <div class="my-4 mx-8">
        <p>가족 연결</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div> -->
  </div>
</template>
