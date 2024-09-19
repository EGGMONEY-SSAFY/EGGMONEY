<template>
  <div class="bg-white m-4 rounded-lg shadow p-4">
    <Doughnut :data="data" :options="options"/>
  </div>
</template>

<script lang="ts">
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Colors } from "chart.js"
import { Doughnut } from "vue-chartjs"
import * as chartConfig from "./chartConfig.js"


ChartJS.register(ArcElement, Tooltip, Legend, Colors)

export default {
  name: "App",
  components: {
    Doughnut,
  },
  options: {
    plugins: {
      legend: {
        labels: {
          color: 'rgb(255, 99, 132)'
        }
      }
    }
  },
  data() {
    return chartConfig
  },
  
}
</script>
