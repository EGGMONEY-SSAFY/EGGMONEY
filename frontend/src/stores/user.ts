import { ref } from "vue"
import { defineStore } from "pinia"
import axios from "axios"
import { useAuthStore } from "./auth"

// User 인터페이스
export interface User {
  userId: number
  realAccount: string
  name: string
  role: string // 부모, 자녀 등의 역할
  bank: string
  stockRatio: number
}

// Family 인터페이스 정의
export interface Family {
  familyId: number
  members: User[]
}

// 전체 UserResponse 인터페이스 정의
export interface UserResponse {
  userId: number
  name: string
  role: string
  family: Family
}

export const useUserStore = defineStore("user", () => {
  const USER_API_URL = "/api/v1/profile"

  const authStore = useAuthStore()
  const user = ref<User | null>(null)
  const children = ref<User[]>([])
  const familyId = ref<number | null>(null)
  const userData = { 현재잔액: 135000, 투자가능금액: 35000 }

  // 유저 조회
  const getUser = function (): Promise<void> {
    return axios({
      method: "get",
      url: `${USER_API_URL}`,
      headers: {        
        Authorization: `Bearer ${authStore.accessToken}`,
      },
    })
      .then((res) => {
        user.value = {
          userId: res.data.userId,
          name: res.data.name,
          realAccount: res.data.realAccount,
          bank: res.data.bank,
          role: res.data.role,
          stockRatio: res.data.stockRatio,
        }
        familyId.value = res.data.family.familyId
        let childrenArray = <User[]>[]
        if (familyId.value && user.value.role === "부모") {
          res.data.family.members.forEach((member: User) => {
            if (member.role === "자녀") {
              childrenArray.push(member)
            }
          })
          children.value = childrenArray
        }
      })
      .catch((err) => {
        console.error(err)
      })
  }

  return { user, children, familyId, getUser, userData }
})
