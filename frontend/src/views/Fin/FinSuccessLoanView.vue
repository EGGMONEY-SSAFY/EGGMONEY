<script setup lang="ts">
import router from "@/router"
import { useFinStore } from "@/stores/fin"
import { useUserStore } from "@/stores/user"
import { onMounted, ref, onUnmounted } from "vue"

const finStore = useFinStore()
const userStore = useUserStore()

const role = ref("")
onMounted(() => {
  finStore.isYellowPage = true
  finStore.isTab = true
  setTimeout(() => {
    router.push({ name: "AssetLoanListView" })
  }, 3000)
})

onUnmounted(() => {
  finStore.isYellowPage = false
  finStore.isTab = false
})
</script>

<template>
  <div class="bg-yellow-50">
    <div class="h-[78vh] pt-20 flex flex-col gap-20">
      <div class="text-center text-3xl font-bold">대출 신청 성공!</div>
      <div class="text-center">부모님의 승인을 받으면 대출이 가능해요</div>
      <div class="flex justify-center">
        <img src="@/assets/fin/loan.png" alt="로딩중" />
      </div>
    </div>
  </div>
</template>
