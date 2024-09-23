import { defineStore } from "pinia";

export const useAuthStore = defineStore('auth',{
    state:()=>({
        accessToken: null as string | null,
        refreshToken: null as string | null,
    }),
    actions:{
        setTokens(accessToken:string, refreshToken:string){
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        },
        clearTokens(){
            this.accessToken = null;
            this.refreshToken = null;
        }
    },
    //persist: true, // persist 플러그인 사용 시
})