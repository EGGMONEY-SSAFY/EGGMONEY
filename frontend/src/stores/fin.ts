import { defineStore } from "pinia"
import axios from "axios"
import { reactive, ref } from "vue"

export const useFinStore = defineStore("fin", () => {
  const DEPOSIT_PRODUCT_API_URL = "/api/v1/fin/deposit/product"

  interface Products {
    productId: number
    productName: string
    depositRate: number
    depositDate: number
  }

  const products = reactive<Products[]>([])
  const getDepositProduct = function () {
    axios({
      method: "GET",
      url: `${DEPOSIT_PRODUCT_API_URL}`,
    })
      .then((res) => {
        products.join(res.data)
        console.log(res.data)
      })
      .catch((err) => {
        console.error(err)
      })
  }

  return { products, getDepositProduct }
})
