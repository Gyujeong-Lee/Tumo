<template>
  <v-sheet 
  :elevation="elevation"
  rounded
  class="articleFeed mx-2 my-5"
  @mouseover="elevation=10"
  @mouseleave="elevation=4"
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
        myArticle: this.article,
        elevation: 4,
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