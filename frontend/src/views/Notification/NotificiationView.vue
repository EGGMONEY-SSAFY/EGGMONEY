<template>
    <div>
        <NavBarNotificiation/>
    </div>
</template>
<script setup lang="ts">
import NavBarNotificiation from "@/components/navbar/NavBarNotificiation.vue";
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("알림")
</script>
