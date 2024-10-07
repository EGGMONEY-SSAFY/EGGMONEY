<script setup lang="ts">
import IconRightArrow from "../icons/IconRightArrow.vue"
import IconSetting from "../icons/IconAllStock.vue"
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
    <div class="flex justify-between cursor-pointer">
      <div class="mx-8 my-4">
        <p>주식 현재가</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer">
      <div class="mx-8 my-4">
        <p>거래내역</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between mb-3 cursor-pointer">
      <div class="mx-8 my-4">
        <p>뉴스</p>
      </div>
      <div class="flex items-center justify-center m-4">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
