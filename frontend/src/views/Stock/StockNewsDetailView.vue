<script setup lang="ts">
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { computed } from "vue"
import { useRoute } from "vue-router"

const route = useRoute()

const path = computed(() => {
  return route.path
})
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <div>뉴스 디테일</div>
  </div>
</template>
