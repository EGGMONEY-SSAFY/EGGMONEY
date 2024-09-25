<script setup lang="ts">
import { computed } from "vue"
import { RouterLink, useRoute } from "vue-router"

const route = useRoute()
const isActive = computed(() => {
  return (linkPath: String) =>
    route.path === linkPath ? "bg-main-color rounded-full text-white" : ""
})
</script>

<template>
  <div class="flex justify-center pt-4 pb-2">
    <RouterLink to="/stock" type="button" :class="isActive('/stock')" class="px-4">홈</RouterLink>
    <RouterLink to="/stock/history" type="button" class="px-4" :class="isActive('/stock/history')"
      >거래내역</RouterLink
    >
    <RouterLink to="/stock/news" type="button" class="px-4" :class="isActive('/stock/news')"
      >뉴스</RouterLink
    >
  </div>
</template>
