import { defineStore } from "pinia"
import axios from "axios"
import { reactive, ref } from "vue"
export interface depositProducts {
  productId: number
  productName: string
  depositRate: Number
  depositDate: Number
}
export interface savingsProducts {
  productId: number
  productName: string
  savingsRate: number
  savingsDate: number
  maxPrice: number
}

export interface Savings {
  savingsId: Number | null
  savingsDate: Number | null // 적금 기간 (예: 5년)
  savingsRate: Number | null // 적금 이율 (예: 4.0%)
  productName: string | null // 상품 이름 (예: 좋은 적금)
  balance: Number | null // 잔액 (예: 50000원)
  expireDate: string | null // 만기일 (ISO 형식 날짜)
  createdAt: string | null // 생성일 (ISO 형식 날짜)
  paymentDate: Number | null // 납입일 (예: 1일)
  paymentMoney: Number | null // 납입금액 (예: 10000원)
}

export interface SavingsLogs {
  balance: number
  paymentMoney: number
  createdAt: string
}

export interface Deposit {
  depositId: number | null
  depositProduct: depositProducts | null
  createdAt: string | null
  expireDate: string | null
  depositMoney: Number | null
}

export interface Loan {
  loanId: number | null
  userName: string | null
  loanType: string | null
  loanStatus: string | null
  loanAmount: number | null
  loanDate: number | null
  balance: number
  loanReason: string | null
  refuseReason: string | null
  loanRate: number | null
  expirationDate: string | null
  createdAt: string | null
}

export interface LoanLog {
  id: number
  balance: number
  repayment: number
  createdAt: string
}

export const useFinStore = defineStore(
  "fin",
  () => {
    const DEPOSIT_PRODUCT_API_URL = "/api/v1/fin/deposit/product"
    const SAVINGS_PRODUCT_API_URL = "/api/v1/fin/savings/product"
    const USER_SAVINGS_API_URL = "/api/v1/fin/savings"
    const USER_SAVINGS_LOG_API_URL = "/api/v1/fin/savings/log"
    const USER_DEPOSIT_API_URL = "/api/v1/fin/deposit"
    const USER_LOAN_API_URL = "/api/v1/fin/loan"
    const USER_LOAN_LOG_API_URL = "/api/v1/fin/loan/log"
    const USER_LOAN_DETAIL_API_URL = "/api/v1/fin/loan/detail"
    const USER_SAVINGS_SEND_API_URL = "/api/v1/fin/savings/send"
    const USER_LOAN_SEND_API_URL = "/api/v1/fin/loan/send"
    const DELETE_SAVINGS_API_URL = "/api/v1/fin/savings/delete"
    const DELETE_DEPOSIT_API_URL = "/api/v1/fin/deposit/delete"

    const isYellowPage = ref<boolean>(false)
    const depositProducts = reactive<depositProducts[]>([])
    const savingsProducts = reactive<savingsProducts[]>([])

    const savings = ref<Savings | null>({
      savingsId: null,
      savingsDate: null,
      savingsRate: null,
      productName: null,
      balance: null,
      expireDate: null,
      paymentDate: null,
      paymentMoney: null,
      createdAt: null,
    })

    const savingsLogs = ref<SavingsLogs[] | null>(null)
    const deposit = ref<Deposit | null>(null)
    const loan = ref<Loan | null>(null)
    const loanList = ref<Loan[] | null>(null)
    const loanLogs = ref<LoanLog[] | null>([])

    const getDepositProduct = function () {
      axios({
        method: "GET",
        url: `${DEPOSIT_PRODUCT_API_URL}`,
      })
        .then((res) => {
          depositProducts.push(...res.data)
          console.log(res.data)
        })
        .catch((err) => {
          console.error(err)
        })
    }

    const getSavingsProduct = function () {
      axios({
        method: "GET",
        url: `${SAVINGS_PRODUCT_API_URL}`,
      })
        .then((res) => {
          savingsProducts.push(...res.data)
          console.log(res.data)
        })
        .catch((err) => {
          console.error(err)
        })
    }

    // User 적금 계좌 조회
    const getUserSavings = function (userId: Number): Promise<void> {
      return axios({
        method: "get",
        url: `${USER_SAVINGS_API_URL}/${userId}`,
      })
        .then((res) => {
          {
            savings.value = {
              savingsId: res.data.savingsId,
              savingsDate: res.data.savingsDate,
              savingsRate: res.data.savingsRate,
              productName: res.data.productName,
              balance: res.data.balance,
              expireDate: res.data.expireDate,
              paymentDate: res.data.paymentDate,
              paymentMoney: res.data.paymentMoney,
              createdAt: res.data.createdAt,
            }
          }
        })
        .catch((err) => {
          savings.value = null
          console.error(err)
        })
    }

    // User 적금 로그 조회
    const getUserSavingsLogs = function (savingsId: Number): Promise<void> {
      return axios({
        method: "get",
        url: `${USER_SAVINGS_LOG_API_URL}/${savingsId}`,
      })
        .then((res) => {
          let logsArray: SavingsLogs[] = []
          res.data.forEach((log: SavingsLogs) => {
            logsArray.push(log)
          })
          savingsLogs.value = logsArray
        })
        .catch((err) => {
          console.error(err)
        })
    }

    // User 예금 계좌 조회
    const getUserDeposit = function (userId: Number): Promise<void> {
      return axios({
        method: "get",
        url: `${USER_DEPOSIT_API_URL}/${userId}`,
      })
        .then((res) => {
          deposit.value = res.data
          console.log(deposit.value)
        })
        .catch((err) => {
          deposit.value = null
          console.error(err)
        })
    }

    // User 대출 리스트 조회
    const getUserLoanList = function (userId: Number): Promise<void> {
      return axios({
        method: "get",
        url: `${USER_LOAN_API_URL}/${userId}`,
      })
        .then((res) => {
          let loanValue: Loan[] = []
          res.data.forEach((data: Loan) => {
            loanValue.push(data)
          })
          loanList.value = loanValue
        })
        .catch((err) => {
          loanList.value = null
          console.error(err)
        })
    }

    // User 대출 조회
    const getUserLoan = function (loanId: Number): Promise<void> {
      return axios({
        method: "get",
        url: `${USER_LOAN_DETAIL_API_URL}/${loanId}`,
      })
        .then((res) => {
          loan.value = res.data
        })
        .catch((err) => {
          loan.value = null
          console.error(err)
        })
    }

    // User 대출 로그 조회
    const getUserLoanLogs = function (loanId: Number): Promise<void> {
      return axios({
        method: "get",
        url: `${USER_LOAN_LOG_API_URL}/${loanId}`,
      })
        .then((res) => {
          let logsArray: LoanLog[] = []
          res.data.forEach((log: LoanLog) => {
            logsArray.push(log)
          })
          loanLogs.value = logsArray
          console.log(loanLogs.value)
        })
        .catch((err) => {
          console.error(err)
        })
    }

    // User 적금 납입
    const sendSavings = function (userId: Number): Promise<void> {
      return axios({
        method: "post",
        url: `${USER_SAVINGS_SEND_API_URL}/${userId}`,
      })
        .then((res) => {})
        .catch((err) => {
          console.error(err)
        })
    }

    // User 대출 상환
    const sendLoan = function (loanId: Number): Promise<void> {
      return axios({
        method: "post",
        url: `${USER_LOAN_SEND_API_URL}/${loanId}`,
      })
        .then((res) => {})
        .catch((err) => {
          console.error(err)
        })
    }

    // User 적금 해약
    const deleteSavings = function (savingsId: Number): Promise<void> {
      return axios({
        method: "post",
        url: `${DELETE_SAVINGS_API_URL}/${savingsId}`,
      })
        .then((res) => {})
        .catch((err) => {
          console.error(err)
        })
    }

    // User 예금 해약
    const deleteDeposit = function (depositId: Number): Promise<void> {
      return axios({
        method: "post",
        url: `${DELETE_DEPOSIT_API_URL}/${depositId}`,
      })
        .then((res) => {})
        .catch((err) => {
          console.error(err)
        })
    }

    return {
      depositProducts,
      getDepositProduct,
      savingsProducts,
      getSavingsProduct,
      getUserSavings,
      savings,
      savingsLogs,
      sendSavings,
      getUserSavingsLogs,
      getUserDeposit,
      deposit,
      loan,
      getUserLoan,
      loanLogs,
      getUserLoanLogs,
      getUserLoanList,
      loanList,
      isYellowPage,
      sendLoan,
      deleteDeposit,
      deleteSavings,
    }
  }
  // {
  //   persist: {
  //     enabled: true, // 상태 지속 활성화
  //     strategies: [
  //       {
  //         key: "finStore",
  //         storage: sessionStorage,
  //       },
  //     ],
  //   },
  // }
)
