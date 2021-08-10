<template>
  <div id="subfeed">
    <!-- article Detail Subfeed -->
    <div v-if="$route.name === 'articleDetail'" class="d-flex flex-column">
      <h3 class="fw-bold text-center mb-4">유사한 게시물</h3>
      <div>
        <v-card v-for="(feed, idx) in relatedFeeds" :key="idx" outlined elevation="4" class="px-4 pt-3 pb-2 mb-4">
          <img src="@/assets/main/user.png" alt="default" style="width: 30px;">
          <router-link 
            class="text-secondary nickname ms-2" 
            :to="{ name: 'profile', params: { nickname: `${feed.nickname}`}}"
          >
           @{{ feed.nickname }}
          </router-link>
          <div class="my-2 fw-bold h6" @click="moveToDetail(feed.userIdx, feed.boardIdx)" style="cursor: pointer;">{{ feed.title }}</div>
          <div>
            <v-chip v-for="(tag, idx) in feed.tags.slice(0, 3)" :key="idx" label class="px-2 me-1 mb-1">#{{ tag }}</v-chip>
          </div>
        </v-card>
        <v-skeleton-loader
          v-for="n in 3-relatedFeeds.length" :key="n"
          class="mb-4"
          type="avatar, heading, text, chip"
          elevation="4"
        ></v-skeleton-loader>
      </div>
      <Loading />
    </div>

    <!-- Main Subfeed -->
    <div v-else class="d-flex flex-column">
      <vue-word-cloud
        class="w-100"
        style="
          height: 240px;
          width: 240px;
        "
        :words="hotkeyList"
        font-family="Jua"
        :font-size-ratio="1"
        rotation-unit="deg"
        id="wordcloud"
      />
      <div>
        <h1 class="fw-bold text-center">TOP KeyWords</h1>
      </div>
      <br>
      <Newspaper v-if="hotkeyList.length" :keyList="hotkeyList.slice(0, 3)"/>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Loading from '@/components/Loading'
import Newspaper from '@/components/Newspaper'

export default {
  name: 'SubFeed',
  components: {
    Loading,
    Newspaper,
  },
  data: function () {
    return  {
      hotkeyList: [],
      relatedFeeds: [],
    }
  },
  methods: {
    getHotKeywords: function () {
      axios({
        method: 'GET',
        url: '/api/feed/hotkeyword'
      })
      .then(res => {
        this.hotkeyList = res.data.hotkeyList.map(data => {
          return {
            text: data.keyword, 
            weight: data.amount,
            rotation: Math.random() * 360,
            color: "#" + Math.round(Math.random() * 0xFFFFFF).toString(16),
          }
        })
      })      
    },
    getRelatedFeeds: function () {
      axios({
        method: 'GET',
        url: `/api/feed/${this.$store.state.selectedArticle.boardIdx}`
      })
      .then(res => {
        const feedList = res.data.feedList
        this.relatedFeeds = feedList ? feedList.slice(0, 3) : []
      })
    },
    moveToDetail: function (userIdx, boardIdx) {
      axios({
        method: 'GET',
        url: `/api/article/${boardIdx}/${userIdx}`
      })
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
        this.$router.push({ name: 'articleDetail', params: { userIdx, boardIdx }})
        this.$router.go()
      })

    }
  },
  created: function () {
    this.$route.name === 'articleDetail' ? this.getRelatedFeeds() : this.getHotKeywords()
  }
}
</script>

<style>
@media screen and (min-width: 576px) {
  #subfeed > div {
    width: 300px;
  }
}

@media screen and (max-width: 940px) {
  #subfeed {
    display: none !important;
  }  
}

#subfeed > div {
  position: sticky;
  top: 48px;
  padding-top: 4rem;
  margin-left: 2rem;
}

#subfeed h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 900;
}

#wordcloud {
  margin-bottom: 2rem;
}

.v-skeleton-loader > div {
  margin: 0rem 1rem 0.8rem;
}

.v-skeleton-loader > div:first-child,
.v-skeleton-loader > div:nth-child(2) {
  display: inline-block;
  margin: 0.5rem 1rem 0.3rem;
}

.v-skeleton-loader > div:nth-child(2) {
  margin: 0rem 0rem 1rem;
}
</style>