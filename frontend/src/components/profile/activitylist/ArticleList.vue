<template>
  <v-sheet 
    elevation="5"
    rounded="xl"
    class="mx-2 my-5"
    height="auto"
    width="auto">
    <p class="p-3" @click="moveToDetail">{{ myArticle.title }}</p>
  </v-sheet>
</template>

<script>

export default {
  name: 'ArticleList',
  data: function () {
      return {
        myArticle: this.article
      }
  },
  props: {
    article: {
      type: Object
    }
  },
  methods: {
    moveToDetail: function () {
      this.$router.push({name: 'articleDetail', params: {boardIdx: this.myArticle.boardIdx}})
    }
  }
}
</script>