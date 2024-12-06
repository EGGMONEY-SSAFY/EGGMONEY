<script setup lang="ts">
import { useFinStore } from "@/stores/fin"
import { watch } from "vue"
import { onMounted, onUnmounted, ref } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()
const finStore = useFinStore()
const remainingTime = ref(10)
let countInterval: number | null = null

onMounted(() => {
  if (countInterval) {
    clearInterval(countInterval)
  }
  countInterval = window.setInterval(() => {
    remainingTime.value--
  }, 1000)
})
watch(remainingTime, (newValue) => {
  if (newValue <= 0) {
    clearInterval(countInterval!)
    router.push("/asset")
  }
})

onUnmounted(() => {
  if (countInterval) {
    clearInterval(countInterval)
    countInterval = null
  }
})
</script>
<template>
  <div class="flex flex-col items-center justify-start mb-4 text-center mt-40">
    <div class="text-center font-bold m-4 text-red-500">
      {{ remainingTime }}초 후에 홈으로 이동합니다
    </div>
    <img class="w-3/4 mx-4" src="@/assets/fin/No.png" alt="" />
    <div v-if="finStore.errMessage" class="text-xl font-bold text-red-500 mt-4">
      ※ {{ finStore.errMessage }} ※
    </div>
  </div>
</template>
