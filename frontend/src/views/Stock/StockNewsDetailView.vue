<script setup lang="ts">
import BoxNewsDetail from "@/components/box/BoxNewsDetail.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useStockStore } from "@/stores/stock"
import { computed, onMounted, reactive } from "vue"
import { useRoute } from "vue-router"

interface Article {
  id?: number
  title?: string
  link?: string
  press?: string
  content?: string
  publishDate?: string
}

const route = useRoute()
const article = reactive<Article>({})
const store = useStockStore()
onMounted(async () => {
  const newsId = route.params.newsId as string
  const fetchedArticle = await store.getArticle(newsId)
  Object.assign(article, fetchedArticle)
})

const path = computed(() => {
  return route.path
})
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <BoxNewsDetail :article="article" />
  </div>
</template>
