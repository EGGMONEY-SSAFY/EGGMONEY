<script setup lang="ts">
const props = defineProps({
  price: Number,
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <div class="m-4">
      <span>현재 가격</span>
    </div>
    <div class="m-4">
      <span>{{ props.price }} 알</span>
    </div>
  </div>
</template>
