<template>
  <div class="px-4 py-6">
    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="flex justify-between items-center">
        <select
          v-model="selectedChildId"
          @change="updateSelectedChild"
          class="border border-gray-300 rounded-lg p-2 w-40 text-orange-600 font-bold"
        >
          <option v-for="child in children" :key="child.id" :value="child.id">
            {{ child.name }}
          </option>
        </select>
        <i class="fas fa-bell text-orange-500"></i>
        <!-- 알림 아이콘 -->
      </div>
      <div class="mt-">
        <!-- 자녀 정보 -->
        <span class="font-semibold">{{ parent.name }}님은 현재</span>
        <span class="font-bold text-orange-600">{{ selectedChild?.name }}</span
        >에게
      </div>
      <hr class="my-2" />
      <div class="text-gray-500">
        <input
          class="bg-orange-100 text-orange-500 px-2 py-1 rounded-full w-20"
          type="number"
          v-model="selectedStockRatio"
        />
        <span>퍼센트로 주식투자비율을 제한하고 있습니다.</span>
      </div>
    </div>
    <!-- 수정 완료 버튼 -->
    <button
      class="w-full px-4 py-3 bg-main-color text-white font-semibold rounded-lg mt-4"
      @click="sumbitchanges"
    >
      수정 완료
    </button>

    <!-- 수정완료 모달 -->
    <div
      v-if="showModal"
      class="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white p-8 rounded-lg shadow-lg w-64 h-auto"
    >
      <img src="@/assets/common/완료 폭죽.png" class="w-40 h-auto" />
      <div class="mt-4">
        <span class="text-blue-700 font-bold mt-4">주식 비율 수정 완료</span>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue"
import axios from "axios"
import { useVariableStore } from "@/stores/variable"

const store = useVariableStore()
store.setTitle("주식 투자비율 제한")

interface Child {
  name: string
  id: number
  stockRatio: number
}
const parent = ref({ name: "김엄마" }) // 로그인 유저의 정보 user.name;

const children = ref<Child[]>([])
const selectedChild = ref<Child | null>(null)
const selectedChildId = ref<number | null>(null)
const selectedStockRatio = ref<number | null>(null)
const showModal = ref(false)
onMounted(async () => {
  const response = {
    data: [
      { name: "김아들", id: 1, stockRatio: 10 },
      { name: "김딸", id: 2, stockRatio: 50 },
    ],
  }
  try {
    // const response = await axios.get('/api/v1/family/1/searchchild');
    console.log(response)
    children.value = response.data
    selectedChild.value = children.value[0]
    selectedStockRatio.value = selectedChild.value?.stockRatio ?? ""
  } catch (error) {
    console.error("자녀 정보를 불러오는 중 오류", error)
  }
})

const updateSelectedChild = () => {
  const child = children.value.find((c) => c.id === selectedChildId.value)
  if (child) {
    selectedChild.value = child
    selectedStockRatio.value = child.stockRatio
  }
}
const sumbitchanges = async () => {
  if (selectedChild.value) {
    try {
      console.log(selectedChild.value.id, selectedStockRatio.value)
      // const response = await axios.post('/api/allowance', {
      // user_id: selectedChild.value.id,
      // allowance_period: selectedPeriodUnit.value,
      // allowance_day: allowanceAmount.value
      // });
      showModal.value = true
      setTimeout(() => {
        showModal.value = false
      }, 1000)
    } catch (error) {
      console.error("수정하는 중 오류", error)
    }
  }
}
</script>
<style></style>
