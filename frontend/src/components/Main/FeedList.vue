<template>
  <div class="w-100">
    <div class="w-100 pt-5 sticky-top bg-white">
      <button id="newFeedBtn" class="btn" @click="selectNewFeeds">최신 피드</button>
      <button id="portfolioBtn" class="btn" @click="selectPortfolio">포트폴리오</button>
    </div>
    <div v-if="!feedList.length">로딩 아이콘</div>
    <div v-else>
      <div v-if="selectedTab === 'newfeeds'">
        <ArticleFeed v-for="(data, idx) in feedList" :key="idx" :data="data" />
      </div>
      <div v-else-if="selectedTab === 'portfolio'">
        <PortfolioFeed v-for="(data, idx) in feedList" :key="idx" :data="data" />
      </div>
    </div>
  </div>
</template>

<script>
import ArticleFeed from './ArticleFeed'
import PortfolioFeed from './PortfolioFeed'

export default {
  name: 'FeedList',
  data: function () {
    return {
      selectedTab: 'newfeeds',
      feedList: [],
    }
  },
  components: {
    ArticleFeed,
    PortfolioFeed,
  },
  methods: {
    selectNewFeeds: function () {
      this.selectedTab = 'newfeeds'
      const newFeedBtn = document.querySelector('#newFeedBtn')
      const portfolioBtn = document.querySelector('#portfolioBtn')
      newFeedBtn.classList.add('btn-primary')
      portfolioBtn.classList.remove('btn-danger')

      this.feedList = []
      let feedData = this.getNewFeeds()
      this.feedList = feedData
    },
    selectPortfolio: function () {
      this.selectedTab = 'portfolio'
      const newFeedBtn = document.querySelector('#newFeedBtn')
      const portfolioBtn = document.querySelector('#portfolioBtn')
      portfolioBtn.classList.add('btn-danger')
      newFeedBtn.classList.remove('btn-primary')

      this.feedList = []
      let feedData = this.getPortfolioFeeds()
      this.feedList = feedData
    },
    getNewFeeds: function () {
      // axios 요청 보낸후 받은 response.data return
      // data = dump data
      const data = {
        "feed" :[
          {
            "board_idx" : 1,
          "user_idx": 1,
          "title" : "제목입니다",
          "content" : "본문입니다",
          "likes" : 5,
          "islike" : true,
          "isscrap" : false
          },
          {
            "board_idx" : 2,
          "user_idx": 1,
          "title" : "제목입니다",
          "content" : "본문입니다",
          "likes" : 7,
          "islike" : false,
          "isscrap" : true
          },
        ]
      };
      return data.feed
    },
    getPortfolioFeeds: function () {
      // axios 요청 보낸후 받은 response.data return
      // data = dump data
      const data = [
        {
          "user_idx" : 1,
          "portfolio_idx" : 1,
          "title" : "포트폴리오1",
          "contents" : "내용",
          "goal" : 10
        },
        {
          "user_idx" : 1,
          "portfolio_idx" : 2,
          "title" : "포트폴리오2",
          "contents" : "내용2",
          "goal" : 10
        }
      ];
      return data 
    }
  },
  mounted: function () {
    this.selectNewFeeds()
  }
}
</script>

<style>

</style>