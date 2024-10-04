import axios from "axios"
import { defineStore } from "pinia"

export const useStockStore = defineStore("stock", () => {
  const API_URL = "/api/v1"
  const token = "q"
  // const authStore = useAuthStore()
  // authStore.accessToken
  const getMyStock = async () => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "applications/json",
        },
        method: "get",
        url: `${API_URL}/stock/user/portfolio`,
      })
      return response.data
    } catch (error) {
      console.error(error)
    }
  }

  const getMoneyInfo = async () => {
    try {
      const response = await axios({
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "applications/json",
        },
        method: "get",
        url: `${API_URL}/stock/user/available-balance`,
      })
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
  return { getNews, getArticle, getStockPrice, getMoneyInfo, getMyStock }
})
