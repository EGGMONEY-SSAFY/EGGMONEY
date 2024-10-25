import { ref } from "vue"
import { defineStore } from "pinia"

export const useVariableStore = defineStore("variable", () => {
  const title = ref("홈")
  const setTitle = (newTitle: string) => {
    title.value = newTitle
  }

  const balance = ref(0)
  const setBalance = (newBalance: number) => {
    balance.value = newBalance
  }
  return { title, setTitle, balance, setBalance }
})
