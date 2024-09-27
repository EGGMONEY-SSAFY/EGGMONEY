<script setup lang="ts">
const props = defineProps({
  article: Object,
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="m-4 text-center text-2xl">
      <span class="font-bold">{{ props.article?.title }}</span>
    </div>
    <div class="m-4 flex justify-between">
      <span>{{ props.article?.press }}</span>
      <span>{{ props.article?.publishDate }}</span>
      <a :href="props.article?.link" target="_blank" rel="noopener noreferrer">원문보기</a>
    </div>
    <div class="m-4">
      <span>{{ props.article?.content }}</span>
    </div>
  </div>
</template>
