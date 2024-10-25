<template>
  <div class="flex justify-center items-center mt-36 bg-gray-200">
    <div class="text-center relative">
      <div class="absolute top-[-2%] left-1/2 transform -translate-x-1/2 z-10">
        <div
          class="w-0 h-0 border-l-[20px] border-l-transparent border-r-[20px] border-r-transparent border-t-[30px] border-t-orange-500"
        ></div>
      </div>
      <div
        ref="wheel"
        class="w-[300px] h-[300px] rounded-full relative overflow-hidden transition-transform duration-80000 ease-[cubic-bezier(0.33,1,0.68,1)] shadow-lg"
        style="border: 8px solid #8b5c3a"
      >
        <div
          class="absolute w-full h-full clip-path-polygon"
          v-for="(segment, index) in segments"
          :key="index"
          :style="{ transform: `rotate(${index * 60}deg)`, background: colors[index] }"
        >
          <span
            class="absolute w-full h-full text-center leading-[80px] text-3xl font-bold text-white shadow-md"
          >
            {{ segment }}
          </span>
        </div>
        <img
          src="../../../public/img/icons/egg.png"
          alt="Egg"
          class="absolute inset-0 m-auto w-20 h-20"
        />
      </div>
      <br />
      <button
        v-if="buttonVisible"
        @click="spinWheel"
        class="mt-4 px-6 py-3 bg-orange-500 text-white rounded-lg shadow hover:bg-orange-600 transition duration-200"
      >
        돌리기
      </button>
      <div
        v-if="spinningMessage"
        class="mt-4 text-lg font-bold text-orange-600"
        v-html="formattedMessage"
        style="margin-top: 20px"
      ></div>
    </div>

    <!-- 모달 창 -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center"
    >
      <div
        class="bg-white p-6 rounded-lg shadow-lg text-center fade-in"
        :class="{ 'fade-in-active': showModal }"
      >
        <h2 class="text-xl font-bold mb-4 text-green-500">오늘의 행운🍀</h2>
        <div v-html="result" class="mb-4 text-lg text-gray-700"></div>
        <button
          @click="closeModal"
          class="px-4 py-2 bg-blue-500 text-white rounded-lg shadow hover:bg-blue-600 transition duration-200"
        >
          닫기
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from "vue"
import { useVariableStore } from "@/stores/variable"
import { useRouter } from "vue-router"

const store = useVariableStore()
store.setTitle("행운의 금융 룰렛")

const router = useRouter()

const segments = ["", "", "", "", "", ""]
const colors = ["#FF6347", "#FFD700", "#8FBC8F", "#FF69B4", "#87CEEB", "#BA55D3"]
const result = ref("")
const buttonVisible = ref(true)
const showModal = ref(false)
const spinningMessage = ref("") // 추가된 메시지 변수
const wheel = ref<HTMLElement | null>(null)

const formattedMessage = computed(() => {
  return spinningMessage.value.replace(/\n/g, "<br>") // 줄 바꿈을 <br>로 변환
})

function spinWheel() {
  if (!wheel.value) return

  const randomDegree = Math.floor(Math.random() * 360 + 360 * 5)
  wheel.value.style.transform = `rotate(${randomDegree}deg)`

  buttonVisible.value = false
  spinningMessage.value = " 에그머니와 함께 \n 금융에 대해 배워봐요"

  setTimeout(() => {
    spinningMessage.value = "" // 메시지 초기화
    displayResult(randomDegree)
  }, 700)
}

function displayResult(randomDegree: number) {
  const finalDegree = randomDegree % 360
  let segment = Math.floor(finalDegree / 60)

  const results = [
    "불필요한 지출을 조심하고 <br>예산을 점검해보세요 💸❣️<br>시드머니를 만들 시기입니다",
    "작은 투자에 주목하면 <br> 큰 수익을 가져올 수 있습니다 🐏❣️ <br>예적금을 고려해보세요",
    "재정 계획을 세우는 데 집중하세요 <br> 당신의 미래가 밝아집니다 🌞❣️ <br>금융일기를 작성해보세요 ",
    "신뢰할 수 있는 사람에게 <br> 조언을 구하세요 <br>부모님을 찾아갑시다 👪❣️",
    "새로운 기회를 찾는 데 <br> 적합한 시기입니다 👀❣️ <br> 대출과 레버리지를 고려해보세요",
    "긍정적인 마음가짐이 <br> 재정운을 더욱 끌어올립니다 <br> 투자일기를 적어보세요 📜❣️",
  ]

  result.value = results[segment]
  showModal.value = true
}

function closeModal() {
  showModal.value = false
  buttonVisible.value = true
  // 'all' 페이지로 이동
  router.push("/all")
}
</script>

<style scoped>
/* 커스텀 CSS가 필요한 경우 여기에 추가 */
.clip-path-polygon {
  clip-path: polygon(50% 50%, 100% 0, 100% 100%);
}
.fade-in {
  opacity: 0;
  transform: translateY(-20px);
  transition:
    opacity 0.5s ease,
    transform 0.5s ease;
}
.fade-in-active {
  opacity: 1;
  transform: translateY(0);
}
</style>
