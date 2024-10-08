<script setup lang="ts">
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Colors, Title } from "chart.js"
import { Doughnut } from "vue-chartjs"
import { useStockStore } from "@/stores/stock.js"
import { onMounted, ref } from "vue"

const stockStore = useStockStore()

const data = ref()
const data1 = ref()
const options = ref()
onMounted(async () => {
  data1.value = await stockStore.getChartData()
  stockStore.setTotalStockValue(data1.value.totalPrice)
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
  options.value = {
    responsive: true,
    maintainAspectRatio: false,
    hoverOffset: 20,
    borderDashOffset: 10,
    plugins: {},
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
