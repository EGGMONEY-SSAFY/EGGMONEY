import { createRouter, createWebHistory } from "vue-router"
import AssetView from "../views/AssetView.vue"
import AllView from "@/views/AllView.vue"
import FinView from "@/views/FinView.vue"
import StockView from "@/views/StockView.vue"
import FamilyView from "@/views/FamilyView.vue"

import MyFamilyComponent from "@/components/family/FamilyInviteComponent.vue"
import FamilyInviteComponent from "@/components/family/FamilyInviteComponent.vue"
import FamilyConnectionComponent from "@/components/family/FamilyConnectionComponent.vue"

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
      path:"/family",
      name:"FamilyView",
      component:FamilyView,
      children:[
        
        {
          path:"/my-family",
          name:"MyFamilyView",
          component:MyFamilyComponent,
        },
        {
          path:"/family-invite",
          name:"FamilyInviteView",
          component:FamilyInviteComponent,
        },
        {
          path:"/family-connection",
          name:"FamilyConnectionView",
          component:FamilyConnectionComponent,
        },
      ]
    }
  ],
})

export default router
