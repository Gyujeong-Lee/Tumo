<template>
  <div v-if="feeds.length">
    <h4>피드 검색 결과</h4>
    <div class="d-flex flex-column my-autoss">
      <v-card elevation="2" shaped v-for="(feed, idx) in feeds" :key="idx" class="mb-3">
        <v-card-title>{{ feed.title }}</v-card-title>
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
      searchWord: this.searchItem,
      resultExist: false,
      pageNum: 1,
      feeds: [
    {
      "boardIdx": 18,
      "userIdx": 24,
      "nickname": "안동준",
      "title": "asdfaasef",
      "stock": "국내주식",
      "content": "<h1>123123</h1>",
      "likes": 0,
      "tags": []
    },
    {
      "boardIdx": 17,
      "userIdx": 24,
      "nickname": "안동준",
      "title": "123",
      "stock": "국내주식",
      "content": "<p>ㅁㄴㅇㄹㄴㅇㄹㅈㄷ</p>",
      "likes": 0,
      "tags": []
    },
    {
      "boardIdx": 12,
      "userIdx": 24,
      "nickname": "안동준",
      "title": "안녕하세요",
      "stock": "국내주식",
      "content": "<p>123</p>",
      "likes": 0,
      "tags": []
    },
    {
      "boardIdx": 10,
      "userIdx": 24,
      "nickname": "안동준",
      "title": "123",
      "stock": "국내주식",
      "content": "<p>123123123</p>",
      "likes": 0,
      "tags": []
    },
    {
      "boardIdx": 5,
      "userIdx": 24,
      "nickname": "안동준",
      "title": "123",
      "stock": "국내주식",
      "content": "<p>123</p>",
      "likes": 0,
      "tags": []
    }
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
      console.log(res)
      if (res.request.status === 204) {
        console.log('자료 x')
      } else {
        this.resultExist = true
        // 응답 담기
        this.feeds = res.data.feedList
      }
    })
    .catch(err => {
      console.log(err)
    })
  },
}
</script>