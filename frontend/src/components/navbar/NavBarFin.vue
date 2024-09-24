<script setup lang="ts">
import { RouterLink, useRoute } from "vue-router"
import { computed } from "vue"
import IconFin from "../icons/IconFin.vue"
import IconFinSelected from "../icons/IconFinSelected.vue"

const route = useRoute()
const isActive = computed(() => {
  return route.path.includes("/fin")
})
</script>

<template>
  <RouterLink to="/fin" class="flex flex-col justify-center items-center mt-2">
    <component :is="isActive ? IconFinSelected : IconFin" />
    <p class="text-sm mt-1">금융</p>
  </RouterLink>
</template>
