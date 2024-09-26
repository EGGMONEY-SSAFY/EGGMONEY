import { createRouter, createWebHistory } from "vue-router"
import AssetView from "@/views/Asset/AssetView.vue"
import AllView from "@/views/All/AllView.vue"
import FinView from "@/views/Fin/FinView.vue"
import StockView from "@/views/Stock/StockView.vue"
import StockHistoryView from "@/views/Stock/StockHistoryView.vue"
import StockNewsView from "@/views/Stock/StockNewsView.vue"
import WonAuthView from "@/views/All/WonAuthView.vue"
import StockDetail from "@/views/Stock/StockDetail.vue"
import MainView from "@/views/All/MainView.vue"
import LoginView from "@/views/All/LoginView.vue"
import FamilyInviteComponent from "@/components/family/FamilyInviteComponent.vue"
import FamilyConnectionComponent from "@/components/family/FamilyConnectionComponent.vue"
import FamilyComponent from "@/components/family/FamilyComponent.vue"
import FamilyView from "@/views/All/FamilyView.vue"
import FamilyManageComponent from "@/components/family/FamilyManageComponent.vue"
import PinPadComponent from "@/components/login/PinPadComponent.vue"
import CreateFamilySuccess from "@/components/family/complete/CreateFamilySuccess.vue"
import ConnectionFamilySuccess from "@/components/family/complete/ConnectionFamilySuccess.vue"
import PocketMoneyView from "@/views/All/PocketMoneyView.vue"
import FinDepositView from "@/views/Fin/FinDepositView.vue"
import FinSavingsCreateView from "@/views/Fin/FinSavingsCreateView.vue"
import FinSavingsView from "@/views/Fin/FinSavingsView.vue"
import FinDepositCreateDetailView from "@/views/Fin/FinDepositCreateDetailView.vue"
import FinDepositCreateView from "@/views/Fin/FinDepositCreateView.vue"
import FinSavingsCreateDetailView from "@/views/Fin/FinSavingsCreateDetailView.vue"

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
      name: "AssetsView",
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
        {
          path: "savings/create/detail",
          name: "FinSavingsCreateDetailView",
          component: FinSavingsCreateDetailView,
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
          path: "create",
          name: "FamilyCreate",
          component: CreateFamilySuccess,
        },
        {
          path: "",
          name: "FamilyCom",
          component: FamilyComponent,
          children: [],
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
          children: [],
        },
        {
          path: "/family/family-connection/success",
          name: "FamilyConnectSuccess",
          component: ConnectionFamilySuccess,
        },
        {
          path: "/pinpad",
          name: "pinpadView",
          component: PinPadComponent,
        },
        {
          path: "/pocketmoney",
          name: "PocketMoneyView",
          component: PocketMoneyView,
        },
      ],
    },
  ],
})

export default router
