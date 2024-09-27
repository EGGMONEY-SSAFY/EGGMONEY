<script setup lang="ts">
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed } from "vue"
import { useRoute } from "vue-router"

const store = useVariableStore()
store.setTitle("주문현황")
const route = useRoute()
const path = computed(() => {
  return route.path
})
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <div>오더 리스트</div>
  </div>
</template>
