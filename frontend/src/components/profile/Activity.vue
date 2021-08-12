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
      <ArticleList v-for="(article, idx) of activityList" :key="idx" :article="article"/>
      <Loading v-if="!activityList.length"/>
    </div>
    <div v-else-if="selectedTab === 'scrap'">
      <!-- scrap -->
      <ScrapList v-for="(scrap, idx) of activityList" :key="idx" :scrap="scrap"/>
      <Loading v-if="!activityList.length"/>
    </div>
  </div>
</template>

<script>
import ArticleList from '@/components/profile/activitylist/ArticleList.vue'
import ScrapList from '@/components/profile/activitylist/ScrapList.vue'
import axios from 'axios'
import Loading from '../Loading.vue'

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
        url: `/api/sns/board/${this.userId}`
      })
      .then(res => {
        // console.log(res)
        this.activityList = res.data.myFeed
      })
      .catch(err => {
        console.log(err)
      })
    },
    getScrapList: function () {
      // console.log(this.userId)
      //axios for Scrap
      axios({
        method: 'GET',
        url: `/api/sns/scrap/${this.userId}`
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
        
        this.activityList = res.data.scrap

        //204 때문에 임의 데이터 삽입함.
        this.activityList = response.scrap
      })
      .catch(err => {
        console.log(err)
      })
    },
  },
  created: function () {
    this.getArticleList()
  },
  components: {
    ArticleList,
    ScrapList,
    Loading,
  }
}
</script>