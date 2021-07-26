<template>
  <div id="mainfeed">
    <v-tabs grow :color="tabColor" id="feedTabs" class="sticky-top" style="padding-top: 3em;">
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
import ArticleFeed from './ArticleFeed'
import PortfolioFeed from './PortfolioFeed'

export default {
  name: 'FeedList',
  data: function () {
    return {
      selectedTab: 'portfolio',
      feedList: [],
      tabColor: '',
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
        let feedData = this.getNewFeeds()
        this.feedList = feedData
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
          {
            "board_idx" : 2,
          "user_idx": 1,
          "title" : "제목입니다",
          "content" : "본문입니다",
          "likes" : 7,
          "islike" : false,
          "isscrap" : true
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
@import url('https://fonts.googleapis.com/css2?family=Otomanopee+One&display=swap');

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
    padding-left: 3rem;
  }
}

#feedTabs span {
  font-family: 'Otomanopee One', sans-serif;
}
</style>