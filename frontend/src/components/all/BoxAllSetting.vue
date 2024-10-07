<script setup lang="ts">
import { useRouter } from "vue-router"
import IconRightArrow from "../icons/IconRightArrow.vue"
import IconSetting from "../icons/IconSetting.vue"

// Vue Router 사용
const router = useRouter()

// 내 가족 클릭 이벤트 핸들러
const pinpad = () => {
  router.push("/pinpad")
}

const editProfile = () => {
  router.push("/editProfile")
}

const pocketmoney = () => {
  router.push("/pocketmoney")
}

const won = () => {
  router.push("/won")
}
</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="flex items-center mt-3 text-lg"><IconSetting class="ml-4 size-6" />&nbsp; 설정</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="pinpad">
      <div class="mx-8 my-4">
        <p>간편 비밀번호</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer" @click="editProfile">
      <div class="mx-8 my-4">
        <p>회원정보 수정</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer" @click="pocketmoney">
      <div class="mx-8 my-4">
        <p>용돈 수정</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between mb-3 cursor-pointer" @click="won">
      <div class="mx-8 my-4">
        <p>입금계좌 등록</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
