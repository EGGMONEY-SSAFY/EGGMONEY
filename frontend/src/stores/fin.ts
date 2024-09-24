import { defineStore } from "pinia"
import axios from "axios"
import { reactive } from "vue"

export interface depositProducts {
  productId: number
  productName: string
  depositRate: number
  depositDate: number
}
export interface savingsProducts {
  productId: number
  productName: string
  savingsRate: number
  savingsDate: number
  maxPrice: number
}
export const useFinStore = defineStore("fin", () => {
  const DEPOSIT_PRODUCT_API_URL = "/api/v1/fin/deposit/product"
  const SAVINGS_PRODUCT_API_URL = "/api/v1/fin/savings/product"

  const depositProducts = reactive<depositProducts[]>([])
  const savingsProducts = reactive<savingsProducts[]>([])
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

  return { depositProducts, getDepositProduct, savingsProducts, getSavingsProduct }
})
