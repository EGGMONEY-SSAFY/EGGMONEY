<script setup lang="ts">
import { onMounted, ref, watch } from "vue"
import type { SavingsLogs } from "@/stores/fin"

const props = defineProps<{ history: SavingsLogs[] | null }>()
let count = ref<number>(0)

watch(
  () => props.history,
  (newHistory) => {
    if (newHistory && newHistory.length > 0) {
      count.value = newHistory.length
    }
  },
  { immediate: true }
)

onMounted(() => {
  if (props.history) {
    count.value = props.history.length
  }
})
</script>

<template>
  <div class="py-3 m-4 mt-8 bg-white rounded-lg" v-if="props.history">
    <div v-for="(log, idx) in props.history" :key="log.createdAt">
      <div class="flex justify-between px-5 py-1 m-4">
        <div class="text-start">
          <h1 class="text-lg font-semibold">{{ count - idx }} 회차</h1>
          <h1>
            {{
              new Date(log.createdAt).toLocaleDateString("en-US", {
                month: "2-digit",
                day: "2-digit",
              })
            }}
          </h1>
        </div>
        <div class="text-end">
          <h1 class="text-lg">{{ log.balance.toLocaleString() }} 원</h1>
          <h1 class="text-sm">{{ log.paymentMoney.toLocaleString() }} 원</h1>
        </div>
      </div>
      <hr />
    </div>
  </div>
</template>
