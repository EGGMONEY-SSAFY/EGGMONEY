import { ref } from "vue"
import { defineStore } from "pinia"
import axios from "axios"

export const useAssetStore = defineStore("asset", () => {
  const API_URL = 'http://localhost:8000/api/v1/assets'
  const deposit = ref(null)
  const savings = ref(null)
  const loan = ref(null)
  const mainAccount = ref(null)

// 메인계좌 조회
const getAccount = function (userId:number) {
    axios({
      method: 'get',
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      }
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

// 예금 조회
const getDeposit = function (userId:number) {
    axios({
      method: 'get',
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      }
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

// 적금계좌 조회
const getSavings = function (userId:number) {
    axios({
      method: 'get',
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      }
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

// 대출 조회
const getLoan = function (userId:number) {
    axios({
      method: 'get',
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      }
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }


  return { deposit, savings, loan, mainAccount,
    getAccount, getDeposit, getSavings, getLoan
   }
})
