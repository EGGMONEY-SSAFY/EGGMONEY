<script setup lang="ts">
import IconRightArrow from "@/components/icons/IconRightArrow.vue"
</script>

<template>
  <div class="flex justify-between m-4 bg-white rounded-lg shadow">
    <div class="m-4">
      <span>기본 박스</span>
    </div>
    <RouterLink to="/" class="flex items-center justify-center m-4">
      <IconRightArrow class="size-6" />
    </RouterLink>
  </div>
</template>
