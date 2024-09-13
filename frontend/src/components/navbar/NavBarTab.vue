<script setup lang="ts">
import { computed } from "vue"
import { RouterLink, useRoute } from "vue-router"

const props = defineProps({path: String})
const route = useRoute()
const isAtive = computed(() => {
  return (linkPath: String) => route.path === linkPath ? "bg-main-color rounded-full text-white" : ""
})
</script>

<template>
  <div class="flex justify-center p-4">
    <RouterLink to="/stock" type="button" :class="isAtive('/stock')" class="px-4"
      >홈</RouterLink>
    <RouterLink to="/stock/history" type="button" class="px-4" :class="isAtive('/stock/history')">거래내역</RouterLink>
    <RouterLink to="/stock/news" type="button" class="px-4" :class="isAtive('/stock/news')">뉴스</RouterLink>
  </div>
  <div>
    {{ props.path }}
  </div>
</template>
