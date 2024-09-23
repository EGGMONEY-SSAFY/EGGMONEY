<script setup lang="ts">
import BoxProduct from "@/components/box/BoxProduct.vue"
import NavBarTab from "@/components/navbar/NavBarTab.vue"
</script>

<template>
  <div class="">
    <h1>This is an fin page</h1>
    <NavBarTab />
    <BoxProduct :deposit-date="0" , :deposit-rate="3.2" />
  </div>
</template>
