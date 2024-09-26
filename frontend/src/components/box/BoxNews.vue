<script setup lang="ts">
import IconRightArrow from "@/components/icons/IconRightArrow.vue"

interface Article {
  id: number
  press: string
  publishDate: string
  title: string
}

const props = defineProps<{ article: Article }>()
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <div class="m-4">
      <span>{{ props.article.title }}</span>
    </div>
    <RouterLink
      :to="`/stock/news/${props.article.id}`"
      class="m-4 flex justify-center items-center"
    >
      <IconRightArrow class="size-6" />
    </RouterLink>
  </div>
</template>
