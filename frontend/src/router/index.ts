import { createRouter, createWebHistory } from "vue-router"
import AssetsView from "@/views/Assets/AssetsView.vue"
import AllView from "@/views/All/AllView.vue"
import FinView from "@/views/Fin/FinView.vue"
import StockView from "@/views/Stock/StockView.vue"
import StockHistoryView from "@/views/Stock/StockHistoryView.vue"
import StockNewsView from "@/views/Stock/StockNewsView.vue"
import WonAuthView from "@/views/WonAuthView.vue"
import StockDetail from "@/views/Stock/StockDetail.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      redirect: "/assets",
    },
    {
      path: "/assets",
      name: "AssetsView",
      component: AssetsView,
    },
    {
      path: "/all",
      name: "AllView",
      component: AllView,
    },
    {
      path: "/fin",
      // name: "FinView",
      component: FinView,
      children: [
        {
          path: "",
          name: "FinDepositView",
          component: FinView,
        },
        {
          path: "loan",
          name: "FinLoanView",
          component: FinView,
        },
        {
          path: "savings",
          name: "FinSavingsView",
          component: FinView,
        },
      ],
    },
    {
      path: "/stock",
      name: "StockView",
      component: StockView,
    },
    {
      path: "/stock/history",
      name: "StockHistoryView",
      component: StockHistoryView,
    },
    {
      path: "/stock/news",
      name: "StockNewsView",
      component: StockNewsView,
    },
    {
      path: "/won",
      name: "WonAuthView",
      component: WonAuthView,
    },
    {
      path: "/stock/:stock",
      name: "StockDetail",
      component: StockDetail,
    },
  ],
})

export default router
