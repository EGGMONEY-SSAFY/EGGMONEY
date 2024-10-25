<script setup lang="ts">
import { onMounted, ref } from "vue"
import type { TradeData } from "@/stores/asset"

const props = defineProps<{ history: TradeData[] }>()
</script>

<template>
  <div class="py-3 m-4 mt-8 bg-white rounded-lg">
    <div v-for="log in props.history" :key="log.accountId">
      <div class="flex justify-between px-5 py-1 m-4">
        <div>
          <h1 class="font-semibold">{{ log.tradeTarget }}</h1>
          <h1>
            {{
              new Date(log.createdAt).toLocaleDateString("ko-KR", {
                month: "2-digit",
                day: "2-digit",
              })
            }}
          </h1>
        </div>
        <div class="text-end">
          <h1 :class="{ 'text-red-500': log.tradePrice < 0, 'text-lg': true }">
            {{ log.tradePrice.toLocaleString() }} 원
          </h1>
          <h1 class="text-sm">{{ log.currentBalance.toLocaleString() }} 원</h1>
        </div>
      </div>
      <hr />
    </div>
  </div>
</template>
