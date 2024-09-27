<script setup lang="ts">
import BoxNews from "@/components/box/BoxNews.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed, onMounted, reactive } from "vue"
import { useRoute } from "vue-router"
import { useStockStore } from "@/stores/stock"

interface Article {
  id: number
  press: string
  publishDate: string
  title: string
}

const stockStore = useStockStore()
const news = reactive<Article[]>([]) // 뉴스 모음 배열

onMounted(async () => {
  const fetchedNews = await stockStore.getNews()
  news.push(...fetchedNews)
})

const route = useRoute()
const path = computed(() => route.path)
const store = useVariableStore()
store.setTitle("뉴스")
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <div v-if="news.length">
      <BoxNews v-for="article in news" :key="article.id" :article="article" />
    </div>
    <div v-else>Loading...</div>
  </div>
</template>
