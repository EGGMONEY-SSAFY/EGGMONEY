<script setup lang="ts">
import IconLogout from "../icons/IconLogout.vue"
import { useAuthStore } from "@/stores/auth"

const authStore = useAuthStore()
const logout = () => {
  authStore.logout()
}
</script>

<template>
  <div class="flex items-center justify-center m-4 mt-10 cursor-pointer">
    <p @click="logout" class="flex items-center mt-3 text-lg">
      <span>로그아웃 &nbsp;</span>
      <IconLogout />
    </p>
  </div>
</template>
