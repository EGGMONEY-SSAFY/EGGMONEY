<template>
  <div class="px-4 py-6" v-if="!isLoading">
    <!-- 자녀 선택 드롭다운 -->
    <div class="bg-white rounded-lg shadow-md p-4">
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
      <div>
        <!-- 자녀 정보 -->
        <span class="font-semibold">{{ parent.name }}님은 현재</span>
        <span class="font-bold text-orange-600">{{ selectedChild?.name }}</span
        >에게
      </div>
      <hr class="my-2" />
      <div class="text-gray-500">
        <span class="bg-orange-100 text-orange-500 px-2 py-1 rounded-full">{{
          getTranslatedPeriod(selectedChild?.allowancePeriod)
        }}</span>
        <span>마다</span>
        <span class="font-semibold">{{ selectedChild?.price.toLocaleString() }}</span> 알을 주고
        있습니다
      </div>
    </div>

    <hr class="my-3" />

    <!-- 용돈 단위 설정 (주, 월) -->
    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="font-semibold text-gray-700 mb-2">
        {{ selectedChild?.name }}에게 줄 단위를 설정해주세요
      </div>
      <select
        v-model="selectedPeriodUnit"
        class="border border-gray-300 rounded-lg p-2 w-full focus:ring-2 focus:ring-orange-500"
      >
        <option value="주">주</option>
        <option value="월">월</option>
      </select>
    </div>

    <!-- <hr class="my-6" /> -->

    <!-- 용돈 줄 시기 설정 (요일 혹은 날짜) -->
    <div class="bg-white rounded-lg shadow-md p-4 mb-6">
      <div class="font-semibold text-gray-700 mb-2">용돈을 줄 시기를 설정해주세요</div>
      <!-- 요일 선택 상자 -->
      <div v-if="selectedPeriodUnit === '주'" class="grid grid-cols-7 gap-2">
        <button
          v-for="(day, index) in daysOfWeek"
          :key="index"
          @click="selectedAllowanceDay = index"
          :class="{
            'bg-orange-500 text-white': selectedAllowanceDay === index,
            'bg-orange-100 text-orange-500': selectedAllowanceDay !== index,
          }"
          class="border border-gray-300 rounded-lg p-2 text-center cursor-pointer"
        >
          {{ day }}
        </button>
      </div>
      <!-- 날짜 선택 -->
      <div v-if="selectedPeriodUnit === '월'" class="grid grid-cols-7 gap-2">
        <button
          v-for="day in 31"
          :key="day"
          @click="selectedAllowanceDay = day"
          :class="{
            'bg-orange-500 text-white': selectedAllowanceDay === day,
            'bg-orange-100 text-orange-500': selectedAllowanceDay !== day,
          }"
          class="border border-gray-300 rounded-lg p-2 text-center cursor-pointer"
        >
          {{ day }}
        </button>
      </div>
    </div>

    <!-- <hr class="my-6" dddddddddddddddddddd/> -->

    <!-- 용돈 설정 -->
    <div class="bg-white rounded-lg shadow-md p-4 mb-4">
      <div class="font-semibold text-gray-700 mb-2">
        {{ selectedChild?.name }}에게 줄 용돈을 설정해 주세요
      </div>
      <div class="flex items-center">
        <input
          v-model="allowanceAmount"
          type="number"
          class="border border-gray-300 rounded-lg p-2 w-24 text-right focus:ring-2 focus:ring-orange-500"
        />
        <span class="ml-2">알</span>
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
    <div v-if="showModal" class="modal p-12">
      <img src="@/assets/common/완료 폭죽.png" class="w-40 h-auto" />
      <div class="mt-4">
        <span class="text-blue-700 font-bold mt-4">용돈 정보 수정 완료</span>
      </div>
    </div>
  </div>
  <div v-else>Loading...</div>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue"
import axios from "axios"
import { useVariableStore } from "@/stores/variable"
import { useAuthStore } from "@/stores/auth"

const authStore = useAuthStore()

const store = useVariableStore()
store.setTitle("용돈 수정")

interface Child {
  name: string
  id: number
  price: number
  allowancePeriod: string
  allowanceDay: number
}
const parent = ref({ name: "김엄마" }) // 로그인 유저의 정보 user.name;

const children = ref<Child[]>([])
const selectedChild = ref<Child | null>(null)
const selectedChildId = ref<number | null>(null)
const selectedPeriodUnit = ref("")
const selectedAllowanceDay = ref<number | null>(null)

const allowanceAmount = ref(0)

const showModal = ref(false)
const isLoading = ref(true)
const daysOfWeek = ["월", "화", "수", "목", "금", "토", "일"]
onMounted(async () => {
  // const response = {
  //   data: [
  //     { name: "김아들", id: 1, price: 30000, allowance_period: "주", allowance_day: 1 },
  //     { name: "김딸", id: 2, price: 20000, allowance_period: "월", allowance_day: 15 },
  //   ],
  // }

  const token = authStore.accessToken

  try {
    const response = await axios.get("/api/v1/total/money/search", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
    console.log(response)
    children.value = response.data
    selectedChild.value = children.value[0]
    selectedPeriodUnit.value = selectedChild.value?.allowancePeriod ?? ""
    allowanceAmount.value = selectedChild.value?.price ?? 0
    isLoading.value = false
  } catch (error) {
    console.error("자녀 정보를 불러오는 중 오류", error)
  }
})
const getTranslatedPeriod = (period: any) => {
  if (period === "WEEK") {
    return "주"
  } else if (period === "MONTH") {
    return "월"
  }
  console.log(period)
  return period // 기본값 그대로 반환
}
const translatePeriodForServer = (period: string) => {
  if (period === "주") {
    return "WEEK"
  } else if (period === "월") {
    return "MONTH"
  }
  return period
}
const updateSelectedChild = () => {
  const child = children.value.find((c) => c.id === selectedChildId.value)
  if (child) {
    selectedChild.value = child
    selectedPeriodUnit.value = child.allowancePeriod
    selectedAllowanceDay.value = child.allowanceDay
    allowanceAmount.value = child.price
  }
}
const sumbitchanges = async () => {
  const token = "_hY7xGfo9UfUokhsO-xd8eLTYiIxygDrAAAAAQopyWAAAAGSSv-H8ZCBbdpZdq0Z"
  if (selectedChild.value) {
    try {
      const periodForServer = translatePeriodForServer(selectedPeriodUnit.value)
      console.log(
        selectedChild.value.id,
        selectedPeriodUnit.value,
        selectedAllowanceDay.value,
        allowanceAmount.value
      )
      const response = await axios.post(
        "/api/v1/total/money/update",
        {
          allowanceId: selectedChild.value.id,
          allowancePeriod: periodForServer,
          allowanceDay: selectedAllowanceDay.value,
          price: allowanceAmount.value,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      )
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
<style scoped>
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  border-radius: 10px;
}
button {
  cursor: pointer;
}
</style>
