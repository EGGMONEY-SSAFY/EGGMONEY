<script setup lang="ts">
interface Article {
  id: number
  press: string
  publishDate: string
  title: string
}

const props = defineProps<{ article: Article }>()
</script>

<template>
  <RouterLink
    :to="`/stock/news/${props.article.id}`"
    class="flex justify-between m-4 bg-white rounded-lg shadow"
  >
    <div class="flex flex-col w-full gap-4 m-6">
      <span class="font-bold">{{ props.article.title }}</span>
      <div class="flex justify-between">
        <span class="text-gray-400">{{ props.article.press }}</span>
        <span class="text-gray-400">{{ props.article.publishDate }}</span>
      </div>
    </div>
  </RouterLink>
</template>
