<template>
  <div class="flex flex-col items-center justify-center mx-auto">
    <div v-if="step != 3">
      <div class="text-center mt-16 text-sm font-semibold text-gray-700">
        {{ instructionMessage }}
      </div>

      <!-- 비밀번호 입력 표시 -->
      <div class="flex gap-3 m-8 justify-center items-center">
        <div
          v-for="(digit, index) in 6"
          :key="index"
          class="w-10 h-10 border border-gray-300 rounded bg-white flex items-center justify-center text-xl font-bold shadow-md"
        >
          {{ (step === 1 ? firstInput[index] : secondInput[index]) !== undefined ? "*" : "" }}
        </div>
      </div>

      <!-- 이미지 및 핀 패드 -->
      <div class="bg-white">
        <div
          v-if="pinPadImage"
          class="bg-no-repeat h-[393px] w-[393px]"
          :style="{ backgroundImage: `url(${pinPadImage})` }"
        >
          <div class="flex justify-center flex-wrap mx-10 h-[393px]">
            <button
              v-for="index in numbers"
              :key="index"
              class="border rounded-md shadow-md size-16 m-4"
              :class="{ 'bg-white': clickedButton === index || randomButton === index }"
              @click="onButtonClick(index)"
            ></button>
          </div>
        </div>
      </div>
    </div>
    <div v-if="step === 3">
      <CreateSimplePwdSuccess />
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
import CreateSimplePwdSuccess from "./complete/CreateSimplePwdSuccess.vue"
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("간편 비밀번호")

const pinPadImage = ref<string | null>(null)
const numbers = ref<number[]>([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11])
const firstInput = ref<number[]>([])
const secondInput = ref<number[]>([])
const step = ref<number>(1)
const publicKey = ref<string>("")
const instructionMessage = ref<string>("비밀번호를 입력해주세요")

const clickedButton = ref<number | null>(null)
const randomButton = ref<number | null>(null)

const authStore = useAuthStore()

const fetchPublicKey = async () => {
  try {
    const token = authStore.accessToken
    console.log(token)
    const response = await axios.get("/api/public-key", {
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
    const response = await axios.get("/api/pinpad", {
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
  // if (numbers.value[index] === 10) {
  //   if (step.value === 1) {
  //     firstInput.value.pop()
  //   } else {
  //     secondInput.value.pop()
  //   }
  //   return
  // }
  if (index === 9) {
    if (step.value === 1) firstInput.value.pop()
    else secondInput.value.pop()
    return
  }
  if (index != 9) {
    if (step.value === 1 && firstInput.value.length < 6 && index != 11) {
      firstInput.value.push(numbers.value[index])
    } else if (step.value === 1 && firstInput.value.length === 6 && index === 11) {
      instructionMessage.value = "비밀번호를 한 번 더 입력해주세요"
      step.value = 2
    } else if (step.value === 2 && secondInput.value.length < 6 && index != 11) {
      secondInput.value.push(numbers.value[index])
    } else if (secondInput.value.length === 6 && index === 11) {
      verifyInput()
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
  if (firstInput.value.join("") === secondInput.value.join("")) {
    const pinString = firstInput.value.toString()
    encryptAndSendPin(pinString)
    step.value = 3
  } else {
    try {
      instructionMessage.value = "비밀번호가 일치하지 않습니다. 다시 시도해주세요"
      resetInput()
      step.value = 1
    } catch (error) {}
    console.log(instructionMessage.value)
  }
}

const encryptAndSendPin = (pin: string) => {
  // const encrypt = new JSEncrypt()
  // encrypt.setPublicKey(publicKey.value)
  // const encryptedPin = encrypt.encrypt(pin)
  // if (!encryptedPin) {
  //   console.error("PIN 암호화 실패")
  //   instructionMessage.value = "암호화 오류가 발생했습니다."
  //   return
  // }
  // sendToBackend(encryptedPin)
  sendToBackend(pin)
}
const sendToBackend = async (encryptedPin: string) => {
  const token = authStore.accessToken

  try {
    await axios.post(
      "/api/pinpad/verify",
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
    instructionMessage.value = "비밀번호 설정이 완료되었습니다!"
  } catch (error) {
    console.error(error)
    instructionMessage.value = "오류가 발생했습니다. 다시 시도해주세요."
  }
}

const resetInput = () => {
  firstInput.value = []
  secondInput.value = []
  step.value = 1
  // instructionMessage.value = "비밀번호를 입력해주세요"
}
onUnmounted(() => {
  pinPadImage.value = null
})
onMounted(() => {
  fetchPublicKey()
  fetchPinPadImage()
})
</script>

<style scoped></style>
<!-- 
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
} -->
<!-- .pin-container {
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
} -->
<!-- 클릭 이벤트를 받는 투명한 상자들 -->
<!-- const onBackClick = () => {
  console.log('뒤로가기 버튼 클릭됨');
};

const onCheckClick = () => {
  console.log('확인 버튼 클릭됨');
  console.log('클릭된 상자들:', clickedBoxes.value);
}; -->
<!-- <div v-for="(box, index) in numberBoxes" :key="index"
class="absolute box"
:style="boxStyle(box)"
@click.stop="onBoxClick(index)">
</div>


const numberBoxes = ref<{ x: number; y: number; width: number; height: number }[]>([]);
const clickedBoxes = ref<number[]>([]); // 클릭된 상자의 인덱스를 저장
<div class="text-center mt-4">비밀번호를 입력해주세요</div> -->

<!-- <template>
  <div class="pin-pad relative">
    <img
      :src="pinPadImage"
      @click="onPinPadClick"
      class="w-full cursor-pointer"
      alt="Pin Pad"
    />
    <div
      v-for="(box, index) in numberBoxes"
      :key="index"
      class="absolute border border-gray-500"
      :style="boxStyle(box)"
    >
      {{ box.number }}
    </div>
    <div class="text-center mt-4">비밀번호를 입력해주세요</div>
    <button class="bg-orange-500 text-white py-2 rounded mt-4">확인</button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';

const pinPad = ref<number[]>([]);
const pinPadImage = ref<string>('');
const numberBoxes = ref<{ number: number; x: number; y: number; width: number; height: number }[]>([]);

const fetchPinPad = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/pinpad', {
      headers: {
        'Accept': 'application/json'
      }
    });
    pinPad.value = response.data.pinPad;
    pinPadImage.value = `data:image/png;base64,${response.data.encryptedImage}`;
    numberBoxes.value = calculateNumberBoxes(response.data.pinPad);
  } catch (error) {
    console.error(error);
  }
};



const onPinPadClick = (event: MouseEvent) => {
  const img = event.currentTarget as HTMLImageElement;
  const rect = img.getBoundingClientRect();
  const x = event.clientX - rect.left; // 이미지 내 x 좌표
  const y = event.clientY - rect.top;  // 이미지 내 y 좌표

  console.log('Clicked at position:', { x, y });
  axios.post('/api/pinpad/submit', { x, y });
};

const boxStyle = (box: { x: number; y: number; width: number; height: number }) => ({
  left: `${box.x}px`,
  top: `${box.y}px`,
  width: `${box.width}px`,
  height: `${box.height}px`
});

onMounted(fetchPinPad);
</script>

<style scoped>
.pin-pad {
  position: relative;
}


</style> -->
