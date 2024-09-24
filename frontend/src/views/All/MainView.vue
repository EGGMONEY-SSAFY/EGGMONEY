<template>
  <div class="flex flex-col items-center justify-center text-center bg-[#fef6e4] h-screen m-0">
    <img src="../assets/all/EGGMONEY로고.png" alt="Chicken logo" class="w-[1000px]" />
    <h1 class="text-2xl font-bold my-4">나의 첫 경제생활 도우미</h1>
    <p class="my-4">
      복잡한 송금, 가계부 관리, 어려운 주식 공부까지<br>
      <strong class="text-[#ff7f50]">에그머니</strong>에서 쉽고 재미있게 시작해 보세요!
    </p>
    <button @click="start" class="my-20 bg-[#ff7f50] text-white border-none py-2  cursor-pointer hover:bg-[#ff6347]" style="border-radius: 8rem; padding-left: 8rem; padding-right: 8rem;">
      시작하기
    </button>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const start = () => {
  router.push({ name: 'LoginView' });
};
</script>
