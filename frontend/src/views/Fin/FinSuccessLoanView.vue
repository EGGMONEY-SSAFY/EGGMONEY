<script setup lang="ts">
import router from "@/router"
import { useFinStore } from "@/stores/fin"
import { useUserStore } from "@/stores/user"
import { onMounted, ref, onUnmounted } from "vue"

const finStore = useFinStore()
const userStore = useUserStore()

const role = ref("")
const name = ref("AssetLoanListView")
if (userStore.user?.role === "부모") {
  name.value = "FinLoanJudgeView"
}
onMounted(() => {
  finStore.isYellowPage = true
  finStore.isTab = true
  setTimeout(() => {
    router.push({ name: name.value })
  }, 3000)
})

onUnmounted(() => {
  finStore.isYellowPage = false
  finStore.isTab = false
})
</script>

<template>
  <div v-if="userStore.user?.role === '자녀'" class="bg-yellow-50">
    <div class="h-[78vh] pt-20 flex flex-col gap-20">
      <div class="text-center text-3xl font-bold">대출 신청 성공!</div>
      <div class="text-center">부모님의 승인을 받으면 대출이 가능해요</div>
      <div class="flex justify-center">
        <img src="@/assets/fin/loan.png" alt="로딩중" />
      </div>
    </div>
  </div>
  <div v-else-if="userStore.user?.role === '부모'" class="bg-yellow-50">
    <div class="h-[78vh] pt-20 flex flex-col gap-20">
      <div class="text-center text-3xl font-bold">대출 심사 성공!</div>
      <div class="text-center">아이에게 대출 심사 결과가 전송되어요.</div>
      <div class="flex justify-center">
        <img src="@/assets/fin/loan.png" alt="로딩중" />
      </div>
    </div>
  </div>
  <div v-else>
    <div class="grid justify-center mt-20">
      <img class="w-full" src="@/assets/asset/notFound.png" alt="notFound" />
    </div>
    <h1 class="text-xl text-center pt-10 font-bold pb-5">대출 신청 내역이 없습니다.</h1>
  </div>
</template>
