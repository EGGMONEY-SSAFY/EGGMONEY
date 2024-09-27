<script setup lang="ts">
import { useUserStore } from "@/stores/user"

interface User {
  userId: number
  name: string
  realAccount: string
  bank: string
}
const userStore = useUserStore()

const props = defineProps<{ user: User; balance: Number | null }>()
</script>

<template>
  <div class="bg-white m-4 p-2 rounded-lg shadow">
    <div class="">
      <div class="m-3 flex justify-between text-sm">
        <h1 class="font-semibold text-white bg-main-color rounded-xl text-center p-2 px-3">
          입출금통장
        </h1>
        <div class="flex my-auto text-sm gap-2 font-semibold">
          <h1>{{ user.bank }}</h1>
          <h1>{{ props.user.realAccount }}</h1>
        </div>
      </div>
      <div class="m-4 font-bold">
        <h1 class="text-center mt-5" style="font-size: 35px">
          {{ props.balance?.toLocaleString() }}<span class="ms-1 font-bold">원</span>
        </h1>
      </div>
    </div>
    <div
      class="flex text-justify justify-around mt-8 px-5 mb-5"
      v-if="userStore.user?.role === '부모'"
    >
      <button class="bg-red-500 px-5 py-2 rounded-xl text-white font-semibold">출금요청</button>
    </div>
  </div>
</template>
