<script setup lang="ts">
import { onMounted } from 'vue';

    const props = defineProps({'child' : Object})
    onMounted ( () => {
            console.log(child.email)
        }
    )
        
</script>
<template>
    <div>
        {{ child.email }}
    </div>
</template>