<template>
  <div class="pin-pad relative">
    <!-- 실제 이미지 -->
    <img
      :src="pinPadImage"
      class="w-full"
      alt="Pin Pad"
    />

    <!-- 클릭 이벤트를 받는 투명한 상자들 -->
    <div v-for="(box, index) in numberBoxes" :key="index"
         class="absolute box"
         :style="boxStyle(box)"
         @click.stop="onBoxClick(index)">
    </div>

    <!-- 버튼들 -->
    <button class="absolute bottom-4 left-4 bg-gray-500 text-white py-2 px-4 rounded" @click="onBackClick">
      뒤로가기
    </button>
    <button class="absolute bottom-4 right-4 bg-orange-500 text-white py-2 px-4 rounded" @click="onCheckClick">
      확인
    </button>

    <div class="text-center mt-4">비밀번호를 입력해주세요</div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';

const pinPadImage = ref<string>('');
const numberBoxes = ref<{ x: number; y: number; width: number; height: number }[]>([]);
const clickedBoxes = ref<number[]>([]); // 클릭된 상자의 인덱스를 저장

const fetchPinPad = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/pinpad', {
      headers: {
        'Accept': 'application/json'
      }
    });
    pinPadImage.value = `data:image/png;base64,${response.data.encryptedImage}`;
    numberBoxes.value = calculateNumberBoxes(response.data.pinPad);
  } catch (error) {
    console.error(error);
  }
};

const onBackClick = () => {
  console.log('뒤로가기 버튼 클릭됨');
};

const onCheckClick = () => {
  console.log('확인 버튼 클릭됨');
  console.log('클릭된 상자들:', clickedBoxes.value);
};

const onBoxClick = (index: number) => {
  if (!clickedBoxes.value.includes(index)) {
    clickedBoxes.value.push(index);
  }
};

const boxStyle = (box: { x: number; y: number; width: number; height: number }) => ({
  left: `${box.x}px`,
  top: `${box.y}px`,
  width: `${box.width}px`,
  height: `${box.height}px`,
  backgroundColor: 'rgba(0, 0, 0, 0)', // 투명 배경
  border: '2px solid black', // 상자의 경계선
  cursor: 'pointer' // 커서 모양을 기본으로 설정
});

onMounted(fetchPinPad);

// 3x4 그리드의 각 상자의 크기와 위치를 계산하는 함수 (예시)
// 실제 배치는 이미지의 크기와 상자 사이의 간격에 따라 조정해야 합니다.
function calculateNumberBoxes(pinPad: number[]): { x: number; y: number; width: number; height: number }[] {
  const imageWidth = 400; // 이미지의 가로 크기
  const imageHeight = 400; // 이미지의 세로 크기
  const numRows = 4;
  const numCols = 3;
  const boxWidth = imageWidth / numCols;
  const boxHeight = imageHeight / numRows;
  const padding = 40; // 상자 사이의 간격

  const boxes: { x: number; y: number; width: number; height: number }[] = [];

  for (let row = 0; row < numRows; row++) {
    for (let col = 0; col < numCols; col++) {
      const x = col * (boxWidth + padding);
      const y = row * (boxHeight + padding);
      boxes.push({ x, y, width: boxWidth, height: boxHeight });
    }
  }

  return boxes;
}
</script>

<style scoped>
.pin-pad {
  position: relative;
}

.box {
  pointer-events: all;
  cursor: default; /*상자 내부에서 커서 모양을 기본으로 설정 */
}

/* 상자 외부에서 커서 모양을 기본으로 설정 */
.pin-pad {
  cursor: default;
}
</style>




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
