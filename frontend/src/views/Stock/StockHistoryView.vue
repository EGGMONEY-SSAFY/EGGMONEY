<script setup lang="ts">
import BoxBasic from "@/components/box/BoxBasic.vue";
import NavBarTab from "@/components/navbar/navBarTab/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed, ref } from "vue"
import { useRoute } from "vue-router"

const route = useRoute()
const path = computed(() => {
  return route.path
})
const store = useVariableStore()
store.setTitle("거래내역")



</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <div class="flex flex-col flex-grow justify-between">
      <BoxBasic/>
      <BoxBasic class="mb-20"/>
    </div>
  </div>
</template>
