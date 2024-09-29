<template>
  <!-- 만들 때 -->
  <div class="flex flex-col items-center justify-center mx-auto">
    <div v-if="step != 2">
      <div class="text-center mt-6 text-lg font-semibold text-gray-700">
        {{ instructionMessage }}
      </div>

      <!-- 비밀번호 입력 표시 -->
      <div class="flex gap-3 mb-4 mt-8">
        <div
          v-for="(digit, index) in 6"
          :key="index"
          class="w-10 h-10 border border-gray-300 rounded bg-white flex items-center justify-center text-xl font-bold shadow-md"
        >
          {{ (step === 1 ? firstInput[index] : secondInput[index]) !== undefined ? "*" : "" }}
        </div>
      </div>

      <!-- 이미지 및 핀 패드 -->
      <div class="bg-white flex justify-center items-center w-full max-w-md h-auto relative">
        <img :src="pinPadImage" class="ml-8 w-3/4 h-auto" alt="Pin Pad" v-if="pinPadImage" />
        <button
          v-for="(number, index) in numbers"
          :key="index"
          class="absolute border border-gray-400 rounded-md shadow-md bg-white w-10 h-10"
          :class="{ 'bg-white': clickedButton === index || randomButton === index }"
          :style="buttonStyle(index)"
          @click="onButtonClick(index)"
        ></button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue"
import axios from "axios"
import { useAuthStore } from "@/stores/auth"
import JSEncrypt from "jsencrypt"
//@ts-ignore
import CryptoJS from "crypto-js"
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("간편 비밀번호")

const emit = defineEmits<{
  (event: "pinSuccess"): void
  (event: "pinFail"): void
}>()
const pinPadImage = ref<string | null>(null)
const numbers = ref<number[]>([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11])
const firstInput = ref<number[]>([])
const secondInput = ref<number[]>([])
const step = ref<number>(1)
const publicKey = ref<string>("")
const instructionMessage = ref<string>("비밀번호를 입력해주세요")

const clickedButton = ref<number | null>(null)
const randomButton = ref<number | null>(null)
const failCount = ref<number>(0) // 틀린 횟수를 저장

const authStore = useAuthStore()

const fetchPublicKey = async () => {
  try {
    const token = authStore.accessToken
    console.log(token)
    const response = await axios.get("http://localhost:8080/api/public-key", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })

    publicKey.value = response.data
  } catch (error) {
    console.error("공개 키:", error)
  }
}

const fetchPinPadImage = async () => {
  try {
    const token = authStore.accessToken
    console.log(token)
    const response = await axios.get("http://localhost:8080/api/pinpad", {
      //headers: {
      //Authorization: `Bearer ${token}`,
      //'Content-Type':'application/json',
      //}
    })
    const encryptedImage = response.data.encryptedImage
    console.log(encryptedImage)
    // const decrypt = new JSEncrypt();
    // decrypt.setPrivateKey(''
    //   // `${env.RSA.key}`
    // );
    // const aesKey = CryptoJS.enc.Utf8.parse(import.meta.env.VITE_AES_KEY);
    // const decrypted = CryptoJS.AES.decrypt(encryptedImage, CryptoJS.enc.Utf8.parse(aesKey), {
    //   mode: CryptoJS.mode.ECB,
    //   padding: CryptoJS.pad.Pkcs7 ,
    // });
    const decryptedBase64Image = encryptedImage
    // decrypted.toString(CryptoJS.enc.Base64);
    pinPadImage.value = `data:image/png;base64,${decryptedBase64Image}`
    console.log(pinPadImage.value)
  } catch (error) {
    console.error("이미지 불러오기 실패:", error)
  }
}

const onButtonClick = (index: number) => {
  clickedButton.value = index
  randomButton.value = getRandomIndex(index)
  console.log(clickedButton.value, randomButton.value)
  if (index === 9) {
    if (step.value === 1) firstInput.value.pop()
    else secondInput.value.pop()
    return
  }
  if (index != 9) {
    if (step.value === 1 && firstInput.value.length < 6 && index != 11) {
      firstInput.value.push(numbers.value[index])
    } else if (step.value === 1 && firstInput.value.length === 6 && index === 11) {
      verifyInput()
      step.value = 2
    }
  }

  setTimeout(() => {
    clickedButton.value = null
    randomButton.value = null
  }, 500)
}
const getRandomIndex = (excludeIndex: number): number => {
  let randomIndex
  do {
    randomIndex = Math.floor(Math.random() * numbers.value.length)
  } while (randomIndex === excludeIndex)
  return randomIndex
}
const verifyInput = () => {
  console.log(firstInput.value, secondInput.value)
  const pinString = firstInput.value.toString()
  encryptAndSendPin(pinString)
}

const encryptAndSendPin = (pin: string) => {
  const encrypt = new JSEncrypt()
  encrypt.setPublicKey(publicKey.value)
  //const encryptedPin = encrypt.encrypt(pin)
  const encryptedPin = pin
  //   if (!encryptedPin) {
  //     console.error("PIN 암호화 실패")
  //     instructionMessage.value = "암호화 오류가 발생했습니다."
  //     return
  //   }
  sendToBackend(encryptedPin)
}
const sendToBackend = async (encryptedPin: string) => {
  try {
    // const token = authStore.accessToken
    const token = "8CHnOwrEfKz3D_d9svUewrgwa0qyWihdAAAAAQoqJZAAAAGSMhZ9aJCBbdpZdq0Z"
    const response = await axios.post(
      "http://localhost:8080/api/pinpad/verify/check",
      {
        encryptedPin: encryptedPin,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    )
    console.log(response)
    if (response.data.status === "success") {
      // 상위 프롭스에 이벤트전달 성공 이벤트 전달
      failCount.value = 0 // 성공 시 틀린 횟수 초기화
      emit("pinSuccess")
    } else {
      instructionMessage.value = "비밀번호가 일치하지 않습니다. 다시 시도해주세요."
      resetInput()
      failCount.value += 1 // 실패 시 틀린 횟수 증가
      console.log(failCount.value)
      if (failCount.value >= 5) {
        emit("pinFail")
        // 5번 틀리면 상위 프롭스에 실패 이벤트 전달
        failCount.value = 0 // 틀린 횟수 초기화
      }
    }
  } catch (error) {
    console.error(error)
    instructionMessage.value = "비밀번호 검증 오류입니다. 다시 시도해주세요."
    resetInput()
    emit("pinFail")
  }
}

const resetInput = () => {
  firstInput.value = []
  secondInput.value = []
  step.value = 1
  instructionMessage.value = "비밀번호를 입력해주세요"
}
onUnmounted(() => {
  pinPadImage.value = null
})
onMounted(() => {
  fetchPublicKey()
  fetchPinPadImage()
})

// 버튼 스타일 설정
const buttonStyle = (index: number) => {
  const row = Math.floor(index / 3)
  const col = index % 3
  const buttonSize = 80 // 버튼 크기
  const padding = 20 // 버튼 사이 간격
  // test
  const backgroundColor =
    clickedButton.value === index || randomButton.value === index ? "white" : "transparent"

  return {
    left: `${col * (buttonSize + padding)}px`,
    top: `${row * (buttonSize + padding)}px`,
    width: `${buttonSize}px`,
    height: `${buttonSize}px`,
    backgroundColor,
    fontSize: "24px",
    textAlign: "center" as const, // textAlign 타입을 올바르게 지정
    cursor: "pointer",
    transition: "background-color 0.5s ease",
  }
}
</script>

<style scoped>
.pin-container {
  width: 400px; /* 이미지 및 키패드의 너비 고정 */
}

.keypad-button {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  background-color: transparent;
  transition: background-color 1s ease;
}
.input-display {
  display: flex;
  justify-content: center;
  gap: 10px;
  font-size: 24px;
  margin-bottom: 20px;
}
</style>
