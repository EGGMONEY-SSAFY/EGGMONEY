<script setup lang="ts">

import { useStockStore } from '@/stores/stock';
import { onMounted, ref } from 'vue';


const AuthStore = useStockStore()
const myStock = ref()
onMounted(async () => {
  myStock.value = await AuthStore.getMyStock()
  console.log(myStock.value);
  
})

</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col">
    <div class="m-4 mb-2">
      <span>현재 잔액 : </span>
      <!-- <span class="font-bold">{{ myStock.balance.toLocaleString() }} 알</span> -->
    </div>
    <div class="m-4 mt-2">
      <span>투자 가능 금액 : </span>
      <!-- <span class="font-bold">{{ myStock.investablePrice.toLocaleString() }} 알</span> -->
    </div>
  </div>
</template>
