import axios from "axios"
import { defineStore } from "pinia"
import { ref } from "vue"
import { useAuthStore } from "./auth"

export const useStockStore = defineStore("stock", () => {
  const API_URL = "/api/v1"
  const authStore = useAuthStore()
  const token = authStore.accessToken
  const totalStockValue = ref(0)
  const setTotalStockValue = (total: number) => {
    totalStockValue.value = total
  }

  const postPendingCancel = async (stockPendingId: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "post",
        url: `${API_URL}/stock/pending/${stockPendingId}/cancel`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getDetailStockData = async (stockId: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "get",
        url: `${API_URL}/stock/${stockId}/price/year`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getChartData = async (userId: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "get",
        url: `${API_URL}/stock/user/${userId}/portfolio`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getOrderLog = async () => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "get",
        url: `${API_URL}/stock/pending/log`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const postBuyOrder = async (stockId: number, pendingPrice: number, pendingAmount: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "post",
        url: `${API_URL}/stock/pending/buy`,
        data: {
          stockId,
          pendingPrice,
          pendingAmount,
        },
      })

      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const postSellOrder = async (stockId: number, pendingPrice: number, pendingAmount: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "post",
        url: `${API_URL}/stock/pending/sell`,
        data: {
          stockId,
          pendingPrice,
          pendingAmount,
        },
      })

      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const postSellCurrent = async (stockId: number, amount: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "post",
        url: `${API_URL}/stock/user/sell`,
        data: {
          stockId,
          amount,
        },
      })

      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const postBuyCurrent = async (stockId: number, amount: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "post",
        url: `${API_URL}/stock/user/buy`,
        data: {
          stockId,
          amount,
        },
      })

      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getMyStockLog = async () => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "get",
        url: `${API_URL}/stock/user/log`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getMyStockInfo = async (stockId: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "get",
        url: `${API_URL}/stock/${stockId}/user/info`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getMyStock = async (userId: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "get",
        url: `${API_URL}/stock/user/${userId}/portfolio`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getMoneyInfo = async (userId: number) => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        method: "get",
        url: `${API_URL}/stock/user/${userId}/available-balance`,
      })
      console.log(response.data)

      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getStockPrice = async () => {
    try {
      const response = await axios({
        method: "get",
        url: `${API_URL}/stock/price`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getNews = async () => {
    try {
      const response = await axios({
        method: "get",
        url: `${API_URL}/news`,
      })
      return response.data // 성공적으로 데이터를 가져오면 news 상태 업데이트
    } catch (error) {
      console.error("Failed to fetch news:", error) // 에러 핸들링
    }
  }

  const getArticle = async (id: string) => {
    try {
      const respose = await axios({
        method: "get",
        url: `${API_URL}/news/${id}`,
      })
      return respose.data
    } catch (error) {
      console.error("Failed to fetch news:", error) // 에러 핸들링
    }
  }
  return {
    getNews,
    getArticle,
    getStockPrice,
    getMoneyInfo,
    getMyStock,
    getMyStockInfo,
    getMyStockLog,
    postBuyCurrent,
    postSellCurrent,
    postBuyOrder,
    postSellOrder,
    getOrderLog,
    getChartData,
    getDetailStockData,
    postPendingCancel,
    totalStockValue,
    setTotalStockValue,
  }
})
