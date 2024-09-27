<script setup lang="ts">
import NavBarall from "@/components/navbar/NavBarAll.vue"
import NavBarAssets from "@/components/navbar/NavBarAsset.vue"
import NavBarFin from "@/components/navbar/NavBarFin.vue"
import NavBarStock from "@/components/navbar/NavBarStock.vue"
</script>

<template>
  <div
    class="w-full max-w-[393px] fixed bg-white bottom-0 h-16 grid grid-cols-4 border rounded-t-lg border-black border-solid divide-x-2"
  >
    <NavBarAssets />
    <NavBarFin />
    <NavBarStock />
    <NavBarall />
  </div>
</template>
