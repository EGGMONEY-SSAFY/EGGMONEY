<script setup lang="ts">
import IconRightArrow from "../icons/IconRightArrow.vue"
import IconSetting from "../icons/IconAllFinance.vue"
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="flex justify-between">
      <div class="m-4">
        <p class="text-lg flex items-center mt-3"><IconSetting class="size-6 ml-4" />&nbsp; 금융</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p></p>
      </div>
    </div>
    <div class="flex justify-between cursor-pointer">
      <div class="m-4">
        <p>&nbsp;&nbsp;&nbsp;예금 상품 조회</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <div class="flex justify-between cursor-pointer">
      <div class="m-4">
        <p>&nbsp;&nbsp;&nbsp;적금 상품 조회</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>

    <!-- <div class="flex justify-between">
      <div class="m-4">
        <p>&nbsp;&nbsp;&nbsp;뉴스</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div> -->

    <div class="flex justify-between mb-3 cursor-pointer">
      <div class="m-4">
        <p>&nbsp;&nbsp;&nbsp;대출</p>
      </div>
      <div class="m-4 flex justify-center items-center">
        <p><IconRightArrow class="size-6" /></p>
      </div>
    </div>
  </div>
</template>
