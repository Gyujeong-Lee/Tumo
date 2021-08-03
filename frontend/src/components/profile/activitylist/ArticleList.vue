<template>
  <v-sheet 
    elevation="5"
    rounded="xl"
    class="mx-2 my-5"
    height="auto"
    width="auto">
    <p class="p-3">{{ article.title }}</p>
  </v-sheet>
</template>

<script>

export default {
  name: 'ArticleList',
  data: function () {
      return {

      }
  },
  props: {
    article: {
      type: Object
    }
  }
}
</script>