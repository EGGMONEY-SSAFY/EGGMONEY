<template>
  <router-link :to="{ name: routeName }">
    <button class="bg-orange-500 text-white font-bold py-2 w-full rounded-full">다음</button>
  </router-link>
</template>

<script setup lang="ts">

const props = defineProps({
  routeName: {
    type: String,
    required: true,
  },
})
</script>
