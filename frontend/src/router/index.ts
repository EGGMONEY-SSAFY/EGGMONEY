import { createRouter, createWebHistory } from "vue-router"
import AssetView from "../views/AssetView.vue"
import AllView from "@/views/AllView.vue"
import FinView from "@/views/FinView.vue"
import StockView from "@/views/StockView.vue"
import * as path from 'path';
import MainView from "@/views/MainView.vue"
import LoginView from "@/views/LoginView.vue"


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:"/main",
      name: "MainView",
      component:MainView,
    },
    {
      path:"/login",
      name:"LoginView",
      component:LoginView,
    },
    {
      path: "/asset",
      name: "AssetView",
      component: AssetView,
    },
    {
      path: "/all",
      name: "AllView",
      component: AllView,
    },
    {
      path: "/fin",
      name: "FinView",
      component: FinView,
    },
    {
      path: "/stock",
      name: "StockView",
      component: StockView,
    },
    
  ],
})

export default router
