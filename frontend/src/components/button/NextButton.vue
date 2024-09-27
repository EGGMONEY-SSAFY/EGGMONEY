<script setup lang="ts">
import { defineProps } from "vue"

const props = defineProps({
  routeName: {
    type: String,
    required: true,
  },
  content: {
    type: String,
    default: "다음",
  },
  disabled: {
    type: Boolean,
    default: true,
  },
})
</script>
<template>
  <router-link :to="{ name: routeName }">
    <button class="bg-orange-500 text-white font-bold py-2 w-full rounded-full">
      {{ content }}
    </button>
  </router-link>
</template>
