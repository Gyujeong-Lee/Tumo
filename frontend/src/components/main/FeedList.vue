<template>
  <div id="mainfeed">
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
import axios from 'axios'
import ArticleFeed from './ArticleFeed'
import PortfolioFeed from './PortfolioFeed'

export default {
  name: 'FeedList',
  data: function () {
    return {
      selectedTab: 'portfolio',
      feedList: [],
      tabColor: '',
      pageNum: 0,
    }
  },
  components: {
    ArticleFeed,
    PortfolioFeed,
  },
  methods: {
    selectNewFeeds: function () {
      if (this.selectedTab !== 'newfeeds') {
        this.feedList = []
        this.getNewFeeds()
      }
      this.tabColor = 'primary'
      this.selectedTab = 'newfeeds'
    },
    selectPortfolio: function () {
      if (this.selectedTab !== 'portfolio') {
        this.feedList = []
        let feedData = this.getPortfolioFeeds()
        this.feedList = feedData
      }
      this.tabColor = 'error'
      this.selectedTab = 'portfolio'
    },
    getNewFeeds: function () {
      // axios 요청 보낸후 받은 res.data.feedList 저장
      axios({
        method: 'GET',
        url: `/feed/${this.$store.state.user_info.id}/${this.pageNum}`
      })
      .then(res => {
        this.feedList = res.data.feedList
      })
      .catch(err => {
        console.log(err)
      })
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

#mainfeed {
  width: 95%;
}

@media screen and (min-width: 576px) {
  #mainfeed {
    width: 80%;
  } 
}

@media screen and (min-width: 940px) {
  #mainfeed {
    width: 614px;
    padding-right: 3rem;
  }
}

#feedTabs span {
  font-family: 'Otomanopee One', sans-serif;
}

#feedTabs {
  padding-top: 3rem;
  position: sticky;
  top: 48px;
  background-color: white;
  z-index: 1;
}

</style>