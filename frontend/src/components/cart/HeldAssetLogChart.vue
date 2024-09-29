<script setup lang="ts">
import { Line } from "vue-chartjs"
import { onUnmounted, ref, watch } from "vue"
import "chartjs-adapter-date-fns" // 날짜 포맷을 위해 필요한 어댑터
import { useAssetStore } from "@/stores/asset"
import type { TradeData } from "@/stores/asset"
import {
  Chart as ChartJS,
  LineElement,
  PointElement,
  LinearScale,
  TimeScale,
  Tooltip,
  Legend,
  Title,
} from "chart.js"

const assetStore = useAssetStore()

// Chart.js 컴포넌트 등록
ChartJS.register(LineElement, PointElement, LinearScale, TimeScale, Tooltip, Legend, Title)

// Line 차트 데이터 가공 함수
function formatLineData(tradeHistory: TradeData[]): {
  labels: Date[]
  datasets: {
    label: string
    data: number[]
    borderColor: string
    backgroundColor: string
    fill: boolean
  }[]
} {
  // labels에 날짜(Date 객체)를, data에 currentBalance를 저장
  const labels = tradeHistory.map((trade) => new Date(trade.createdAt))
  const data = tradeHistory.map((trade) => trade.currentBalance)

  return {
    labels: labels, // labels는 Date 객체 배열
    datasets: [
      {
        label: "잔액",
        data: data, // 잔액 데이터
        borderColor: "rgba(75, 192, 192, 1)", // 선 색상
        backgroundColor: "rgba(75, 192, 192, 0.2)", // 배경색
        fill: false, // 배경 채우기 비활성화
      },
    ],
  }
}

// Line 차트 옵션 설정 (가로축 단위를 일 단위로 설정)
const lineOptions = {
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    x: {
      type: "time" as const, // 가로축을 시간(time)으로 설정
      time: {
        unit: "day" as const, // 월 단위로 축 설정
        tooltipFormat: "MMM yyyy", // 툴팁에서 월과 연도 포맷으로 표시
        displayFormats: {
          month: "MMM yyyy", // 축에 표시되는 포맷
        },
      },
      ticks: {
        autoSkip: true, // 너무 많은 라벨이 있을 경우 자동으로 건너뜀
        maxTicksLimit: 3, // 가로축에 표시할 최대 틱 수 (필요시 조정 가능)
        callback: function (value: string | number, index: number, values: any[]) {
          const totalTicks = values.length
          if (totalTicks > 10) {
            // 만약 틱이 많다면 월 단위로 표시
            return new Date(value as string).toLocaleDateString("en-US", {
              month: "short",
              year: "numeric",
            })
          } else {
            // 틱이 적다면 일 단위로 표시
            return new Date(value as string).toLocaleDateString("en-US", {
              month: "short",
              day: "numeric",
            })
          }
        },
      },
      title: {
        display: true,
        text: "날짜 (일 단위)", // 가로축 제목
      },
    },
    y: {
      title: {
        display: true,
        text: "보유액", // 세로축 제목
      },
      ticks: {
        maxTicksLimit: 10, // 세로 축의 최대 표시 개수 제한 (예: 5개 이하)
      },
    },
  },
  plugins: {
    tooltip: {
      callbacks: {
        // 툴팁 제목을 tradeTarget으로 표시
        title: (tooltipItems: any) => {
          const tooltipItem = tooltipItems[0] // 첫 번째 데이터 포인트만 사용
          const index = tooltipItem.dataIndex // 해당 데이터 포인트의 인덱스
          const tradeTarget = props.accountHistory[index].tradeTarget // tradeTarget 값 가져오기
          return `${tradeTarget}` // 툴팁 제목으로 반환
        },
        label: (tooltipItem: any) => {
          const date = new Date(tooltipItem.parsed.x) // X축의 값을 날짜로 변환
          const formattedDate = date.toISOString().split("T")[0] // YYYY-MM-DD 형식으로 변환
          const value = tooltipItem.parsed.y.toLocaleString() // Y축 값 (잔액 등)
          return [`${formattedDate}`, `잔액: ${value}`]
        },
      },
      bodyFont: {
        size: 12, // 툴팁 본문 글자 크기 설정
      },
      titleFont: {
        size: 14, // 툴팁 제목 글자 크기 설정
      },
    },
    title: {
      display: true,
      text: "자산 추이",
      font: {
        size: 20,
      },
      color: "#2F4F4F",
    },
  },
}

// props로 tradeHistory 받기
const props = defineProps<{ accountHistory: TradeData[] }>()

// Line 차트 데이터
const lineData = ref<{
  labels: Date[]
  datasets: {
    label: string
    data: number[]
    borderColor: string
    backgroundColor: string
    fill: boolean
  }[]
} | null>(null)

// tradeHistory가 변경될 때 차트 데이터 업데이트
watch(
  () => props.accountHistory,
  (newTradeHistory) => {
    if (newTradeHistory) {
      lineData.value = formatLineData(newTradeHistory)
    }
  },
  { immediate: true, deep: true }
)

onUnmounted(() => {
  assetStore.logs = []
})
</script>

<template>
  <div class="bg-white m-4 rounded-lg shadow p-4">
    <!-- Line 차트 렌더링 -->
    <Line v-if="lineData" :data="lineData" :options="lineOptions" />
  </div>
</template>
