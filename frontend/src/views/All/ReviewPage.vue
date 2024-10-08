<template>
    <div>
      <h1>오답 리뷰</h1>
      <div v-for="(question, index) in incorrectAnswers" :key="index">
        <h2>문제 {{ index + 1 }}</h2>
        <p>{{ question.content }}</p>
        <p>정답: {{ question.answer }}</p>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from "vue";
  import { useRoute } from "vue-router";
  
  const route = useRoute();
  const incorrectAnswers = ref([]);
  
  onMounted(() => {
    // Get the incorrect answers from the route query
    const answers = route.query.incorrectAnswers;
    if (answers) {
      incorrectAnswers.value = JSON.parse(answers);
    }
  });
  </script>
  