<script setup lang="ts">
import { RouterLink, useRoute } from "vue-router"
import { computed } from "vue"
import IconAssets from "../icons/IconAssets.vue"
import IconAssetsSelected from "../icons/IconAssetsSelected.vue"

const route = useRoute()
const isActive = computed(() => {
  return route.path.includes("/assets")
})
</script>

<template>
  <RouterLink to="/assets" class="flex flex-col justify-center items-center mt-2">
    <component :is="isActive ? IconAssetsSelected : IconAssets" />
    <p class="text-sm mt-1">자산</p>
  </RouterLink>
</template>
