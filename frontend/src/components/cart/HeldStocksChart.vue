<script setup lang="ts">
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Colors } from "chart.js"
import { Doughnut } from "vue-chartjs"
import * as chartConfig from "./HeldStocksChartConfig.js"

const { data, options } = chartConfig
ChartJS.register(ArcElement, Tooltip, Legend, Colors)
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow p-4">
    <Doughnut :data="data" :options="options" />
  </div>
</template>
