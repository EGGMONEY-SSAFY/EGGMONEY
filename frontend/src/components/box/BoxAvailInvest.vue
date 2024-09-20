<script setup lang="ts">
import IconRightArrow from "../icons/IconRightArrow.vue"
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <div class="m-4">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">10,200 알</span>
    </div>
    <RouterLink to="/" class="m-4 flex justify-center items-center">
      <IconRightArrow class="size-6" />
    </RouterLink>
  </div>
</template>
