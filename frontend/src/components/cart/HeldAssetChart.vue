<script setup lang="ts">
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Colors } from "chart.js"
import { Doughnut } from "vue-chartjs"
// import * as chartConfig from "./HeldAssetChartConfig.js"
import { ref, onMounted, watch } from "vue"
import { useUserStore } from "@/stores/user.js"
import { useAssetStore } from "@/stores/asset.js"

const userStore = useUserStore()
const assetStore = useAssetStore()

interface Analytics {
  입출금통장: Number | null
  예금: Number | null
  적금: Number | null
  대출: Number | null
  주식: Number | null
}

// labels와 datasets의 타입 정의
interface ChartData {
  labels: string[]
  datasets: {
    data: number[]
  }[]
}

// labels와 datasets으로 가공하는 함수
function formatData(response: Analytics): ChartData {
  const labels = Object.keys(response) // key값을 labels로 변환
  const data = Object.values(response).map((value) => (value !== null ? value : 0)) // null을 0으로 변환

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

    console.log(props.analytics)
  }
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow p-4">
    <Doughnut :data="data" :options="options" v-if="props.analytics && data" />
  </div>
</template>
