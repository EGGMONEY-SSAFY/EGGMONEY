<script setup lang="ts">
import IconRightArrow from "../icons/IconRightArrow.vue"
import IconSetting from "../icons/IconAllStock.vue"
import { useRouter } from "vue-router"

const router = useRouter()
function log() {
  router.push("/stock/log")
}
function list() {
  router.push("/stock/order-list")
}
function news() {
  router.push("/stock/news")
}

</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="flex items-center mt-3 text-lg"><IconSetting class="ml-4 size-6" />&nbsp; 증권</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="log">
      <div class="mx-8 my-4">
        <p>거래내역</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer" @click="list">
      <div class="mx-8 my-4">
        <p>주문현황</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between mb-3 cursor-pointer" @click="news">
      <div class="mx-8 my-4">
        <p>뉴스</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
