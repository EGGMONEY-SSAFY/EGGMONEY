import { createRouter, createWebHistory } from "vue-router"
import AssetView from "@/views/Asset/AssetView.vue"
import AllView from "@/views/All/AllView.vue"
import FinView from "@/views/Fin/FinView.vue"
import StockView from "@/views/Stock/StockView.vue"
import StockHistoryView from "@/views/Stock/StockHistoryView.vue"
import StockNewsView from "@/views/Stock/StockNewsView.vue"
import WonAuthView from "@/views/All/WonAuthView.vue"
import StockDetail from "@/views/Stock/StockDetail.vue"
import MainView from "@/views/MainView.vue"
import LoginView from "@/views/LoginView.vue"

import FamilyInviteComponent from "@/components/family/FamilyInviteComponent.vue"
import FamilyConnectionComponent from "@/components/family/FamilyConnectionComponent.vue"
import FamilyComponent from "@/components/family/FamilyComponent.vue"
import FamilyView from "@/views/All/FamilyView.vue"
import FamilyManageComponent from "@/components/family/FamilyManageComponent.vue"
import PinPadComponent from "@/components/login/PinPadComponent.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      redirect: "/asset",
    },
    {
      path: "/main",
      name: "MainView",
      component: MainView,
    },
    {
      path: "/login",
      name: "LoginView",
      component: LoginView,
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
    {
      path: "/family",
      name: "FamilyView",
      component: FamilyView,
      children: [
        {
          path: "",
          name: "FamilyCom",
          component: FamilyComponent,
        },

        {
          path: "my-family",
          name: "MyFamilyView",
          component: FamilyManageComponent,
        },
        {
          path: "family-invite",
          name: "FamilyInviteView",
          component: FamilyInviteComponent,
        },
        {
          path: "family-connection",
          name: "FamilyConnectionView",
          component: FamilyConnectionComponent,
        },
        {
          path:"/pinpad",
          name:"pinpadView",
          component:PinPadComponent,
        }
      ]
    }
  ],
})

export default router
