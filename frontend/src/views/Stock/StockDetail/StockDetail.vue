<script setup lang="ts">
import { useVariableStore } from "@/stores/variable"
import { useRoute } from "vue-router"

const route = useRoute()
const store = useVariableStore()
const name = route.params.stock
if (typeof name === "string") {
  store.setTitle(name)
}
</script>

<template>
  <div>
    <h1>{{ name }}</h1>
  </div>
</template>
