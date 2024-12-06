<template>
  <div class="flex items-center justify-center mt-20 bg-gray-200">
    <div class="bg-white p-6 rounded-lg shadow-lg text-center w-full max-w-md mx-auto">
      <div v-if="!showResult && questions.length">
        <div class="text-2xl font-bold mb-8 mt-8 text-blue-500 font-bold">
          문제 {{ currentQuestionIndex + 1 }}
        </div>
        <div class="text-lg mb-4">{{ currentQuestion.content }}</div>

        <div>
          <p class="flex items-center"><IconAllalarm class="size-6 mr-2 text-lg" /> Time limit</p>
          <div class="w-full bg-gray-300 rounded-full h-2 mb-4">
            <div class="bg-teal-500 h-2 rounded-full" :style="{ width: timerBarWidth }"></div>
          </div>
        </div>

        <div id="options" class="mb-4">
          <button
            @click="selectOption(currentQuestion.select1)"
            class="block mx-auto mb-2 px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600"
          >
            {{ currentQuestion.select1 }}
          </button>
          <button
            @click="selectOption(currentQuestion.select2)"
            class="block mx-auto mb-2 px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600"
          >
            {{ currentQuestion.select2 }}
          </button>
          <button
            @click="selectOption(currentQuestion.select3)"
            class="block mx-auto mb-2 px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600"
          >
            {{ currentQuestion.select3 }}
          </button>
          <button
            @click="selectOption(currentQuestion.select4)"
            class="block mx-auto mb-2 px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600"
          >
            {{ currentQuestion.select4 }}
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
        <p class="mb-8">
          당신의 점수는 <span class="text-red-500 text-2xl font-bold">{{ score }}</span> 점입니다
        </p>

        <button
          id="review-btn"
          @click="reviewIncorrectAnswers"
          class="mr-2 px-4 py-2 text-white bg-green-500 rounded hover:bg-green-600"
        >
          공부하기
        </button>

        <button
          id="restart-btn"
          @click="restartQuiz"
          class="px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600"
        >
          다시시작
        </button>

        <p class="mb-8"></p>
        <!-- 모달창 -->
        <div
          v-if="showModal"
          class="fixed inset-0 bg-gray-900 bg-opacity-50 flex items-center justify-center z-50"
        >
          <div class="bg-white p-6 rounded-lg shadow-lg w-full max-w-lg">
            <h2 class="text-2xl font-bold mb-8 text-green-600">공부하기 🐇❣️</h2>
            <ul>
              <li v-for="(question, index) in incorrectAnswers" :key="index" class="mb-4">
                <p class="font-bold mb-2">Q{{ question.index + 1 }}. {{ question.content }}</p>
                <p class="text-orange-600">{{ question.answer }}</p>
              </li>
            </ul>
            <button
              class="mt-4 px-4 py-2 text-white bg-green-500 rounded hover:bg-orange-600"
              @click="showModal = false"
            >
              닫기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue"
import { useRouter } from "vue-router" // Import useRouter for navigation
import IconAllalarm from "@/components/icons/IconAllalarm.vue"
import axios from "axios"
import { useAuthStore } from "@/stores/auth"
import { useAssetStore } from "@/stores/asset"
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("경제용어 퀴즈")

interface Question {
  createdAt: string
  updatedAt: string
  id: number
  content: string
  select1: string
  select2: string
  select3: string
  select4: string
  answer: string
  index: number
}

const questions = ref<Question[]>([])
const currentQuestionIndex = ref(0)
const score = ref(0)
const showResult = ref(false)
const timer = ref<ReturnType<typeof setInterval> | null>(null)
const timeLimit = 10
const timeLeft = ref(timeLimit)
const timerBarWidth = computed(() => `${(timeLeft.value / timeLimit) * 100}%`)
const selectedAnswers = ref<string[]>([])
const currentQuestion = computed(() => questions.value[currentQuestionIndex.value])
const authStore = useAuthStore()
const token = authStore.accessToken

const resultMessage = computed(() => {
  if (score.value <= 1) return "우리 함께 공부해요 😊"
  if (score.value <= 2) return "금융에 대해 배우셨군요 🥰"
  return "와우 ! 아주 멋져요 😆❤️"
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

const assetStore = useAssetStore()

const selectOption = (selectedOption: string) => {
  selectedAnswers.value[currentQuestionIndex.value] = selectedOption // 선택한 답 저장
  if (selectedOption === currentQuestion.value.answer) {
    // 퀴즈 로그 생성 : 정답
    assetStore.sendQuizJudge(currentQuestion.value.id, 1)
    score.value++
  } else {
    // 퀴즈 로그 생성 : 오답
    assetStore.sendQuizJudge(currentQuestion.value.id, 0)
  }
  clearTimer()
  if (currentQuestionIndex.value < questions.value.length - 1) {
    nextQuestion()
  } else {
    showResult.value = true
  }
}

const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
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

const fetchQuestions = async () => {
  try {
    const response = await axios.get("/api/v1/quiz/", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    questions.value = response.data
  } catch (error) {
    console.error("Error fetching quiz data:", error)
  }
}

const router = useRouter() // Create a router instance

const showModal = ref(false) // 모달 열림 상태
const incorrectAnswers = ref<Question[]>([]) // 오답 저장

// 오답을 필터링하여 모달에 표시

const reviewIncorrectAnswers = () => {
  incorrectAnswers.value = questions.value
    .map((question, index) => ({
      ...question,
      index, // This adds the index to each question object
    }))
    .filter((question, index) => {
      return question.answer !== selectedAnswers.value[index] // Filter incorrect answers
    })
  showModal.value = true // Show the modal
}

onMounted(() => {
  fetchQuestions() // Fetch quiz questions on component mount
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
