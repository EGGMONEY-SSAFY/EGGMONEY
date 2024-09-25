<script setup lang="ts">
import { computed } from "vue"
import { RouterLink, useRoute } from "vue-router"

const props = defineProps({ path: String })
const route = useRoute()
const isAtive = computed(() => {
  return (linkPath: string) => {
    return route.path === linkPath ? "bg-main-color rounded-full text-white" : ""
  }
})
</script>

<template>
  <div class="flex justify-center pt-4 pb-2">
    <RouterLink to="/fin" type="button" :class="isAtive('/fin')" class="px-4">예금</RouterLink>
    <RouterLink to="/fin/savings" type="button" class="px-4" :class="isAtive('/fin/savings')"
      >적금</RouterLink
    >
    <RouterLink to="/fin/loan" type="button" class="px-4" :class="isAtive('/fin/loan')"
      >대출</RouterLink
    >
  </div>
  <div>
    {{ props.path }}
  </div>
</template>
