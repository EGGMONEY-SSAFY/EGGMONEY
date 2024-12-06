<template>
  <div class="totalcontents">
    <canvas ref="screen" width="390" height="560"></canvas>
    <div class="content">
      <div
        v-if="gameOver"
        id="game-over"
        style="
          position: fixed;
          top: 40%;
          left: 50%;
          transform: translate(-50%, -50%);
          text-align: center;
        "
      >
        <h1 class="bold-text" style="color: gray">
          최종 점수
          <span style="color: rgb(220, 70, 18); font-size: x-large; font-weight: 800">{{
            scoreNow
          }}</span
          >&nbsp;점
        </h1>
        <!-- <p>최종 점수: {{ scoreNow }}점</p> -->
        <br />
        <button
          id="retry-button"
          @click="reloadPage"
          style="
            padding: 10px 20px;
            background-color: #808080;
            color: white;
            border: none;
            cursor: pointer;
          "
        >
          도전하기
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, onBeforeUnmount } from "vue"
import { useVariableStore } from "@/stores/variable"

// store 설정
const store = useVariableStore()
store.setTitle("돈 먹기 게임")

// 페이지 새로고침 메서드
const reloadPage = () => {
  location.reload()
}

// 참조 변수 설정
const screen = ref<HTMLCanvasElement | null>(null)
const ctx = ref<CanvasRenderingContext2D | null>(null)
const character = ref({
  img: new Image(),
  width: 50,
  height: 50,
  x: 0,
  y: 0,
  speed: 10,
  to_x: 0,
})
const background = new Image()
const donuts = ref<
  Array<{ img: HTMLImageElement; x: number; y: number; width: number; height: number }>
>([])
const docs = ref<
  Array<{
    img: HTMLImageElement
    x: number
    y: number
    width: number
    height: number
    speed: number
  }>
>([])
const totalTime = 40 // 총 시간 (초)
const remainingTime = ref(totalTime) // 남은 시간
const scoreNow = ref(0)
const gameInterval = ref<ReturnType<typeof setInterval> | null>(null)
const timerInterval = ref<ReturnType<typeof setInterval> | null>(null)
const gameOver = ref(false)

// 게임 초기화
const initGame = () => {
  if (screen.value) {
    ctx.value = screen.value.getContext("2d")

    // 점수 및 시간 초기화
    scoreNow.value = 0
    remainingTime.value = totalTime

    // 이미지 로드
    background.src =
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbrJ3UM%2FbtsJXWmP3eQ%2F6k4udHoyhN8lTckkjMBMVk%2Fimg.png"
    character.value.img.src =
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FsEd7N%2FbtsJYETB96G%2FVFSvL9vRyYjgZqbRZM4CY0%2Fimg.png"
    character.value.x = screen.value.width / 2 - character.value.width / 2
    character.value.y = screen.value.height - character.value.height

    // 도넛 초기화
    const donutImages = [
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fluvln%2FbtsJZVs03lb%2FHPzPEzRD18WlclKNPh0uZK%2Fimg.png",
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcowUel%2FbtsJXZKAOO5%2Fj6BnEOtDwn9Endp8THmeek%2Fimg.png",
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcRiMHd%2FbtsJXYycQK5%2FkeyLtlzPk1kfT6CtIHlf61%2Fimg.png",
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcPVAmD%2FbtsJ0cH3SdZ%2FMZ4EKeAvOC1xuJ1aQRRTgk%2Fimg.png",
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcRiMHd%2FbtsJXYycQK5%2FkeyLtlzPk1kfT6CtIHlf61%2Fimg.png",
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fluvln%2FbtsJZVs03lb%2FHPzPEzRD18WlclKNPh0uZK%2Fimg.png",
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcowUel%2FbtsJXZKAOO5%2Fj6BnEOtDwn9Endp8THmeek%2Fimg.png",
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcRiMHd%2FbtsJXYycQK5%2FkeyLtlzPk1kfT6CtIHlf61%2Fimg.png",
    ]

    for (let i = 0; i < 10; i++) {
      const tempDonut = new Image()
      tempDonut.src = donutImages[Math.floor(Math.random() * donutImages.length)]
      donuts.value.push({
        img: tempDonut,
        x: Math.floor(Math.random() * (screen.value.width - character.value.width)),
        y: 0,
        width: 30,
        height: 30,
      })
    }

    // 독 초기화
    for (let i = 0; i < 4; i++) {
      const tempDoc = new Image()
      tempDoc.src =
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FMa4rc%2FbtsJZLxojt4%2FB8BkCWiqvWLm47wl6H6zz1%2Fimg.png"
      docs.value.push({
        img: tempDoc,
        x: Math.floor(Math.random() * (screen.value.width - character.value.width)),
        y: 0,
        width: 30,
        height: 30,
        speed: Math.random() * 3 + 5,
      })
    }

    // 키 이벤트 리스너 등록
    window.addEventListener("keydown", handleKeyDown)
    window.addEventListener("keyup", handleKeyUp)
  }
}

// 타이머 업데이트 함수
const updateTimer = () => {
  if (remainingTime.value > 0) {
    remainingTime.value--
  } else {
    gameOver.value = true
    clearInterval(timerInterval.value!) // 타이머 종료
  }
}

// 키 이벤트 처리
const handleKeyDown = (event: KeyboardEvent) => {
  if (event.key === "ArrowLeft") {
    character.value.to_x = -character.value.speed
  } else if (event.key === "ArrowRight") {
    character.value.to_x = character.value.speed
  }
}

const handleKeyUp = (event: KeyboardEvent) => {
  if (event.key === "ArrowLeft" || event.key === "ArrowRight") {
    character.value.to_x = 0
  }
}

// 게임 상태 업데이트
const update = () => {
  if (gameOver.value) return

  character.value.x += character.value.to_x

  if (character.value.x < 0) character.value.x = 0
  if (character.value.x > screen.value!.width - character.value.width)
    character.value.x = screen.value!.width - character.value.width

  // 도넛 충돌 체크 및 위치 업데이트
  donuts.value.forEach((donut, index) => {
    donut.y += 3 * (index + 1)
    if (donut.y > screen.value!.height) {
      donut.y = 0
      donut.x = Math.floor(Math.random() * (screen.value!.width - character.value.width))
    }

    // 충돌 감지
    if (
      character.value.x < donut.x + donut.width &&
      character.value.x + character.value.width > donut.x &&
      character.value.y < donut.y + donut.height &&
      character.value.height + character.value.y > donut.y
    ) {
      scoreNow.value++
      donut.y = screen.value!.height + 1
    }
  })

  // 독 로직 추가
  docs.value.forEach((doc) => {
    doc.y += doc.speed
    if (doc.y > screen.value!.height) {
      doc.y = 0
      doc.x = Math.floor(Math.random() * (screen.value!.width - character.value.width))
    }

    // 독 충돌 감지
    if (
      character.value.x < doc.x + doc.width &&
      character.value.x + character.value.width > doc.x &&
      character.value.y < doc.y + doc.height &&
      character.value.height + character.value.y > doc.y
    ) {
      gameOver.value = true
    }
  })
}

// 렌더링 처리
const render = () => {
  if (!ctx.value || !screen.value) return

  ctx.value.clearRect(0, 0, screen.value!.width, screen.value!.height)

  ctx.value.drawImage(background, 0, 0, screen.value!.width, screen.value!.height)
  ctx.value.drawImage(
    character.value.img,
    character.value.x,
    character.value.y,
    character.value.width,
    character.value.height
  )

  // 도넛과 독 그리기
  if (ctx.value) {
    // ctx.value가 null이 아닐 때만 호출
    donuts.value.forEach((donut) => {
      if (ctx.value) {
        // ctx.value가 null이 아닐 경우에만 drawImage 호출
        ctx.value.drawImage(donut.img, donut.x, donut.y, donut.width, donut.height)
      }
    })

    docs.value.forEach((doc) => {
      if (ctx.value) {
        // ctx.value가 null이 아닐 경우에만 drawImage 호출
        ctx.value.drawImage(doc.img, doc.x, doc.y, doc.width, doc.height)
      }
    })
  }

  // 점수와 남은 시간 표시
  ctx.value.font = "20px Arial"
  ctx.value.fillStyle = "black"
  ctx.value.fillText("Score: " + scoreNow.value, 10, 30)
  ctx.value.fillText("Time: " + remainingTime.value, 300, 30)
}

// 게임 루프
const gameLoop = () => {
  if (!gameOver.value) {
    update()
    render()
  }
}

// 게임 시작
const startGame = () => {
  initGame()
  gameInterval.value = setInterval(gameLoop, 1000 / 60)
  timerInterval.value = setInterval(updateTimer, 1000) // 1초마다 타이머 업데이트
}

// 재도전 버튼 클릭 시 게임 재시작
const retryGame = () => {
  gameOver.value = false
  startGame()
}

// 컴포넌트가 마운트될 때 게임 시작
onMounted(() => {
  startGame()
})

// 컴포넌트가 언마운트될 때 리소스 정리
onBeforeUnmount(() => {
  if (gameInterval.value) clearInterval(gameInterval.value)
  if (timerInterval.value) clearInterval(timerInterval.value)
  window.removeEventListener("keydown", handleKeyDown)
  window.removeEventListener("keyup", handleKeyUp)
})
</script>

<style scoped>
.totalcontents {
  position: relative;
  overflow: hidden;
}
.bold-text {
  font-weight: bold;
}
</style>
