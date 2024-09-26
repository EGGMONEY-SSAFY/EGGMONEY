import axios from "axios"
import { defineStore } from "pinia"
import { ref } from "vue"

interface Article {
  id: number
  press: string
  publishDate: string
  title: string
}

export const useStockStore = defineStore(
  "stock",
  () => {
    const API_URL = "/api/v1/"
    const news = ref<Article[] | null>(null)

    const getNews = async () => {
      try {
        const response = await axios({
          method: "get",
          url: `${API_URL}/news`,
        })
        news.value = response.data // 성공적으로 데이터를 가져오면 news 상태 업데이트
      } catch (error) {
        console.error("Failed to fetch news:", error) // 에러 핸들링
      }
    }

    return { news, getNews }
  },
  {
    persist: {
      storage: sessionStorage, // 세션 스토리지 사용
    },
  }
)
