<template>
  <v-sheet 
    :elevation="elevation"
    rounded
    class="articleFeed mx-2 my-5"
    @mouseover="elevation=10"
    @mouseleave="elevation=4"
  >
    <div class="d-flex justify-content-between mb-3">
      <div class="d-flex align-items-center">
        <img src="@/assets/main/user.png" alt="user_img" style="width: 35px;">
        <div class="ms-3">
          <h5 class="mb-0 fw-bold">{{ title }}</h5>
          <div style="font-size: 0.75rem;">
            <router-link 
              class="text-secondary nickname" 
              :to="{ name: 'profile', params: { nickname: `${nickname}` }}"
            >
              @{{ nickname }}
            </router-link>
            <span class="mx-3 text-primary"><v-icon small color="primary">mdi-chart-bar</v-icon> {{ stock }}</span>
          </div>
        </div>
      </div>
      <div>
        <v-btn icon v-if="isScrap" @click="cancelScrapArticle" color="yellow"><v-icon color="yellow">mdi-bookmark</v-icon></v-btn>
        <v-btn icon v-else @click="scrapArticle"><v-icon>mdi-bookmark-outline</v-icon></v-btn>
      </div>
    </div>
    <!-- content & tags -->
    <div v-html="content" @click="moveToDetail" style="cursor: pointer;"></div>
    <div class="mb-1">
      <v-chip v-for="(tag, idx) in tags" :key="idx" label class="px-2 me-2 mb-2">#{{ tag }}</v-chip>
    </div>
    <!-- Btn Group -->
    <div class="d-flex justify-content-between">
      <div>
        <v-btn icon v-if="isLike" @click="cancelLikeArticle" color="error"><v-icon color="error">mdi-heart</v-icon></v-btn>
        <v-btn icon v-else @click="likeArticle"><v-icon>mdi-heart-outline</v-icon></v-btn>
        <span style="font-size: 0.85rem;">{{ likes }}</span>
      </div>
      <v-btn icon @click="commentDrawer = !commentDrawer"><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
      <v-btn icon><v-icon>mdi-share-variant-outline</v-icon></v-btn>
    </div>
    <Comments v-if="commentDrawer" :boardIdx="boardIdx"/>
  </v-sheet>
</template>

<script>
import axios from 'axios'
import Comments from '@/components/comment/Comments'

export default {
  name: 'ArticleFeed',
  components: {
    Comments
  },
  props: {
    feed: {
      type: Object
    }
  },
  data: function () {
    return {
      ...this.feed,
      commentDrawer: false,
      elevation: 4,
    }
  },
  methods: {
    likeArticle: function () {
      this.isLike = !this.isLike
      this.likes += 1
      const data = {
        boardIdx: this.boardIdx,
        userIdx: this.$store.state.user_info.id
      }
      axios({
        method: 'POST',
        url: '/api/sns/favor',
        data: data
      })
    },
    cancelLikeArticle: function () {
      this.isLike = !this.isLike
      this.likes -= 1
      axios({
        method: 'DELETE',
        url: `/api/sns/favor/${this.$store.state.user_info.id}/${this.boardIdx}`
      })
    },
    scrapArticle: function () {
      this.isScrap = !this.isScrap
      const data = {
        boardIdx: this.boardIdx,
        userIdx: this.$store.state.user_info.id
      }
      axios({
        method: 'POST',
        url: '/api/sns/scrap',
        data: data
      })
    },
    cancelScrapArticle: function () {
      this.isScrap = !this.isScrap
      axios({
        method: 'DELETE',
        url: `/api/sns/scrap/${this.$store.state.user_info.id}/${this.boardIdx}`
      })
    },
    moveToDetail: function () {
      axios({
        method: 'GET',
        url: `/api/article/${this.boardIdx}/${this.userIdx}`
      })
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
        this.$router.push({ name: 'articleDetail', params: { userIdx: res.data.feed.userIdx ,boardIdx: this.boardIdx }})
      })
    },
  }
}
</script>

<style>
.articleFeed {
  padding: 1rem 0rem;
  transition: 0.5s;
  font-family: 'Noto Sans KR', sans-serif;
}

.articleFeed > * {
  margin: 0% 8%;
}

.articleFeed > div:first-child {
  margin: 0rem 1rem;
}

.nickname {
  text-decoration: none;
}

.nickname:hover {
  text-decoration: underline;
}
</style>