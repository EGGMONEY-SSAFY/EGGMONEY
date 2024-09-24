<template >
    <div class="flex flex-col items-center justify-center bg-gray-100 min-h-screen">
    <!-- 가족 이미지 섹션 -->
    <div class="bg-white p-6 rounded-lg shadow-lg w-full max-w-xl mb-6">
      <img :src="familyImageUrl|| require('../../assets/common/가족 기본이미지.png')" alt="가족 기본이미지" class="w-full h-48 object-cover mb-4" />
      <div class="flex justify-between items-center">
        <h1 class="text-lg font-bold">가훈</h1>
        <button class="px-4 py-2 bg-orange-500 text-white rounded-lg">가족삭제</button>
      </div>
    </div>

    <!-- 구성원 목록 -->
    <div v-for="member in familyMembers" :key="member.id" class="bg-white p-4 rounded-lg shadow-lg w-full max-w-xl mb-4 flex items-center">
      <img :src="member.profileImageUrl" alt="프로필 이미지" class="w-16 h-16 rounded-full object-cover mr-4" />
      <div class="flex-1">
        <h2 class="text-lg font-bold">{{ member.name }}</h2>
        <p class="text-sm text-gray-500">구성원 역할: {{ member.role }}</p>
      </div>
      <button @click="deleteMember(member.id)" class="px-4 py-2 bg-red-500 text-white rounded-lg">삭제</button>
    </div>
    
    
  </div>
</template>
<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';

interface FamilyMembers{
    id:number;
    name:string;
    role:string;
    profileImageUrl:string;
}

const familyImageUrl=ref<string>('');
const familyMembers=ref<FamilyMembers[]>([]);

const fetchFamilyData= async()=>{
    try{
        const familyImageResponse = await axios.get('http://localhost:8080/api/family/image');
        familyImageUrl.value = familyImageResponse.data.imageUrl;
    
        const familyMembersResponse = await axios.get('http://localhost:8080/api/family/members');
        familyMembers.value= familyMembersResponse.data.members;
    }catch(error){
        console.error('가족 정보 업데이트 실패', error);
    }
};

const deleteMember = async(memberId:number)=>{
    try{
        await axios.delete(`http://localhost:8080/api/family/member/${memberId}`);
        fetchFamilyData();
    }catch(error){
        console.error('가족 맴버 삭제 실패',error);
    }
}
onMounted(()=>{
    fetchFamilyData();
})
</script>
<style lang="">
    
</style>