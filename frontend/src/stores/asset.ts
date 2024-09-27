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

export interface Analytics {
  입출금통장: Number | null
  예금: Number | null
  적금: Number | null
  대출: Number | null
  주식: Number | null
}

export interface ChartData {
  labels: string[]
  datasets: {
    data: number[]
  }[]
}

export const useAssetStore = defineStore("asset", () => {
  const API_URL = "/api/v1/asset"
  const deposit = ref<Number | null>(null)
  const savings = ref<Number | null>(null)
  const loan = ref<Number | null>(null)
  const mainAccount = ref<Number | null>(null)
  const stock = ref<Number | null>(null)
  const logs = ref<TradeData[]>([])

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
        loan.value = res.data.loan
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
          } else if (data.tradeTarget === "LOAN") {
            data.tradeTarget = "대출"
          } else if (data.tradeTarget === "DEPOSIT") {
            data.tradeTarget = "예금"
          } else if (data.tradeTarget === "STOCK") {
            data.tradeTarget = "주식"
          } else if (data.tradeTarget === "SAVINGS") {
            data.tradeTarget = "적금"
          } else if (data.tradeTarget === "ALLOWANCE") {
            data.tradeTarget = "용돈"
          }
          logsArray.push(data)
        })
        logs.value = logsArray
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
    getPort,
    getAccountLog,
  }
})
