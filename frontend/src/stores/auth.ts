import { defineStore } from "pinia"
import { openDB } from "idb"
import axios from "axios"

// IndexedDB 설정
// async function saveTokensToIndexedDB(accessToken: string, refreshToken: string) {
//   const db = await openDB("authDB", 1, {
//     upgrade(db) {
//       db.createObjectStore("tokenStore")
//     },
//   })
//   await db.put("tokenStore", { accessToken, refreshToken }, "authTokens")
//   console.log(accessToken,refreshToken,db)
// }

async function saveTokensToIndexedDB(accessToken: string, refreshToken: string) {
  const db = await openDB("authDB", 2, {
    upgrade(db) {
      // 'tokenStore'가 존재하지 않으면 새로 생성
      if (!db.objectStoreNames.contains("tokenStore")) {
        db.createObjectStore("tokenStore")
      }
    },
  })
  await db.put("tokenStore", { accessToken, refreshToken }, "authTokens")
}

async function loadTokensFromIndexedDB() {
  const db = await openDB("authDB", 2, {
    upgrade(db) {
      // 'tokenStore'가 존재하지 않으면 새로 생성
      if (!db.objectStoreNames.contains("tokenStore")) {
        db.createObjectStore("tokenStore")
      }
    },
  })
  const tokens = await db.get("tokenStore", "authTokens")
  return tokens
}

async function clearTokensFromIndexedDB() {
  const db = await openDB("authDB", 2)
  if (!db.objectStoreNames.contains("tokenStore")) {
    console.error("tokenStore 객체 저장소가 존재하지 않습니다.")
    return null
  }
  await db.delete("tokenStore", "authTokens")
}

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: null as string | null,
    refreshToken: null as string | null,
  }),
  actions: {
    async setTokens(accessToken: string, refreshToken: string) {
      this.accessToken = accessToken
      this.refreshToken = refreshToken
      // console.log(accessToken, refreshToken)
      await saveTokensToIndexedDB(accessToken, refreshToken)
    },
    async loadTokens(router: any) {
      const tokens = await loadTokensFromIndexedDB()
      const currentRoute = router.currentRoute.value.path
      // if (!tokens) {
      //   if (currentRoute === '/login' || currentRoute === '/main' ) {
      //     // /login 페이지에서는 토큰 검사를 하지 않음
      //     return
      //   } else {
      //     // 토큰이 없으면 /login 또는 /main으로 리다이렉트
      //     router.push("/login") // 로그인 페이지로 이동
      //   }
      // } else {
      //   // 토큰이 있으면 상태에 저장
      //   this.accessToken = tokens.accessToken
      //   this.refreshToken = tokens.refreshToken
      // }
      if (tokens) {
        this.accessToken = tokens.accessToken
        this.refreshToken = tokens.refreshToken
      }
    },
    async clearToken() {
      this.accessToken = null
      this.refreshToken = null
      await clearTokensFromIndexedDB()
    },
    async logout() {
      if (this.accessToken) {
        try {
          // await axios.post("/api/kakao/logout",{},{
          //   headers:{
          //     Authorization:`Bearer ${this.accessToken}`,
          //   }
          // })
          window.location.href = "/api/kakao/logout"
          await this.clearToken()
          console.log("로그아웃 성공")
        } catch (error) {
          console.error("로그아웃 실패:", error)
        }
      }
    },
  },
})
