import { ref } from "vue"
import { defineStore } from "pinia"
import axios from "axios"

export const useUserStore = defineStore("user", () => { 
  const USER_API_URL = '/api/v1/profile'

  interface User {
    userId: number,
    name: string
  }

  const user = ref<User | null>(null)
  const role = ref("")
  const children = ref<User[]>([])
  const familyId = ref<Number | null>(null)

  const deposit = ref(null)
  const savings = ref(null)
  const loan = ref(null)


//  역할 변경 ( 테스트용 코드 )
  const setRole = (newRole: string) => {
    role.value = newRole
  }

//  유저 조회 
const getUser = function (userId:number) : Promise<void> {
  return axios({
    method: 'get',
    url: `${USER_API_URL}/${userId}`
  })
    .then((res) => {
      role.value = res.data.role
      user.value = {'userId' : res.data.userId, 'name' : res.data.name}
      familyId.value = res.data.family.familyId

      console.log(familyId)

      if ( familyId.value && role.value === '부모' ) {
        res.data.members.forEach ( member => {
          if ( member.role === '자녀' ) {
            children.value.push({'userId':member.userId, 'name':member.name})
          }
        })
      }
    })
    .catch((err) => {
      console.error(err)
    })
}
  

  return { user, role, children, familyId,
    setRole, getUser
   }
})
