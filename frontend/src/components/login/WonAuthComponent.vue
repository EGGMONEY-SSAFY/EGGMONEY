<template>
  <div class="p-5 bg-gray-100 rounded-lg shadow-md">
    <div v-if="bankselectstep === 0" class="step">
      <p class="text-main-color text-lg font-bold mb-4">주로 쓰는 은행 계좌를 선택해주세요</p>
      <div class="grid grid-cols-2 gap-4">
        <div
          @click="selectBank(index)"
          v-for="(item, index) in bankitems"
          :key="index"
          class="bg-white rounded-lg p-4 text-center cursor-pointer hover:scale-105 transition-transform"
        >
          <img :src="item" alt="bank logo" class="w-12 h-12 mx-auto mb-2" />
          <p class="text-sm">{{ bankname[index] }}</p>
        </div>
      </div>
    </div>

    <div v-else-if="bankselectstep === 1" class="step">
      <p class="text-main-color text-lg font-bold mb-4" v-if="selectbank !== null">
        {{ bankname[selectbank] }}계좌번호를 입력해주세요
      </p>
      <input
        type="text"
        v-model="selectaccount"
        class="w-full p-2 border border-gray-300 rounded mb-4"
        placeholder="계좌번호 입력"
      />
      <button
        @click="sendWonAuth"
        class="w-full p-2 bg-orange-500 text-white rounded hover:bg-orange-600 transition"
      >
        1원 인증
      </button>
    </div>

    <div v-else-if="bankselectstep === 2" class="step">
      <p class="text-main-color text-lg font-bold mb-4" v-if="selectbank !== null">
        {{ bankname[selectbank] }} 입금내역을 확인하시고, 에그머니 뒤 4자리 숫자를 알려주세요.
      </p>
      <img
        :src="bankitems[selectbank]"
        alt="bank logo"
        class="w-12 h-12 mx-auto mb-2"
        v-if="selectbank !== null"
      />
      <p class="text-sm text-center" v-if="selectbank !== null">{{ bankname[selectbank] }}</p>
      <p class="text-center">{{ selectaccount }}</p>
      <input
        type="text"
        v-model="checkAuth"
        class="w-full p-2 border border-gray-300 rounded mb-4"
        placeholder="인증번호 입력"
      />
      <button
        @click="checkAuthNumber"
        class="w-full p-2 bg-orange-500 text-white rounded hover:bg-orange-600 transition"
      >
        인증하기
      </button>
    </div>

    <div v-else-if="bankselectstep === 3" class="step">
      <CreateAccountSuccess/>
    </div>
  </div>
  <!-- <div v-else-if="bankselectstep === 2">
            <p>계좌 인증을 위해 1원을 보내볼게요</p>
            <img :src="bankitems[selectbank]" alt="bank logo">
            <p>{{ bankname[selectbank] }}</p>
            <p>{{ selectaccount }}</p>
            <input type="text" v-model="checkAuth" name="인증번호">
            <button @click="checkAuthNumber">인증하기</button>
        </div> -->
</template>

<script setup lang="ts">
import { ref } from "vue"
import axios from "axios"
import kb from "@/assets/bank/kb로고.png"
import nh from "@/assets/bank/농협로고.png"
import sh from "@/assets/bank/신한로고.png"
import wo from "@/assets/bank/우리로고.png"
import toss from "@/assets/bank/토스로고.png"
import ha from "@/assets/bank/하나로고.png"
import { useAuthStore } from "@/stores/auth"
import CreateAccountSuccess from "./complete/CreateAccountSuccess.vue"
const authStore = useAuthStore();
const bankitems = [kb, nh, sh, wo, toss, ha]
const bankname = ["KB 국민은행", "농협은행", "신한은행", "우리은행", "토스", "하나은행"]

const bankselectstep = ref(0)
const selectbank = ref<number | null>(null)
const selectaccount = ref<string | null>(null)
const checkAuth = ref<string | null>(null)

const selectBank = (index: number) => {
  selectbank.value = index
  bankselectstep.value += 1
}

const sendWonAuth = async () => {
  if (selectaccount.value) {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/won/send",
        {
          accountnum: selectaccount.value,
          //'0015279150409321'
          // selectaccount.value
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      )
      console.log(selectaccount.value)
      console.log(response)
      bankselectstep.value += 1
    } catch (error) {
      console.error(error)
      alert("계좌번호를 다시 입력해주세요.")
    }
  } else {
    alert("계좌번호를 입력해주세요.")
  }
}

const checkAuthNumber = async () => {
  if (selectaccount.value && checkAuth.value) {
    try {
      // const token = authStore.accessToken;
      const token='y8bVYxkfMkStpHnhnYsiwSh-aJr_XBwJAAAAAQo9cpcAAAGSML9Ig5CBbdpZdq0Z';
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/won/check",
        
      {
        accountnum: selectaccount.value,
        authText: "SSAFY_TEST",
        authnum: checkAuth.value,
      },{
        headers:{
          Authorization: `Bearer ${token}`,
          'Content-Type':'application/json',
        }
      })
      console.log(response)
      bankselectstep.value += 1
    } catch (error) {
      console.error(error)
      alert("인증번호를 다시 입력해주세요.")
    }
  } else {
    alert("인증번호를 입력해주세요.")
  }
}
</script>

<style scoped>
/* 필요한 스타일을 여기 추가하세요 */
</style>
