<template>
  <div>
    <h1>로그아웃</h1>
    <button @click="logout">Logout</button>
  </div>
</template>
<script setup lang="ts">
import { useAuthStore } from "@/stores/auth"

const authStore = useAuthStore()
const logout = () => {
  authStore.logout()
}
</script>
