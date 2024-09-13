<script setup lang="ts">
import NavBarTab from "@/components/navbar/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable";
import { computed } from "vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const path = computed(() => {
  return route.path
})
const store = useVariableStore()
store.setTitle("거래내역")
</script>

<template>
  <div class="">
    <NavBarTab :path="path" />
    <h1>This is an history page</h1>
  </div>
</template>
