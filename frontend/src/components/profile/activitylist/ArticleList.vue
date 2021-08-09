<template>
  <v-sheet 
    elevation="5"
    rounded="xl"
    class="mx-2 my-5"
    height="auto"
    width="auto">
    <p class="p-3" type="button" @click="moveToDetail">{{ myArticle.title }}</p>
  </v-sheet>
</template>

<script>
import axios from 'axios'
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
      axios({
        method: 'GET',
        url: `/api/article/${this.myArticle.boardIdx}/${this.myArticle.userIdx}`
      })
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
        this.$router.push({name: 'articleDetail', params: {boardIdx: this.myArticle.boardIdx}})
      })
    }
  },
}
</script>