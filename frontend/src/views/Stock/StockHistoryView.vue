<script setup lang="ts">
import NavBarTab from "@/components/navbar/NavBarTab.vue"
import { useVariableStore } from "@/stores/variable"
import { computed, ref } from "vue"
import { useRoute } from "vue-router"

const route = useRoute()
const path = computed(() => {
  return route.path
})
const store = useVariableStore()
store.setTitle("거래내역")

const inputValue = ref(0)

const result = computed(() => {
  return inputValue.value * 1000
})
</script>

<template>
  <div>
    <NavBarTab :path="path" />
    <h1>This is an history page</h1>
    <h1>숫자 입력 및 변환</h1>
    <input type="number" v-model.number="inputValue" placeholder="숫자를 입력하세요" />
    <p>결과: {{ result }}</p>
  </div>
</template>
