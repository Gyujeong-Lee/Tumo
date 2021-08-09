<template>
  <div id="mainfeed">
    <!-- Tab Grop -->
    <v-tabs grow :color="tabColor" id="feedTabs">
      <v-tab id="newFeedBtn" @click="selectNewFeeds">
        <v-icon large class="me-3">mdi-account-group</v-icon>
        <span>Feeds</span>
      </v-tab>
      <v-tab id="portfolioBtn" @click="selectPortfolio">
        <v-icon large class="me-3">mdi-chart-bar</v-icon>
        <span>Portfolio</span>
      </v-tab>
    </v-tabs>
    <!-- newFeed -->
    <div v-if="selectedTab === 'newfeeds'">
      <ArticleFeed v-for="(feed, idx) in articleFeedList" :key="idx" :feed="feed" />
      <infinite-loading @infinite="feedInfiniteHandler" spinner="waveDots" class="mt-5">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
    <!-- Portfolio -->
    <div v-else-if="selectedTab === 'portfolio'">
      <PortfolioFeed v-for="(feed, idx) in portfolioFeedList" :key="idx" :feed="feed" />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import ArticleFeed from './ArticleFeed'
import PortfolioFeed from './PortfolioFeed'
import InfiniteLoading from 'vue-infinite-loading'

export default {
  name: 'FeedList',
  data: function () {
    return {
      selectedTab: 'newfeeds',
      tabColor: 'primary',
      articleFeedList: [],
      portfolioFeedList: [],
      pageNum: 0,
    }
  },
  components: {
    ArticleFeed,
    PortfolioFeed,
    InfiniteLoading,
  },
  methods: {
    selectNewFeeds: function () {
      if (this.selectedTab !== 'newfeeds') {
        this.articleFeedList = []
        this.pageNum = 0
        this.tabColor = 'primary'
        this.selectedTab = 'newfeeds'
      }
    },
    selectPortfolio: function () {
      if (this.selectedTab !== 'portfolio') {
        this.portfolioFeedList = []
        this.pageNum = 0
        let feedData = this.getPortfolioFeeds()
        this.portfolioFeedList = feedData
      }
      this.tabColor = 'error'
      this.selectedTab = 'portfolio'
    },
    getNewFeeds: function () {
      return axios({
        method: 'GET',
        url: `/api/feed/${this.$store.state.user_info.id}/${this.pageNum}`
      })
      .then(res => {
        const feedList = res.data.feedList
        return feedList ? feedList : []
      })
      .catch(err => {
        console.log(err)
      })
    },
    getPortfolioFeeds: function () {
      // axios 요청 보낸후 받은 response.data return
      // axios({
      //   method: 'GET',
      //   url: `/api/portfolio/feedlist/${this.$store.state.user_info.id}/${this.pageNum}`
      // })
      // .then(res => {
      //   console.log(res)
      // })
      // data = dump data
      const data = [
        {
          portfolio_idx: 1,
          goal: 10.0,
          nickname: "admin",
          user_idx: 1,
          title: "나의 첫 포트폴리오",
          content: "처음으로 만든 포트폴리오"
        },
      ]
      return data 
    },
    feedInfiniteHandler: function ($state) {
      const EACH_LEN = 10
      this.getNewFeeds()
      .then(data => {
        setTimeout(() => {
          if (data.length) {
            this.articleFeedList = this.articleFeedList.concat(data)
            $state.loaded()
            this.pageNum += 1
            if (data.length / EACH_LEN < 1) {
              $state.complete()
            }
          } else {
            $state.complete()
          }
        }, 1000)
      })
    }
  },
}
</script>

<style>
#mainfeed {
  width: 95%;
}

#feedTabs {
  position: sticky;
  padding-top: 3rem;
  top: 48px;
  background-color: white;
  z-index: 1;
}

#feedTabs span {
  font-family: 'Otomanopee One', sans-serif;
}

@media screen and (min-width: 576px) {
  #mainfeed {
    width: 614px;
    padding: 0 1.5rem;
  } 
}

@media screen and (min-width: 940px) {
  #mainfeed {
    padding: 0 3rem 0 0;
  }
}


</style>