import { ref } from "vue"
import { defineStore } from "pinia"
import axios from "axios"

// User 인터페이스
export interface User {
  userId: number
  name: string
}

// FamilyMember 인터페이스 정의
export interface FamilyMember {
  userId: number
  email: string
  realAccount: string
  name: string
  role: string // 부모, 자녀 등의 역할
  bank: string
  pwd: string
  stockRatio: number
}

// Family 인터페이스 정의
export interface Family {
  familyId: number
  members: FamilyMember[]
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
  const ASSET_API_URL = "/api/v1/asset"

  const user = ref<User | null>(null)
  const role = ref<string>("")
  const children = ref<User[]>([])
  const familyId = ref<number | null>(null)

  const deposit = ref(null)
  const savings = ref(null)
  const loan = ref(null)
  const userData = { 현재잔액: 135000, 투자가능금액: 35000 }
  // 역할 변경 (테스트용 코드)
  const setRole = (newRole: string): void => {
    role.value = newRole
  }

  // 유저 조회
  const getUser = function (userId: number): Promise<void> {
    return axios({
      method: "get",
      url: `${USER_API_URL}/${userId}`,
    })
      .then((res) => {
        role.value = res.data.role
        user.value = { userId: res.data.userId, name: res.data.name }
        familyId.value = res.data.family.familyId
        if (familyId.value && role.value === "부모") {
          let childrenArray = []
          res.data.family.members.forEach((member: FamilyMember) => {
            if (member.role === "자녀") {
              childrenArray.push({ userId: member.userId, name: member.name })
              children.value = childrenArray
            }
          })
        }
      })
      .catch((err) => {
        console.error(err)
      })
  }

  return { user, role, children, familyId, setRole, getUser, userData }
})
