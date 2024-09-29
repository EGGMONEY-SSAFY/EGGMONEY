<script setup lang="ts">
const props = defineProps({
  content: {
    type: String,
    default: "다음",
  },
})
</script>
<template>
  <button class="bg-orange-500 text-white font-bold py-2 w-full rounded-full">
    {{ content }}
  </button>
</template>
