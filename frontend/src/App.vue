<template>
  <div class="flex justify-center bg-gray-800">
    <div class="main-container bg-gray-200">
      <RouterView v-slot="{ Component }">
      <component :is="Component" />
    </RouterView>
    </div>
  </div>
</template>

<script setup lang="ts">

</script>

<style>

</style>
