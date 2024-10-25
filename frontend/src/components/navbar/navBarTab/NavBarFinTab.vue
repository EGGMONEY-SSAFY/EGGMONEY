<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { computed } from "vue"
import { RouterLink, useRoute } from "vue-router"

const props = defineProps({ path: String })
const finStore = useFinStore()
const route = useRoute()
const isActive = computed(() => {
  return (linkPath: string) => {
    if (linkPath !== "/fin") {
      return route.path.startsWith(linkPath) ? "bg-main-color rounded-full text-white" : ""
    } else if (route.path === "/fin" || route.path.startsWith("/fin/deposit")) {
      return "bg-main-color rounded-full text-white"
    }
  }
})
</script>

<template>
  <div v-if="!finStore.isTab" class="flex justify-center pt-4 pb-2">
    <RouterLink to="/fin" type="button" :class="isActive('/fin')" class="px-4">예금</RouterLink>
    <RouterLink to="/fin/savings" type="button" class="px-4" :class="isActive('/fin/savings')"
      >적금</RouterLink
    >
    <RouterLink to="/fin/loan" type="button" class="px-4" :class="isActive('/fin/loan')"
      >대출</RouterLink
    >
  </div>
  <RouterView></RouterView>
</template>
