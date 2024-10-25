<script setup lang="ts">
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Colors } from "chart.js"
import { Doughnut } from "vue-chartjs"
// import * as chartConfig from "./HeldAssetChartConfig.js"
import { ref, onMounted, watch } from "vue"
import type { ChartData } from "@/stores/asset.js"
import type { Analytics } from "@/stores/asset.js"

// labels와 datasets으로 가공하는 함수
function formatData(response: Analytics): ChartData {
  const labels = Object.keys(response).filter((key) => key !== "대출")
  const data = Object.entries(response)
    .filter(([key]) => key !== "대출")
    .map(([key, value]) => (value !== null ? value : 0))

  return {
    labels: labels,
    datasets: [
      {
        data: data,
      },
    ],
  }
}

// Chart.js의 기본 옵션 설정
const options = {
  responsive: true,
  maintainAspectRatio: false,
  hoverOffset: 20,
  borderDashOffset: 10,
  plugins: {
    title: {
      display: true,
      text: "자산 분석",
      font: {
        size: 20,
      },
      color: "#2F4F4F",
    },
  },
}

// 데이터 변환
const data = ref<ChartData | null>(null)

const props = defineProps<{ analytics: Analytics | null }>()

// 원형 그래프
ChartJS.register(ArcElement, Tooltip, Legend, Colors)

// 꺾은선 그래프

// analytics 값의 변화를 감지하여 데이터 업데이트
watch(
  () => props.analytics,
  (newAnalytics) => {
    if (newAnalytics) {
      data.value = formatData(newAnalytics)
    }
  },
  { immediate: true, deep: true } // immediate: true로 첫 렌더링 시 반응 보장
)

onMounted(async () => {
  if (props.analytics?.입출금통장) {
    data.value = formatData(props.analytics)
  }
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow p-4">
    <Doughnut :data="data" :options="options" v-if="props.analytics && data" />
  </div>
</template>
