<template>
  <div class="flex items-center justify-center mt-20 bg-gray-200">
    <div class="bg-white p-6 rounded-lg shadow-lg text-center w-full max-w-md mx-auto">
      <div v-if="!showResult">
        <div class="text-2xl font-bold mb-8 mt-8 text-orange-600 font-bold">
          문제 {{ currentQuestionIndex + 1 }}
        </div>
        <div class="text-lg mb-4">{{ currentQuestion.question }}</div>
        <div class="w-full bg-gray-300 rounded-full h-2 mb-4">
          <div class="bg-teal-500 h-2 rounded-full" :style="{ width: timerBarWidth }"></div>
        </div>
        <div id="options" class="mb-4">
          <button
            v-for="(option, index) in currentQuestion.options"
            :key="index"
            @click="selectOption(index)"
            class="block mx-auto mb-2 px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600"
          >
            {{ option }}
          </button>
        </div>
        <button
          id="next-btn"
          @click="nextQuestion"
          class="mt-4 px-4 py-2 mb-8 text-white bg-orange-500 rounded hover:bg-orange-600"
        >
          PASS
        </button>
      </div>
      <div v-if="showResult">
        <h1 class="text-2xl font-bold mb-8 text-orange-600"><br />결과 발표</h1>
        <p class="mb-2">{{ resultMessage }}</p>
        <p class="mb-4">당신의 점수는 {{ score }}점입니다!</p>
        <button
          id="restart-btn"
          @click="restartQuiz"
          class="px-4 py-2 text-white bg-green-500 rounded hover:bg-green-600"
        >
          다시 시작하기
        </button>
        <p class="mb-8"></p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue"

interface Question {
  question: string
  options: string[]
  answer: number
}

const questions: Question[] = [
  {
    question: "금리가 오르면 어떤 일이 발생할 가능성이 높은가?",
    options: ["주택 가격 상승", "주식 시장 하락", "소비 증가", "국제 유가 상승"],
    answer: 1,
  },
  {
    question: "주식시장에서 '블루칩'이란 무엇을 의미하는가?",
    options: [
      "신생 기업의 주식",
      "고배당 주식",
      "안정성과 수익성이 높은 대기업의 주식",
      "단기 투자에 적합한 주식",
    ],
    answer: 2,
  },
  {
    question: "'분산 투자'의 주된 목적은 무엇인가?",
    options: [
      "고수익을 추구하기 위해",
      "손실 위험을 줄이기 위해",
      "세금 혜택을 받기 위해",
      "단기 매매 차익을 얻기 위해",
    ],
    answer: 1,
  },
  {
    question: "'레버리지'란 무엇을 의미하는가?",
    options: [
      "자산을 매각하는 것",
      "타인의 자금을 이용하여 투자하는 것",
      "투자의 위험을 줄이는 것",
      "주식의 배당금 증가",
    ],
    answer: 1,
  },
  {
    question: "금융 시장에서 '유동성'이란 무엇을 의미하는가?",
    options: [
      "투자자의 수익률",
      "자산을 현금으로 전환할 수 있는 용이성",
      "금리의 변동성",
      "시장의 안정성",
    ],
    answer: 1,
  },
  {
    question: "다음 중 '채권'의 특징이 아닌 것은?",
    options: [
      "정기적인 이자 지급",
      "만기 시 원금 상환",
      "주식처럼 배당금 지급",
      "상대적으로 낮은 위험",
    ],
    answer: 2,
  },
  {
    question: "'인플레이션'이란 무엇인가?",
    options: ["통화 가치의 증가", "소비자 물가의 지속적인 상승", "재정 적자의 증가", "금리의 하락"],
    answer: 1,
  },
  {
    question: "다음 중 '신용 등급'을 평가하는 기관이 아닌 것은?",
    options: ["    S&P    ", "    Moody's    ", "    Fitch    ", "    IMF    "],
    answer: 3,
  },
  {
    question: "주식 시장에서 '강세장'이란 어떤 시장을 의미하는가?",
    options: [
      "주가가 하락하는 시장",
      "주가가 일정하게 유지되는 시장",
      "주가가 상승하는 시장",
      "투자자들이 매도하는 시장",
    ],
    answer: 2,
  },
  {
    question: "'펀드'란 무엇인가?",
    options: [
      "개인이 직접 주식에 투자하는 방법",
      "여러 투자자의 자금을 모아 전문 투자자가 운용하는 금융 상품",
      "정부가 발행한 채권",
      "주식과 채권의 결합 상품",
    ],
    answer: 1,
  },
]

const currentQuestionIndex = ref(0)
const score = ref(0)
const showResult = ref(false)
const timer = ref<ReturnType<typeof setInterval> | null>(null)
const timeLimit = 10
const timeLeft = ref(timeLimit)
const timerBarWidth = computed(() => `${(timeLeft.value / timeLimit) * 100}%`)

const currentQuestion = computed(() => questions[currentQuestionIndex.value])

const resultMessage = computed(() => {
  if (score.value <= 3) return "우리 함께 공부해요 😊"
  if (score.value <= 6) return "금융에 대해 배우셨군요 🥰"
  return "와우 ! 아주 멋져요 😆"
})

const clearTimer = () => {
  if (timer.value !== null) {
    clearInterval(timer.value)
    timer.value = null
  }
}

const startTimer = () => {
  timeLeft.value = timeLimit
  clearTimer()
  timer.value = setInterval(() => {
    timeLeft.value--
    if (timeLeft.value <= 0) {
      clearTimer()
      nextQuestion()
    }
  }, 1000)
}

const selectOption = (index: number) => {
  if (index === currentQuestion.value.answer) score.value++
  clearTimer()
  nextQuestion()
}

const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.length - 1) {
    currentQuestionIndex.value++
    startTimer()
  } else {
    showResult.value = true
  }
}

const restartQuiz = () => {
  currentQuestionIndex.value = 0
  score.value = 0
  showResult.value = false
  startTimer()
}

onMounted(() => {
  startTimer()
})
</script>

<style scoped>
.timer {
  height: 5px;
  background-color: green;
  transition: width 1s;
}
</style>
