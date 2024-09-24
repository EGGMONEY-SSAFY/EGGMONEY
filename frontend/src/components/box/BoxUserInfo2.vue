<script setup lang="ts">
import { useRoute } from "vue-router"

const props = defineProps({
  price: Number,
})

const userData = { 현재잔액: 135000, 투자가능금액: 35000 }
const route = useRoute()
const name = route.params.stock
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow flex flex-col gap-2">
    <div class="mx-4 mt-4">
      <span>현재 잔액 : </span>
      <span class="font-bold">{{ userData["현재잔액"].toLocaleString() }} 알</span>
    </div>
    <div class="mx-4">
      <span>투자 가능 금액 : </span>
      <span class="font-bold">{{ userData["투자가능금액"].toLocaleString() }} 알</span>
    </div>
    <div class="mx-4 mb-4">
      <span>{{ name }} 가격 : </span>
      <span class="font-bold">{{ props.price }} 알</span>
    </div>
  </div>
</template>
