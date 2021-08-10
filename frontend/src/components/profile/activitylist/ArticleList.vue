<template>
  <v-sheet 
  :elevation="elevation"
  rounded
  class="articleFeed mx-2 my-5"
  @mouseover="elevation=10"
  @mouseleave="elevation=4"
  height="auto"
  width="auto">
  <div>
    <p style="font-weight:bold" type="button" @click="moveToDetail">{{ myArticle.title }}</p>
    <p v-html="myArticle.content"></p>
    <p >{{ myArticle.createdAt }}</p>
    <v-btn icon>
      <v-icon>mdi-heart</v-icon>
    </v-btn>
    <span>: {{ myArticle.likes }}</span>
    <v-btn icon>
      <v-icon>mdi-bookmark</v-icon>
    </v-btn>
    <!-- scrap 수가 없음 ㅠ -->
    <span>: {{ myArticle.likes }}</span>
  </div>
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