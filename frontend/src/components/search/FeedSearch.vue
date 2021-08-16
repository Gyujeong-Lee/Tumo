<template>
  <div v-if="feeds.length">
    <h4>피드 검색 결과</h4>
    <div class="d-grid flex-column flex-sm-row  my-auto">
      <v-card :elevation="elevation" shaped v-for="(feed, idx) in feeds" :key="idx" class="mb-3 mt-3" @click="moveToDetail(feed)">
        <v-card-title>{{ feed.title }}</v-card-title>
        <v-card-subtitle>{{ feed.nickname }}</v-card-subtitle>
        <v-card-text v-html="feed.content"></v-card-text>
        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
        </v-btn>
        <span>: {{ feed.likes }}</span>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h4 style="color:#CE1D28">피드 검색 결과가 없습니다...</h4>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'FeedSearch',
  data: function () {
    return {
      elevation: 4,
      searchWord: this.searchItem,
      resultExist: false,
      pageNum: 0,
      feeds: [
      ],
    }
  },
  props: {
    searchItem: String,
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/api/feed/search/${this.searchWord}/${this.pageNum}`,
    })
    .then(res => {
      // console.log(res)
      if (res.status == 200) {
        this.resultExist = true
        // 응답 담기
        this.feeds = res.data.feedList
      }
    })
    .catch(err => {
      console.log(err)
    })
  },
  methods: {
    moveToDetail: function (feed) {
      console.log(feed)
      axios({
        method: 'GET',
        url: `/api/article/${feed.boardIdx}/${feed.userIdx}`
      })
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
        this.$router.push({ name: 'articleDetail', params: { userIdx: res.data.feed.userIdx ,boardIdx: this.boardIdx }})
      })
    },
  }
}
</script>