<template>
  <div v-if="userIdx">
    <v-tabs grow class="p-0" id="activityTabs">
      <v-tab id="articleBtn" @click="selectArticle">
      <v-icon large class="me-3">mdi-clipboard-text-outline</v-icon>
      <span>Article</span>
      </v-tab>
      <v-tab id="scrapBtn" @click="selectScrap">
      <v-icon large class="me-3">mdi-book-multiple-outline</v-icon>
      <span>Scrap</span>
      </v-tab>
    </v-tabs>
    <div v-if="selectedTab === 'article'">
      <!-- article -->
      <h4>article</h4>
      <ArticleList v-for="(article, idx) of activityList" :key="idx" :article="article"/>
    </div>
    <div v-else-if="selectedTab === 'scrap'">
      <!-- scrap -->
      <h4>scrap</h4>
      <ScrapList v-for="(scrap, idx) of activityList" :key="idx" :scrap="scrap"/>
    </div>
  </div>
</template>

<script>
import ArticleList from '@/components/profile/activitylist/ArticleList.vue'
import ScrapList from '@/components/profile/activitylist/ScrapList.vue'
import axios from 'axios'

export default {
  name: 'Activity',
  data: function () {
      return {
        userId: this.userIdx,
        selectedTab: 'article',
        activityList: [],
      }
  },
  props: {
    userIdx: Number
  },
  // mounted: function () {
  //   this.getArticleList()
  // },
  methods: {
    selectArticle: function () {
      if (this.selectedTab !== 'article') {
        this.selectedTab = 'article'
        this.activityList = []
        this.getArticleList()
        console.log(this.activityList)
      }
    },
    selectScrap: function () {
      if (this.selectedTab !== 'scrap') {
        this.selectedTab = 'scrap'
        this.activityList = []
        this.getScrapList()
      }
    },
    getArticleList: function () {
      //axios for Article
      axios({
        method: 'GET',
        url: `/sns/board/${this.userId}`
      })
      .then(res => {
        // const response = {
        //   "myFeed": [
        //     {
        //       "userIdx": 1,
        //       "nickname": "admin",
        //       "stock": null,
        //       "title": "얼마 전에 샀는데",
        //       "content": "오르겠죠...",
        //       "boardIdx": 3,
        //       "likes": 0,
        //       "createAt": "2021-08-03 10:43:29",
        //       "updateAt": "2021-08-03 10:57:00"
        //     }
        //   ],
        //   "message": "success"
        // }
        // api 응답 변경해야함. scrap 바꿀 것.
        console.log(res)
        this.activityList = res.data.scrap
      })
      .catch(err => {
        console.log(err)
      })
    },
    getScrapList: function () {
      console.log(this.userId)
      //axios for Scrap
      axios({
        method: 'GET',
        url: `/sns/scrap/${this.userId}`
      })
      .then(res => {
        const response = {
          "scrap" : [
            {
              "boardIdx" : 1,
              "title" : "스크랩하고 보기 좋은 글",
              "nickname" : "joon2",
            },
            {
              "boardIdx" : 2,
            "title" : "스크랩하고 보기 좋은 글2",
            "nickname" : "joon2",
            },
          ],
        "message" : "success"
        }
        console.log(res)
        //204 때문에 임의 데이터 삽입함.
        this.activityList = response.scrap
      })
      .catch(err => {
        console.log(err)
      })
    },
  },
  components: {
    ArticleList,
    ScrapList,
  }
}
</script>