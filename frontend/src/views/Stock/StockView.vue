<script setup lang="ts">
import NavBarTab from "@/components/navbar/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed, onBeforeMount } from "vue"
import { useRoute, useRouter } from "vue-router"

const store = useVariableStore()
store.setTitle("증권")
</script>

<template>
  <div class="">
    <NavBarTab />
    <h1>This is an stock page</h1>
  </div>
</template>
