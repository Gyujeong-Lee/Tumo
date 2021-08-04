<template>
  <div v-if="resultExist">
    <h3>피드 검색 결과</h3>
    <div class="d-flex flex-column my-autoss">
      <v-card elevation="2" shaped v-for="(feed, idx) in feeds" :key="idx" class="mb-3">
        <v-card-title>{{ feed.title }}</v-card-title>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h3>피드 검색 결과가 없습니다...</h3>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'FeedSearch',
  data: function () {
    return {
      searchWord: this.searchItem,
      resultExist: true,
      feeds: [
        {
          "board_idx" : "1",
          "title" : "이 종목 어때요?",
          "content" : "추매?",
          "tag" : "#종목추천"
        },
        {
          "board_idx" : "2",
          "title" : "이 종목 어때요2?",
          "content" : "추매2?",
          "tag" : "#종목추천2"
        },
      ],
    }
  },
  props: {
    searchItem: String,
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/feed/search/${this.searchWord}/1`,
    })
    .then(res => {
      // console.log(res)
      if (res.request.status === 204) {
        console.log('자료 x')
      } else {
        this.resultExist = true
        // 응답 담기
        // this.feeds = res.data.feed
      }
    })
    .catch(err => {
      console.log(err)
    })
  },

}
</script>