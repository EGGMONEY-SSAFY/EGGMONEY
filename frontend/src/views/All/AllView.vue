<script setup lang="ts">
import BoxAllAssets from "@/components/all/BoxAllAssets.vue"
import BoxAllFamily from "@/components/all/BoxAllFamily.vue"
import BoxAllFinance from "@/components/all/BoxAllFinance.vue"
import BoxAllSetting from "@/components/all/BoxAllSetting.vue"
import BoxAllStock from "@/components/all/BoxAllStock.vue"
import BoxAllLogout from "@/components/all/BoxAllLogout.vue"
import BoxAllEvent from "@/components/all/BoxAllEvent.vue"
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("전체")
</script>

<template>
  <div class="main-container bg-gray-200">
    <div className="mt-16">
      <BoxAllSetting />
      <BoxAllFamily />
      <BoxAllEvent />
      <BoxAllAssets />
      <BoxAllFinance />
      <BoxAllStock />
      <BoxAllLogout />
    </div>
  </div>
</template>

<style scoped>
.main-container {
  width: 100%;
  height: 100%;
  min-height: 101vh;
}

@media (min-width: 393px) {
  .main-container {
    max-width: 393px;
  }
}
</style>
