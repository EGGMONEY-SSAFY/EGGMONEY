<script setup lang="ts">
import IconLogout from "../icons/IconLogout.vue"
import { useAuthStore } from "@/stores/auth"

const authStore = useAuthStore()
const logout = () => {
  authStore.logout()
}
</script>

<template>
  <div class="m-4 flex justify-center items-center mt-10 cursor-pointer">
    <p @click="logout" class="text-lg flex items-center mt-3">
      <span>로그아웃 &nbsp;</span>
      <IconLogout />
    </p>
  </div>
</template>
