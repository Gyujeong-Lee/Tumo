<template>
  <div>
    <v-tabs grow :color="tabsColor" class="p-0" id="activityTabs">
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
        <ArticleList v-for="(article, idx) in activityList" :key="idx" :article="article"/>
    </div>
    <div v-else-if="selectedTab === 'scrap'">
        <!-- scrap -->
        <h4>scrap</h4>
        <ScrapList v-for="(scrap, idx) in activityList" :key="idx" :scrap="scrap"/>
    </div>
  </div>
</template>

<script>
import ArticleList from '@/components/profile/activitylist/ArticleList.vue'
import ScrapList from '@/components/profile/activitylist/ScrapList.vue'

export default {
  name: 'Activity',
  data: function () {
      return {
        selectedTab: 'article',
        activityList: [],
      }
  },
  methods: {
    selectArticle: function () {
      if (this.selectedTab !== 'article') {
        this.selectedTab = 'article'
        this.activityList = []
        let articleList = this.getArticleList()
        this.activityList = articleList
      }
    },
    selectScrap: function () {
      if (this.selectedTab !== 'scrap') {
        this.selectedTab = 'scrap'
        this.activityList = []
        let scrapList = this.getScrapList()
        this.activityList = scrapList
      }
    },
    getArticleList: function () {
      //axios for Article
    },
    getScrapList: function () {
      //axios for Scrap
      const data = {
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
      return data.scrap
    },
  },
  components: {
    ArticleList,
    ScrapList,
  }
}
</script>