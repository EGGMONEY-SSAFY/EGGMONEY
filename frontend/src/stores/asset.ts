import { ref } from "vue"
import { defineStore } from "pinia"
import axios from "axios"

export interface TradeData {
  accountId: number
  currentBalance: number
  tradePrice: number
  tradeTarget: string
  createdAt: string
}

export const useAssetStore = defineStore("asset", () => {
  const API_URL = "/api/v1/asset"
  const deposit = ref<Number | null>(null)
  const savings = ref<Number | null>(null)
  const loan = ref<Number | null>(null)
  const mainAccount = ref<Number | null>(null)
  const stock = ref<Number | null>(null)
  const logs = ref<TradeData[]>([])

  // 메인계좌 조회
  const getAccount = function (userId: number) {
    axios({
      method: "get",
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      },
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 예금 조회
  const getDeposit = function (userId: number) {
    axios({
      method: "get",
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      },
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 적금계좌 조회
  const getSavings = function (userId: number) {
    axios({
      method: "get",
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      },
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 대출 조회
  const getLoan = function (userId: number) {
    axios({
      method: "get",
      url: `${API_URL}/main-account/${userId}`,
      data: {
        // "username": payload.username,
      },
    })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 유저 자산 조회
  const getPort = function (userId: number): Promise<void> {
    return axios({
      method: "get",
      url: `${API_URL}/analytics/${userId}`,
    })
      .then((res) => {
        deposit.value = res.data.deposit
        savings.value = res.data.savings
        mainAccount.value = res.data.mainAccountBalance
        loan.value = res.data.value
        stock.value = res.data.stock
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 유저 로그 내역 조회
  const getAccountLog = function (userId: number): Promise<void> {
    return axios({
      method: "get",
      url: `${API_URL}/main-account/${userId}/3/log`,
    })
      .then((res) => {
        let logsArray: TradeData[] = []
        res.data.forEach((data: TradeData) => {
          if (data.tradeTarget === "WITHDRAWAL") {
            data.tradeTarget = "출금"
          } else if (data.tradeTarget === "loan") {
            data.tradeTarget = "대출"
          } else if (data.tradeTarget === "deposit") {
            data.tradeTarget = "예금"
          } else if (data.tradeTarget === "stock") {
            data.tradeTarget = "주식"
          } else if (data.tradeTarget === "savings") {
            data.tradeTarget = "적금"
          }
          logsArray.push(data)
        })
        logs.value = logsArray
        console.log(logs.value)
      })
      .catch((err) => {
        console.error(err)
      })
  }

  return {
    deposit,
    savings,
    loan,
    mainAccount,
    stock,
    logs,
    getAccount,
    getDeposit,
    getSavings,
    getLoan,
    getPort,
    getAccountLog,
  }
})
