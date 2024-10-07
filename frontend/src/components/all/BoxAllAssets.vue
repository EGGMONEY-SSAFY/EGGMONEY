<script setup lang="ts">
import IconRightArrow from "../icons/IconRightArrow.vue"
import IconSetting from "../icons/IconAllAssets.vue"
import { useRouter } from "vue-router"

const router = useRouter()
function goAsset() {
  router.push({ name: "AssetMainView" })
}
function goWithdrawalRequest() {
  router.push({ name: "AssetWithdrawalView" })
}
</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="flex items-center mt-3 text-lg"><IconSetting class="ml-4 size-6" />&nbsp; 자산</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer" @click="goAsset">
      <div class="mx-8 my-4">
        <p>자산</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between mb-3 cursor-pointer" @click="goWithdrawalRequest">
      <div class="mx-8 my-4">
        <p>출금 요청</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
