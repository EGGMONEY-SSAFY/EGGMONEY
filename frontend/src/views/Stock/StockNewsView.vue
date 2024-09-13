<script setup lang="ts">
import NavBarTab from "@/components/navbar/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable";
import { computed, onBeforeMount } from "vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const path = computed(() => {
  return route.path
})

const store = useVariableStore()
store.setTitle("뉴스")

</script>

<template>
  <div class="">
    <NavBarTab :path="path" />
    <h1>This is an news page</h1>
    <h1>{{ path }}</h1>
    <h1>{{ store.title }}</h1>
  </div>
</template>
