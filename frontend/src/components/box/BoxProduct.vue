<script setup lang="ts">
import { RouterLink } from "vue-router"

const props = defineProps({
  depositDate: { type: Number },
  depositRate: { type: Number },
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex justify-between">
    <RouterLink to="/" class="m-2 flex justify-center w-full items-center">
      <div class="m-4 w-full flex flex-col gap-y-2">
        <div class="flex justify-between">
          <span class="font-bold text-xl">계좌명</span>
        </div>
        <div class="flex justify-between">
          <span class="text-gray-400">설명설명</span>
          <span class="text-main-color font-bold">{{ props.depositDate }}개월기준</span>
        </div>
        <div class="flex justify-between">
          <span class="text-gray-400">설명설명</span>
          <span class="text-main-color font-bold">연 {{ props.depositRate }}%</span>
        </div>
      </div>
    </RouterLink>
  </div>
</template>
