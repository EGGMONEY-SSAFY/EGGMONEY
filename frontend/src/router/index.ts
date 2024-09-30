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
import StockNewsDetailView from "@/views/Stock/StockNewsDetailView.vue"
import PocketMoneyView from "@/views/All/PocketMoneyView.vue"
import FinDepositView from "@/views/Fin/FinDepositView.vue"
import FinSavingsCreateView from "@/views/Fin/FinSavingsCreateView.vue"
import FinSavingsView from "@/views/Fin/FinSavingsView.vue"
import FinDepositCreateDetailView from "@/views/Fin/FinDepositCreateDetailView.vue"
import FinDepositCreateView from "@/views/Fin/FinDepositCreateView.vue"
import AssetMainAccountView from "@/views/Asset/AssetMainAccountView.vue"
import AssetMainView from "@/views/Asset/AssetMainView.vue"
import AssetSavingsView from "@/views/Asset/AssetSavingsView.vue"
import AssetDepositView from "@/views/Asset/AssetDepositView.vue"
import AssetLoanView from "@/views/Asset/AssetLoanView.vue"
import AssetLoanDetailView from "@/views/Asset/AssetLoanDetailView.vue"
import AssetLoanListView from "@/views/Asset/AssetLoanListView.vue"
import FinSavingsCreateDetailView from "@/views/Fin/FinSavingsCreateDetailView.vue"
import EditProfileView from "@/views/All/EditProfileView.vue"
import FinLoanView from "@/views/Fin/FinLoanView.vue"
import FinLoanCreateView from "@/views/Fin/FinLoanCreateView.vue"
import ExInfoView from "@/views/All/ExInfoView.vue"
import AssetDepositDetailView from "@/views/Asset/AssetDepositDetailView.vue"
import AssetSavingsDetailView from "@/views/Asset/AssetSavingsDetailView.vue"
import StockOrderListView from "@/views/Stock/StockOrderListView.vue"
import AssetWithdrawalView from "@/views/Asset/AssetWithdrawalView.vue"
import NotFoundComponent from "@/components/404/NotFoundComponent.vue"
import StockRateView from "@/views/All/StockRateView.vue"

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
      beforeEnter() {
        console.log("MainView 로드됨")
      },
    },
    {
      path: "/stock/order-list",
      name: "StockOrderListView",
      component: StockOrderListView,
    },
    {
      path: "/stock/news/:newsId",
      name: "StockNewsDetailView",
      component: StockNewsDetailView,
    },
    {
      path: "/login",
      name: "LoginView",
      component: LoginView,
    },
    {
      path: "/asset",
      // name: "AssetView",
      component: AssetView,
      children: [
        {
          path: "",
          name: "AssetMainView",
          component: AssetMainView,
        },
        {
          path: "main-account",
          name: "AssetMainAccountView",
          component: AssetMainAccountView,
        },
        {
          path: "savings",
          name: "AssetSavingsView",
          component: AssetSavingsView,
        },
        {
          path: "deposit",
          name: "AssetDepositView",
          component: AssetDepositView,
        },
        {
          path: "loan",
          name: "AssetLoanListView",
          component: AssetLoanListView,
        },
        {
          path: "withdrawal/judge",
          name: "AssetWithdrawalView",
          component: AssetWithdrawalView,
        },
      ],
    },
    {
      path: "/asset/deposit/:userId",
      name: "AssetDepositDetailView",
      component: AssetDepositDetailView,
    },
    {
      path: "/asset/savings/:userId",
      name: "AssetSavingsDetailView",
      component: AssetSavingsDetailView,
    },
    {
      path: "/asset/loan/:loanId",
      name: "AssetLoanListItem",
      component: AssetLoanView,
    },
    {
      path: "/asset/loan/:loanId/detail",
      name: "AssetLoanListItemDetail",
      component: AssetLoanDetailView,
    },
    {
      path: "/all",
      name: "AllView",
      component: AllView,
      beforeEnter() {
        console.log("AllView 로드됨")
      },
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
          component: FinLoanView,
        },
        {
          path: "loan/create",
          name: "FinLoanCreateView",
          component: FinLoanCreateView,
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
      path: "/stock/home",
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
      path: "/stock/detail/:stock",
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
      ],
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
    {
      path: "/editProfile",
      name: "EditProfileView",
      component: EditProfileView,
    },
    {
      path: "/ExInfo",
      name: "ExInfoView",
      component: ExInfoView,
    },
    {
      path: "/StockRate",
      name: "StockRateView",
      component: StockRateView,
    },
    // 최하단 배치 필요
    { 
      path: "/:pathMatch(.*)*",
      name: "NotFound",
      component: NotFoundComponent, // 404 페이지 컴포넌트
      props: true,
    },
  ],
})

export default router
