<script setup lang="ts">
import { onMounted, ref, watch } from "vue"
import type { LoanLog } from "@/stores/fin"

const props = defineProps<{ history: LoanLog[] | null }>()
let count = ref<number>(history.length)
let index = 0

watch(
  () => props.history,
  (newHistory) => {
    if (newHistory && newHistory.length > 0) {
      count.value = newHistory.length
      index = 0
    }
  },
  { immediate: true }
)

onMounted(() => {
  if (props.history) {
    count.value = props.history.length
    console.log("count : " + count.value)
    index = 0
  }
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg py-3 mt-8" v-if="props.history">
    <div v-for="log in props.history" :key="log.createdAt">
      <div class="m-4 flex justify-between px-5 py-1">
        <div class="text-start">
          <h1 class="font-semibold text-lg">{{ count - index++ }} 회차</h1>
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
          <h1 class="text-sm">{{ log.repayment.toLocaleString() }} 원</h1>
        </div>
      </div>
      <hr />
    </div>
  </div>
</template>
