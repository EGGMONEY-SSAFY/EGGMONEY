<script setup lang="ts">
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Colors } from "chart.js"
import { Doughnut } from "vue-chartjs"
import { useStockStore } from "@/stores/stock.js"
import { onMounted, ref } from "vue"

const stockStore = useStockStore()
const options = {
  responsive: true,
  maintainAspectRatio: false,
  hoverOffset: 20,
  borderDashOffset: 10,
  plugins: {
    title: {
      display: true,
      text: "qwd2qd",
    },
  },
}

const data = ref()
const data1 = ref()
onMounted(async () => {
  data1.value = await stockStore.getChartData()
  data1.value.prices.shift()
  data.value = {
    labels: [
      "코스피",
      "코스닥",
      "자동차",
      "반도체",
      "헬스케어",
      "은행",
      "에너지화학",
      "철강",
      "건설",
      "운송",
      "미디어",
      "IT",
      "유틸리티",
    ],
    datasets: [
      {
        data: data1.value.prices,
      },
    ],
  }
})

ChartJS.register(ArcElement, Tooltip, Legend, Colors)
</script>

<template>
  <div>
    <div class="p-4 m-4 bg-white rounded-lg shadow" v-if="data">
      <Doughnut :data="data" :options="options" />
    </div>
  </div>
</template>
