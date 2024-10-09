<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { ref, onMounted } from 'vue';



const authStore = useAuthStore()
const token = authStore.accessToken

</script>

<template>
  <!-- <div v-if="avgLoanData" class="pt-20 grid grid-cols-1 grid-flow-row justify-center gap-3 px-3">
    <div>
        <div v-for="data in avgLoanData" :key="data.avg_loan_amount"
        class="grid grid-flow-row grid-cols-1 gap-4 p-3 m-4 bg-white rounded-lg shadow">
            <h1 v-if="">{{ data.loan_type }}</h1>
            <h1>{{ data.avg_loan_amount }}</h1>
            <h1>{{ data.avg_loan_rate }}</h1>
        </div>
    </div>
  </div> -->
  <h1>hi</h1>
</template>
