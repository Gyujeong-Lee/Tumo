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
      <ArticleList v-for="(article, idx) of activityList" :activityList="activityList" :key="idx" :article="article"/>
      <div v-if="!activityList.length" class="my-3">
        <v-sheet 
        :elevation="elevation"
        rounded
        @mouseover="elevation=10"
        @mouseleave="elevation=4"
        height="auto"
        width="320">
        <div>
          <v-btn icon @click="createArticle"><v-icon>mdi-file-plus-outline</v-icon></v-btn>
          <span class="text-center" style="color:#00BFFE; font-weight:bold">게시글을 작성하세요</span>
        </div>
        </v-sheet>
      </div>
    </div>
    <div v-else-if="selectedTab === 'scrap'">
      <!-- scrap -->
      <ScrapList v-for="(scrap, idx) of activityList" :key="idx" :scrap="scrap"/>
      <div v-if="!activityList.length" class="my-3">
        <v-sheet 
        :elevation="elevation"
        rounded
        @mouseover="elevation=10"
        @mouseleave="elevation=4"
        height="auto"
        width="320">
          <span class="text-center" style="color:#00BFFE; font-weight:bold">게시글을 스크랩하세요</span>
        </v-sheet>
      </div>
    </div>
  </div>
</template>

<script>
import ArticleList from '@/components/profile/activitylist/ArticleList.vue'
import ScrapList from '@/components/profile/activitylist/ScrapList.vue'
import axios from 'axios'
// import Loading from '../Loading.vue'

export default {
  name: 'Activity',
  data: function () {
      return {
        elevation: 4,
        userId: this.userIdx,
        selectedTab: 'article',
        activityList: [],
        isEmpty: false,
      }
  },
  props: {
    userIdx: Number
  },
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
        console.log(res)
        if (res.status == 200) {
          this.activityList = res.data.myFeed
        } 
      })
      .catch(err => {
        console.log(err)
      })
    },
    getScrapList: function () {
      //axios for Scrap
      axios({
        method: 'GET',
        url: `/api/sns/scrap/${this.userId}`
      })
      .then(res => {
        // console.log(res)
        if (res.status === 200) {
          this.activityList = res.data.scrap
        } 
      })
      .catch(err => {
        console.log(err)
      })
    },
    createArticle: function () {
      this.$store.state.drawCreateArticle = true
    },
  },
  created: function () {
    this.getArticleList()
  },
  components: {
    ArticleList,
    ScrapList,
  }
}
</script>