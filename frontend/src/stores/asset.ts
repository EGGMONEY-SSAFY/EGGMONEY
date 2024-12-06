import { ref } from "vue"
import { defineStore } from "pinia"
import axios from "axios"
import type { User } from "./user"
import { useAuthStore } from "./auth"

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
export interface Withdrawal {
  withdrawalId: number
  applyer: User
  applyee: User
  withdrawalPrice: Number
  type: string
  createdAt: string
  updatedAt: string
}

export const useAssetStore = defineStore("asset", () => {
  const API_URL = "/api/v1/asset"
  const deposit = ref<Number | null>(null)
  const savings = ref<Number | null>(null)
  const loan = ref<Number | null>(null)
  const mainAccount = ref<Number | null>(null)
  const stock = ref<Number | null>(null)
  const logs = ref<TradeData[]>([])
  const withdrawalList = ref<Withdrawal[]>([])
  const mainAccountPages = ref<number>(1)
  const authStore = useAuthStore()

  // 유저 자산 조회
  const getPort = function (userId: Number): Promise<void> {
    return axios({
      method: "post",
      url: `${API_URL}/analytics`,
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`,
      },
      data: {
        userId: userId,
      },
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

  // 유저 로그 내역 조회 ( 차트 그리기용 )
  const getAccountChartLog = function (userId: Number): Promise<void> {
    return axios({
      method: "post",
      url: `${API_URL}/main-account/3/log`,
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`,
      },
      data: {
        userId: userId,
      },
    })
      .then((res) => {
        /* eslint-disable prefer-const */
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

  // 유저 로그 내역 조회
  const getAccountLog = function (userId: number, page: number): Promise<void> {
    return axios({
      method: "post",
      url: `${API_URL}/main-account/log`,
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`,
      },
      data: {
        userId: userId,
      },
      params: {
        page: page - 1,
        size: 10,
      },
    })
      .then((res) => {
        let logsArray: TradeData[] = []
        res.data.content.forEach((data: TradeData) => {
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
        mainAccountPages.value = res.data.totalPages
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 출금 요청 생성
  const createWithdrawal = function (price: number): Promise<void> {
    return axios({
      method: "post",
      url: `${API_URL}/withdrawal/create`,
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`,
      },
      data: {
        price: price,
      },
    })
      .then((res) => {

      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 출금 요청 조회
  const getWithdrawalList = function (userId: Number): Promise<void> {
    return axios({
      method: "post",
      url: `${API_URL}/withdrawal/log`,
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`,
      },
      data: {
        userId: userId,
      },
    })
      .then((res) => {
        withdrawalList.value = res.data
        withdrawalList.value
      })
      .catch((err) => {
        console.error(err)
      })
  }

  // 출금 요청 조회
  const sendWithdrawalJudge = function (
    withdrawalId: number,
    judge: string,
    userId: number
  ): Promise<void> {
    return axios({
      method: "post",
      url: `${API_URL}/withdrawal/judge/${withdrawalId}`,
      data: {
        userId: userId,
        judge: judge,
      },
    })
      .then((res) => {})
      .catch((err) => {
        console.error(err)
      })
  }

  // 퀴즈 정답 오답 로그
  const sendQuizJudge = function (quizId: number, answer: Number): Promise<void> {
    return axios({
      method: "post",
      url: `/api/v1/quiz/judge/${quizId}`,
      data: {
        answer: answer,
      },
      headers: {
        Authorization: `Bearer ${authStore.accessToken}`,
      },
    })
      .then((res) => {})
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
    withdrawalList,
    mainAccountPages,
    getPort,
    getAccountLog,
    getAccountChartLog,
    createWithdrawal,
    getWithdrawalList,
    sendWithdrawalJudge,
    sendQuizJudge,
  }
})
