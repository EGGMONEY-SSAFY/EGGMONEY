<script setup lang="ts">
import BoxNews from "@/components/box/BoxNews.vue"
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed } from "vue"
import { useRoute } from "vue-router"
import { onMounted } from "vue"
import { useStockStore } from "@/stores/stock"

const stockStore = useStockStore()

onMounted(() => {
  stockStore.getNews()
})
const path = computed(() => {
  return route.path
})

const news = stockStore.news
const route = useRoute()

const store = useVariableStore()
store.setTitle("뉴스")
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <BoxNews v-for="article in news" :key="article.id" :article="article" />
  </div>
</template>
