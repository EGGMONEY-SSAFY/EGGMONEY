<script setup lang="ts">
const props = defineProps({
  article: Object,
})
</script>

<template>
  <div class="flex flex-col m-4 bg-white rounded-lg shadow" v-if="props.article?.content">
    <div class="m-4 text-2xl text-center">
      <span class="font-bold">{{ props.article?.title }}</span>
    </div>
    <div class="flex justify-between m-4">
      <span>{{ props.article?.press }}</span>
      <span>{{ props.article?.publishDate }}</span>
      <a :href="props.article?.link" target="_blank" rel="noopener noreferrer">원문보기</a>
    </div>
    <div class="m-4" v-for="paragraph in props.article?.content.split('\n\n')" :key="paragraph">
      {{ paragraph }}
    </div>
  </div>
</template>
