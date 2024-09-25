<script setup lang="ts">
import IconExplanation from "@/components/icons/IconExplanation.vue"
import { useFinStore } from "@/stores/fin"
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"

const route = useRoute()
const finStore = useFinStore()
const productId = Number(route.params.productId)

const selectProduct = ref<Object>()

onMounted(async () => {})
</script>

<template>
  <div class="m-4">
    <div class="flex justify-center items-center">
      <div class="m-2">
        <IconExplanation></IconExplanation>
      </div>
      <div class="text-gray-600 font-bold">선택하신 상품은 {{}} 입니다</div>
    </div>
  </div>
</template>
