<script setup lang="ts">
import { Line } from "vue-chartjs"
import { useStockStore } from "@/stores/stock.js"
import { onMounted, ref } from "vue"
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  scales,
} from "chart.js"

const props = defineProps({
  stockId: Number,
})

const stockStore = useStockStore()
const options = {
  responsive: true,
  maintainAspectRatio: false,
  hoverOffset: 20,
  borderDashOffset: 10,
  scales: {
    x: {
      ticks: {
        maxTicksLimit: 8,
        autoSkip: true,
      },
    },
  },
  plugins: {
    title: {
      // display: true,
      // text: "qwd2qd",
    },
  },
}

const data = ref()
const data1 = ref()
onMounted(async () => {
  data1.value = await stockStore.getDetailStockData(props.stockId as number)
  data.value = {
    labels: data1.value.map((item: { date: string }) => item.date),
    datasets: [
      {
        label: "",
        pointRadius: 1,
        pointHoverRadius: 2,
        fill: false,
        backgroundColor: "#FF5A00",
        data: data1.value.map((item: { price: number }) => item.price),
      },
    ],
  }
})
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend)
</script>

<template>
  <div>
    <div class="p-4 m-4 bg-white rounded-lg shadow" v-if="data">
      <Line :data="data" :options="options" />
    </div>
  </div>
</template>
