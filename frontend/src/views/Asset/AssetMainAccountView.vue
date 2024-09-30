<script setup lang="ts">
import BoxAccountLogs from "@/components/box/BoxAccountLogs.vue"
import BoxMainAccount from "@/components/box/BoxMainAccount.vue"
import { useAssetStore } from "@/stores/asset"
import { useVariableStore } from "@/stores/variable"
import { onMounted, ref, watch, computed } from "vue"
import type { TradeData } from "@/stores/asset"
import type { User } from "@/stores/user"
const varStore = useVariableStore()

const assetStore = useAssetStore()
const balance = ref<Number | null>(0)
const accountHistory = ref<TradeData[]>([])

// Pagination 관련 상태값
const currentPage = ref<number>(1) // 현재 페이지
const totalPages = ref<number>(1) // 전체 페이지 수
const pageWindowSize = ref<number>(5) // 한 번에 보여줄 페이지 수
const pageRangeStart = ref<number>(1) // 현재 페이지 범위의 시작
const pageRangeEnd = computed(() =>
  Math.min(pageRangeStart.value + pageWindowSize.value - 1, totalPages.value)
) // 현재 페이지 범위의 끝

const props = defineProps<{ user: User }>()

// 페이지 번호가 변경되면 로그 다시 가져오기
watch(
  () => currentPage.value,
  async (newPage) => {
    if (props.user) {
      await fetchAccountLogs(newPage)
    }
  }
)

watch(
  () => props.user,
  async (newUser) => {
    if (newUser) {
      await assetStore.getPort(newUser.userId)
      balance.value = assetStore.mainAccount
      await fetchAccountLogs(1)
    }
  },
  { deep: true }
)

// 페이지 이동 함수
const goToPage = async (page: number) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page // 페이지 변경
    adjustPageRange(page) // 페이지 범위 조정
    await fetchAccountLogs(page) // 해당 페이지 데이터 불러오기
  }
}

// 페이지 범위를 조정하는 함수
const adjustPageRange = (page: number) => {
  if (page > pageRangeEnd.value) {
    // 현재 페이지가 범위 끝을 넘어가면 범위를 앞으로 이동
    pageRangeStart.value = page - pageWindowSize.value + 1
  } else if (page < pageRangeStart.value) {
    // 현재 페이지가 범위 시작보다 작으면 범위를 뒤로 이동
    pageRangeStart.value = page
  }
}

// 첫 페이지로 이동
const moveToFirstPage = () => {
  currentPage.value = 1
  pageRangeStart.value = 1 // 첫 페이지 그룹으로 이동
}

// 마지막 페이지로 이동
const moveToLastPage = () => {
  currentPage.value = totalPages.value
  pageRangeStart.value = Math.max(totalPages.value - pageWindowSize.value + 1, 1) // 마지막 페이지 그룹으로 이동
}

// 다음 페이지로 이동
const moveToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    goToPage(currentPage.value + 1)
  }
}

// 이전 페이지로 이동
const moveToPrevPage = () => {
  if (currentPage.value > 1) {
    goToPage(currentPage.value - 1)
  }
}

// 페이지 범위를 오른쪽(다음)으로 이동
const movePageWindowRight = () => {
  if (pageRangeEnd.value < totalPages.value) {
    pageRangeStart.value++
  }
}

// 페이지 범위를 왼쪽(이전)으로 이동
const movePageWindowLeft = () => {
  if (pageRangeStart.value > 1) {
    pageRangeStart.value--
  }
}

// 로그 가져오기 함수 (페이지 번호 반영)
const fetchAccountLogs = async (page: number) => {
  await assetStore.getAccountLog(props.user.userId, page) // 페이지 번호 전달
  accountHistory.value = assetStore.logs
  totalPages.value = assetStore.mainAccountPages // 백엔드에서 받은 총 페이지 수
}

onMounted(async () => {
  varStore.setTitle("계좌 내역")
  await assetStore.getPort(props.user.userId)
  balance.value = assetStore.mainAccount
  await fetchAccountLogs(1) // 첫 페이지부터 시작
})
</script>
<template>
  <div>
    <BoxMainAccount :user="props.user" :balance="balance" />
    <BoxAccountLogs :history="accountHistory" />
    <!-- Pagination Controls -->
    <div class="flex items-center justify-between border-t px-4 py-3 sm:px-6">
      <div class="flex sm:flex-1 sm:items-center sm:justify-between">
        <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
          <!-- << 버튼: 첫 페이지로 이동 -->
          <button
            @click="moveToFirstPage"
            :disabled="currentPage === 1"
            class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500"
          >
            «
          </button>

          <!-- < 버튼: 이전 페이지로 이동 -->
          <button
            @click="moveToPrevPage"
            :disabled="currentPage === 1"
            class="relative inline-flex items-center px-2 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-500"
          >
            <
          </button>

          <!-- 페이지 번호 렌더링 -->
          <template
            v-for="page in pageRangeEnd - pageRangeStart + 1"
            :key="page + pageRangeStart - 1"
          >
            <button
              @click="goToPage(page + pageRangeStart - 1)"
              :class="{
                'bg-blue-600 text-white': currentPage === page + pageRangeStart - 1,
                'bg-white text-gray-700': currentPage !== page + pageRangeStart - 1,
              }"
              class="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium"
            >
              {{ page + pageRangeStart - 1 }}
            </button>
          </template>

          <!-- > 버튼: 다음 페이지로 이동 -->
          <button
            @click="moveToNextPage"
            :disabled="currentPage === totalPages"
            class="relative inline-flex items-center px-2 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-500"
          >
            >
          </button>

          <!-- >> 버튼: 마지막 페이지로 이동 -->
          <button
            @click="moveToLastPage"
            :disabled="currentPage === totalPages"
            class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500"
          >
            »
          </button>
        </nav>
      </div>
    </div>
  </div>
</template>
