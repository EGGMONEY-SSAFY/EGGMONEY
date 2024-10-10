<script setup lang="ts">
import { useVariableStore } from "@/stores/variable"
import { useRouter } from "vue-router"
import { computed } from "vue"
import IconLeftArrow from "../icons/IconLeftArrow.vue"
import IconNotify from "../icons/IconNotify.vue"

const store = useVariableStore()
const title = computed(() => store.title)
const router = useRouter()

const goBack = () => {
  router.back()
}
const goNoti=()=>{
  router.push("/Notification")
}
</script>

<template>
  <div class="w-full max-w-[393px] fixed bg-white top-0 h-12 grid grid-cols-12">
    <div class="cursor-pointer col-span-2 flex justify-center self-center" @click="goBack">
      <IconLeftArrow />
    </div>
    <div class="col-span-8 flex justify-center self-center text-main-color font-bold text-lg">
      {{ title }}
    </div>
    <div class="col-span-2 flex justify-center self-center cursor-pointer" @click="goNoti">
      <IconNotify />
    </div>
  </div>
</template>
