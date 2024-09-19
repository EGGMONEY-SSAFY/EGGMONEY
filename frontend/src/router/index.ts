import { createRouter, createWebHistory } from "vue-router"
import AssetView from "../views/AssetView.vue"
import AllView from "@/views/AllView.vue"
import FinView from "@/views/FinView.vue"
import StockView from "@/views/StockView.vue"
import PinPadView from "@/views/PinPadView.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
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
    {
      path:"/pinpad",
      name:"PinPadView",
      component:PinPadView,
    },
  ],
})

export default router
