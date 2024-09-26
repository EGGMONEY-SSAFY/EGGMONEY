<script setup lang="ts">
import NavBarFinTab from "@/components/navbar/navBarTab/NavBarFinTab.vue"
import { useVariableStore } from "@/stores/variable"
import { useRoute } from "vue-router"
const route = useRoute()
const store = useVariableStore()
store.setTitle("금융")
</script>

<template>
  <div class="">
    <NavBarFinTab />
  </div>
</template>
