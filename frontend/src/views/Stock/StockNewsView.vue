<script setup lang="ts">
import BoxNews from "@/components/box/BoxNews.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed, onMounted, reactive } from "vue"
import { useRoute } from "vue-router"
import { useStockStore } from "@/stores/stock"
import IconExplanation from "@/components/icons/IconExplanation.vue"

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
    <div class="my-auto">
      <div class="flex items-center ml-12 mt-2">
        <IconExplanation></IconExplanation>
        <h1 class="ps-3">AI가 뉴스를 요약해주었어요 🤖⚡</h1>
      </div>
    </div>
    <!-- <div>와우</div> -->
    <div v-if="news.length">
      <BoxNews v-for="article in news" :key="article.id" :article="article" />
    </div>
    <div v-else>Loading...</div>
  </div>
</template>
