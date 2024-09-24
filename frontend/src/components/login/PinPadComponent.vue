<template>
  <div class="pin-container relative mx-auto mt-5">
    <!-- 실제 이미지 -->
    <img
      :src="pinPadImage"
      class="w-full h-auto"
      alt="Pin Pad"
      v-if="pinPadImage"
    />
    <button v-for="(number, index) in numbers" key="index"
        class="absolute keypad-button bg-white text-black border border-gray-400 rounded-md shadow-md hover:bg-gray-200"
        :style="buttonStyle(index)"
        @click="onButtonClick(number)">
    </button>
    
    <div class="text-center mt-6 text-lg font-semibold text-gray-700">
      {{ instructionMessage }}
    </div>
  </div>
</template>
<!-- 0924 키패드 수정 -->
<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const pinPadImage = ref<string|null>(null);
const numbers = ref<number[]>([1,2,3,4,5,6,7,8,9,10,0,11]);
const firstInput = ref<number[]>([]);
const secondInput = ref<number[]>([]);
const step = ref<number>(1);
const publicKey = ref<string>('');
const instructionMessage = ref<string>('비밀번호를 입력해주세요');


const authStore = useAuthStore();

const fetchPublicKey = async()=>{
  try{
    const token = authStore.accessToken;
    console.log(token)
    const response = await axios.get('http://localhost:8080/api/public-key',{
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type':'application/json',
      }
    });
    
    publicKey.value = response.data;
  }catch(error){
    console.error('공개 키:', error);
  }
}

const fetchPinPad = async () => {
  try {
    const token = authStore.accessToken;
    console.log(token)
    const response = await axios.get('http://localhost:8080/api/pinpad', {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type':'application/json',
      }
    });
    pinPadImage.value = `data:image/png;base64,${response.data.encryptedImage}`;
  } catch (error) {
    console.error(error);
  }
};

const onButtonClick = (number:number|string)=>{
  if(number===10){
    if(step.value ===1){
      firstInput.value.pop();
    }else{
      secondInput.value.pop();
    }return;
  }
  if(step.value ===1){
    firstInput.value.push(number);
    if(firstInput.value.length ===4){
      instructionMessage.value = '비밀번호를 한 번 더 입력해주세요';
      step.value = 2;
    }
  } else if(step.value ===2){
    secondInput.value.push(number);
    if(secondInput.value.length === 4){
      verifyInput();
    }
  }
}

const verifyInput=()=>{
  if(firstInput.value.join('') === secondInput.value.join('')){
    const hashedPin = .toString();
    sendToBackend(hashedPin);
  }else{
    instructionMessage.value = '비밀번호가 일치하지 않습니다. 다시 시도해주세요';
    resetInput();
  }
}

const sendToBackend = async(hashedPin:string)=>{
  try{
    await axios.post('http://localhost:8080/api/pinpad/verify',{
      encryptedPin:hashedPin
    });
    instructionMessage.value='비밀번호 설정이 완료되었습니다!';
  }catch(error){
    console.error(error);
    instructionMessage.value = '오류가 발생했습니다. 다시 시도해주세요.';
  }
}

const resetInput =()=>{
  firstInput.value = [];
  secondInput.value = [];
  step.value = 1;
  instructionMessage.value ='비밀번호를 입력해주세요';
}
onUnmounted(()=>{
  pinPadImage.value = null;
})
onMounted(fetchPinPad);

// 버튼 스타일 설정
const buttonStyle = (index: number) => {
  const row = Math.floor(index / 3);
  const col = index % 3;
  const buttonSize = 80; // 버튼 크기
  const padding = 10; // 버튼 사이 간격

  return {
    left: `${col * (buttonSize + padding)}px`,
    top: `${row * (buttonSize + padding)}px`,
    width: `${buttonSize}px`,
    height: `${buttonSize}px`,
    fontSize: '24px',
    textAlign: 'center'
  };
};

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
}
</style>


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
