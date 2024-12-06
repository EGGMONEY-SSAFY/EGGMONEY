<template>
  <div class="flex items-center justify-center mt-20 bg-gray-100">
    <div class="bg-white p-6 rounded-lg shadow-md text-center">
      <h1 class="text-2xl font-bold mb-4">문제번호 {{ currentItemIndex + 1 }}</h1>
      <div v-if="currentItem" class="mb-4">
        <a :href="currentItem.coupangUrl" target="_blank">
          <img
            :src="currentItem.imageUrl"
            alt="item image"
            class="mb-4 mx-auto w-48 h-48 object-cover"
          />
        </a>
        <div id="item" class="text-lg font-medium mb-4">
          {{ currentItem?.name ? currentItem.name : "모든 물건을 맞췄습니다! 게임 종료!" }}
        </div>
      </div>
      <input
        v-if="currentItem"
        v-model.number="userGuess"
        type="number"
        placeholder="가격을 입력하세요"
        class="mb-4 p-2 border rounded w-64"
        step="1000"
      />
      <button
        v-if="currentItem"
        @click="submitGuess"
        class="mb-4 bg-green-600 ml-2 text-white py-2 px-4 rounded hover:bg-green-700"
      >
        제출
      </button>
      <div id="result" class="mb-4 text-lg" v-html="resultMessage"></div>

      <a
        v-if="currentItem && nextVisible"
        :href="currentItem.coupangUrl"
        target="_blank"
        class="bg-orange-600 text-white py-2 px-4 rounded hover:bg-orange-700"
      >
        직접 확인하기
      </a>

      <button
        v-if="currentItem && nextVisible"
        @click="nextItem"
        class="bg-blue-600 ml-2 text-white py-2 px-4 rounded hover:bg-blue-700"
      >
        다음 항목
      </button>

      <!-- Modal for displaying the result -->
      <transition name="fade">
        <div
          v-if="showModal"
          class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50"
        >
          <div class="bg-white p-6 rounded-lg shadow-md text-center">
            <h2 class="text-lg font-bold mt-4 mx-4">결과 공개 🐹💛</h2>
            <div v-for="(percentage, index) in errorPercentages" :key="index" class="mt-2">
              <p>
                {{ index + 1 }}번 오차율
                <span :class="getErrorClass(percentage)">{{ percentage.toFixed(2) }}%</span>
              </p>
            </div>
            <button
              @click="closeModal"
              class="mt-4 bg-red-600 text-white py-2 px-4 rounded hover:bg-red-700"
            >
              닫기
            </button>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { useRouter } from "vue-router"
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("물가 맞추기 게임")

interface Item {
  name: string
  price: number
  imageUrl: string
  coupangUrl: string
}

const items: Item[] = [
  {
    name: "지파워 25W 초고속충전기 PD 3.0 PPS 아이폰 아이패드 C TO 8 케이블 포함",
    price: 16500,
    imageUrl:
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FtuzBt%2FbtsJY9FUlWb%2F9bgKLbAGodJcbxOu7hTBLK%2Fimg.jpg",
    coupangUrl:
      "https://www.coupang.com/vp/products/7761753188?itemId=20932583329&vendorItemId=87356663129&src=1042503&spec=10304025&addtag=400&ctag=7761753188&lptag=10304025I20932583329V87356663129&itime=20241009142947&pageType=PRODUCT&pageValue=7761753188&wPcid=28190612492210251486059&wRef=&wTime=20241009142947&redirect=landing&gclid=Cj0KCQjwsJO4BhDoARIsADDv4vCzd15GbRB_ayWEAZHL-DMGXftOUK0Gwvzs3ZolkzcIqSAbUhNiBYEaAsPQEALw_wcB&mcid=6cc2fbe09d8947d6812f4964a7724dbd&campaignid=21519412236&adgroupid=",
  },
  {
    name: "skater 바나나 케이스, 1개",
    price: 5660,
    imageUrl:
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbTG8yV%2FbtsJZnRi14J%2Fkw8VnwyBajuwQKQnPnaFtk%2Fimg.jpg",
    coupangUrl:
      "https://www.coupang.com/vp/products/3768357?itemId=18580909&vendorItemId=3027261591&q=skater+%EB%B0%94%EB%82%98%EB%82%98+%EC%BC%80%EC%9D%B4%EC%8A%A4%2C+1%EA%B0%9C&itemsCount=31&searchId=e56bb7ae1c4f4369b48f04ccd7935bdf&rank=1&isAddedCart=",
  },
  {
    name: "재원전자 버디쿡 에어프라이어 3.9L BD8002S",
    price: 43460,
    imageUrl:
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fl2OU3%2FbtsJ0fdXUvb%2F1642e6WEcdKZq8ICAlruK1%2Fimg.jpg",
    coupangUrl:
      "https://www.coupang.com/vp/products/1387602348?itemId=2423157476&vendorItemId=70417265843&pickType=COU_PICK&q=%EC%9E%AC%EC%9B%90%EC%A0%84%EC%9E%90+%EB%B2%84%EB%94%94%EC%BF%A1+%EC%97%90%EC%96%B4%ED%94%84%EB%9D%BC%EC%9D%B4%EC%96%B4+%EB%8C%80%EC%9A%A9%EB%9F%89+3.9L&itemsCount=36&searchId=9bf5620dde2b4d9181c56bf8052df614&rank=0&isAddedCart=",
  },
  {
    name: "프레시 채소믹스, 500g, 1개",
    price: 5790,
    imageUrl:
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FU1cBA%2FbtsJ0QLGKmW%2FulNngKkGf896Q6sYHHI71k%2Fimg.jpg",
    coupangUrl:
      "https://www.coupang.com/vp/products/4917479866?itemId=6437111906&vendorItemId=73731638746&pickType=COU_PICK&q=%ED%94%84%EB%A0%88%EC%8B%9C+%EC%B1%84%EC%86%8C%EB%AF%B9%EC%8A%A4%2C+500g%2C+1%EA%B0%9C&itemsCount=36&searchId=ae7d19f440134cdb8f80d2bd757375b6&rank=1&isAddedCart=",
  },
  {
    name: "농심 신라면, 5개",
    price: 3900,
    imageUrl:
      "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkAXvE%2FbtsJYBCUW2S%2FGmaPCujPaSHsOyEl00VSy1%2Fimg.jpg",
    coupangUrl:
      "https://www.coupang.com/vp/products/7958974?itemId=93553&vendorItemId=3000207618&q=%EB%86%8D%EC%8B%AC+%EC%8B%A0%EB%9D%BC%EB%A9%B4%2C+5%EA%B0%9C&itemsCount=36&searchId=92b2b14a3fe7495d8c283b3b34f2bbd3&rank=1&isAddedCart=",
  },
]

const router = useRouter()
const currentItemIndex = ref(0)
const currentItem = ref<Item | null>(items[currentItemIndex.value])
const userGuess = ref<number | null>(null)
const resultMessage = ref("")
const nextVisible = ref(false)
const showModal = ref(false)
const errorPercentages = ref<number[]>([]) // To store error percentages for all questions

function submitGuess() {
  if (currentItem.value && userGuess.value !== null) {
    const priceDifference = Math.abs(userGuess.value - currentItem.value.price)
    const errorPercentage = (priceDifference / currentItem.value.price) * 100 // Calculate the error percentage

    // Store the error percentage
    errorPercentages.value.push(errorPercentage)

    // Determine the class for the error percentage
    const errorClass = errorPercentage <= 50 ? "text-green-600" : "text-red-600"

    // Set the result message
    if (priceDifference === 0) {
      resultMessage.value = `당신은 천재입니까? 😲<br>정답은 <span class="${errorClass}">${currentItem.value.price}</span>원 입니다. <br>오차율 <span class="${errorClass}">${errorPercentage.toFixed(2)}%</span>`
    } else if (errorPercentage <= 10) {
      resultMessage.value = `아주 멋져요 🥰<br>정답은 <span class="${errorClass}">${currentItem.value.price}</span>원 입니다. <br>오차율 <span class="${errorClass}">${errorPercentage.toFixed(2)}%</span>`
    } else if (errorPercentage <= 25) {
      resultMessage.value = `당신은 물가를 아시는 분 😊❣️<br>정답은 <span class="${errorClass}">${currentItem.value.price}</span>원 입니다. <br>오차율 <span class="${errorClass}">${errorPercentage.toFixed(2)}%</span>`
    } else if (errorPercentage <= 45) {
      resultMessage.value = `조금만 더 힘내요 🤣<br>정답은 <span class="${errorClass}">${currentItem.value.price}</span>원 입니다. <br>오차율 <span class="${errorClass}">${errorPercentage.toFixed(2)}%</span>`
    } else if (errorPercentage <= 60) {
      resultMessage.value = `같이 쇼핑을 해볼까요? 😅 <br>정답은 <span class="${errorClass}">${currentItem.value.price}</span>원 입니다. <br>오차율 <span class="${errorClass}">${errorPercentage.toFixed(2)}%</span>`
    } else if (errorPercentage <= 70) {
      resultMessage.value = `공부가 필요해요 😨 <br>정답은 <span class="${errorClass}">${currentItem.value.price}</span>원 입니다. <br>오차율 <span class="${errorClass}">${errorPercentage.toFixed(2)}%</span>`
    } else {
      resultMessage.value = `우리 함께 공부해요 😭 <br>정답은 <span class="${errorClass}">${currentItem.value.price}</span>원 입니다. <br>오차율 <span class="${errorClass}">${errorPercentage.toFixed(2)}%</span>`
    }

    nextVisible.value = true
  }
}

function nextItem() {
  if (currentItemIndex.value === items.length - 1) {
    // 마지막 항목일 때 모달 창 보여주기
    showModal.value = true
  } else {
    // 다음 항목으로 이동
    currentItemIndex.value++
    currentItem.value = items[currentItemIndex.value]
    resultMessage.value = ""
    userGuess.value = null
    nextVisible.value = false
  }
}

function closeModal() {
  showModal.value = false
  nextItem() // Move to the next item or reset the game
  router.push("/all")
}

function getErrorClass(percentage: number): string {
  return percentage <= 50 ? "text-green-600" : "text-red-600"
}
</script>

<style scoped>
/* Add transition effects for the modal */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0;
}
</style>
