<script setup lang="ts">
import NavBarFinTab from "@/components/navbar/navBarTab/NavBarFinTab.vue"
import { useFinStore } from "@/stores/fin"
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("금융")
</script>

<template>
  <div>
    <NavBarFinTab />
  </div>
</template>
