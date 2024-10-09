<script setup lang="ts">
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Colors } from "chart.js"
import { Doughnut } from "vue-chartjs"
import { useStockStore } from "@/stores/stock.js"
import { onMounted, ref } from "vue"

const nameMap: Record<number, string> = {
  1: "코스피",
  2: "코스닥",
  3: "자동차",
  4: "반도체",
  5: "헬스케어",
  6: "은행",
  7: "에너지화학",
  8: "철강",
  9: "건설",
  10: "운송",
  11: "미디어",
  12: "IT",
  13: "유틸리티",
}

const stockStore = useStockStore()

const data = ref()
const data1 = ref()
const options = ref()
onMounted(async () => {
  data1.value = await stockStore.getChartData()
  stockStore.setTotalStockValue(data1.value.totalPrice)
  data.value = {
    labels: data1.value.labels.map((num: number) => nameMap[num]),
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
    plugins: {
      title: {
        display: true,
        text: "주식 분석",
        font: {
          size: 20,
        },
        color: "#2F4F4F",
      },
    },
  }
})

ChartJS.register(ArcElement, Tooltip, Legend, Colors)
</script>

<template>
  <div>
    <div class="p-4 m-4 bg-white rounded-lg shadow" v-if="options">
      <Doughnut :data="data" :options="options" />
    </div>
  </div>
</template>
