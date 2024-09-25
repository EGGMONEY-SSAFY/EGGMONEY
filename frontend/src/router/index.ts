import { createRouter, createWebHistory } from "vue-router"
import AllView from "@/views/AllView.vue"
import StockHistoryView from "@/views/Stock/StockHistoryView.vue"
import StockNewsView from "@/views/Stock/StockNewsView.vue"
import WonAuthView from "@/views/WonAuthView.vue"
import StockDetail from "@/views/Stock/StockDetail.vue"
import MainView from "@/views/MainView.vue"
import LoginView from "@/views/LoginView.vue"
import FamilyInviteComponent from "@/components/family/FamilyInviteComponent.vue"
import FamilyConnectionComponent from "@/components/family/FamilyConnectionComponent.vue"
import FamilyComponent from "@/components/family/FamilyComponent.vue"
import FamilyView from "@/views/FamilyView.vue"
import FamilyManageComponent from "@/components/family/FamilyManageComponent.vue"
import AssetsView from "@/views/Assets/AssetsView.vue"
import FinView from "@/views/Fin/FinView.vue"
import StockView from "@/views/Stock/StockView.vue"
import FinSavingsView from "@/views/Fin/FinSavingsView.vue"
import FinDepositView from "@/views/Fin/FinDepositView.vue"
import FinSavingsCreateView from "@/views/Fin/FinSavingsCreateView.vue"
import FinDepositCreateView from "@/views/Fin/FinDepositCreateView.vue"
import FinDepositCreateDetailView from "@/views/Fin/FinDepositCreateDetailView.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
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
      name: "FinView",
      component: FinView,
      children: [
        {
          path: "",
          name: "FinDepositView",
          component: FinDepositView,
        },
        {
          path: "loan",
          name: "FinLoanView",
          component: FinView,
        },
        {
          path: "savings",
          name: "FinSavingsView",
          component: FinSavingsView,
        },
        {
          path: "deposit/create",
          name: "FinDepositCreateView",
          component: FinDepositCreateView,
        },
        {
          path: "deposit/create/detail",
          name: "FinDepositCreateDetailView",
          component: FinDepositCreateDetailView,
        },
        {
          path: "savings/create",
          name: "FinSavingsCreateView",
          component: FinSavingsCreateView,
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
      ],
    },
  ],
})

export default router
