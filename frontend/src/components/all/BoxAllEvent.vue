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

const game = () => {
  router.push("/game")
}
const roulette = () => {
  router.push("/roulette")
}

const price = () => {
  router.push("/price")
}
// const goToFamilyConnect = () => {
//   router.push("/family/family-connection")
// }
</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="flex items-center mt-3 text-lg">
          <IconFamily class="ml-4 size-6" />&nbsp; 이벤트
        </p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="goToFamily">
      <div class="mx-8 my-4">
        <p>경제용어 퀴즈</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="roulette">
      <div class="mx-8 my-4">
        <p>행운의 금융 룰렛</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer" @click="game">
      <div class="mx-8 my-4">
        <p>돈 먹기 게임</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between mb-4 cursor-pointer" @click="price">
      <div class="mx-8 my-4">
        <p>물가 맞추기 게임</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
