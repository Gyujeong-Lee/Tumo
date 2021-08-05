<template>
  <div class="d-flex justify-content-center">
    <v-sheet 
      elevation="4"
      rounded
      id="articleDetail"
    >
      <div class="d-flex justify-content-between mb-3">
        <div class="d-flex align-items-center">
          <img src="@/assets/main/user.png" alt="user_img" style="width: 55px;">
          <div class="ms-3">
            <h3 class="mb-1">{{ title }}</h3>
            <div style="font-size: 1rem;">
              <router-link 
                class="my-0 text-secondary nickname" 
                :to="{ name: 'profile', params: { nickname: `${nickname}` }}"
              >
                @{{ nickname }}
              </router-link>
              <span class="mx-3 text-primary"><v-icon dense color="primary">mdi-chart-bar</v-icon> {{ stock }}</span>
            </div>
          </div>
        </div>
        <div>
          <p class="my-0">작성 : {{ createAt.substring(2, 16) }}</p>
          <p class="my-0">수정 : {{ updateAt.substring(2, 16) }}</p>
        </div>
      </div>
      <!-- content & tags -->
      <div v-html="content" class="my-3"></div>
      <div class="mb-3">
        <v-chip v-for="(tag, idx) in tags" :key="idx" label class="px-3">#{{ tag }}</v-chip>
      </div>
      <!-- Btn Group -->
      <div class="d-flex justify-content-between">
        <div>
          <v-btn icon large v-if="isLike" @click="cancelLikeArticle" color="error"><v-icon color="error">mdi-heart</v-icon></v-btn>
          <v-btn icon large v-else @click="likeArticle"><v-icon>mdi-heart-outline</v-icon></v-btn>
          <span>{{ likes }}</span>
        </div>
        <v-btn icon large><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
        <div>
          <v-btn icon large v-if="isScrap" @click="cancelScrapArticle" color="yellow"><v-icon color="yellow">mdi-bookmark</v-icon></v-btn>
          <v-btn icon large v-else @click="scrapArticle"><v-icon>mdi-bookmark-outline</v-icon></v-btn>
        </div>
        <v-btn icon large><v-icon>mdi-share-variant-outline</v-icon></v-btn>
      </div>
      <Comments :boardIdx="boardIdx"/>
    </v-sheet>
    <SubFeed/>
  </div>
</template>

<script>
import axios from 'axios'
import SubFeed from '@/components/main/SubFeed'
import Comments from '@/components/comment/Comments'

export default {
  name: 'ArticleDetail',
  components: {
    SubFeed,
    Comments,
  },
  data: function () {
    return {
      ...this.$store.state.selectedArticle,
    }
  },
  methods: {
    likeArticle: function () {
      this.isLike = !this.isLike
      this.likes += 1
      // axios 요청
      const data = {
        boardIdx: this.boardIdx,
        userIdx: this.$store.state.user_info.id
      }
      axios({
        method: 'POST',
        url: '/api/sns/favor',
        data: data
      })
      .then(() => {
      })
    },
    cancelLikeArticle: function () {
      this.isLike = !this.isLike
      this.likes -= 1
      // axios 요청
      axios({
        method: 'DELETE',
        url: `/api/sns/favor/${this.$store.state.user_info.id}/${this.boardIdx}`
      })
      .then(() => {
      })
    },
    scrapArticle: function () {
      this.isScrap = !this.isScrap
      // axios 요청
      const data = {
        boardIdx: this.boardIdx,
        userIdx: this.$store.state.user_info.id
      }
      axios({
        method: 'POST',
        url: '/api/sns/scrap',
        data: data
      })
      .then(() => {
      })
    },
    cancelScrapArticle: function () {
      this.isScrap = !this.isScrap
      // axios 요청
      axios({
        method: 'DELETE',
        url: `/api/sns/scrap/${this.$store.state.user_info.id}/${this.boardIdx}`
      })
      .then(() => {
      })
    },
  },
  created: function () {
    // 새로고침 시 data 없음
  }
}
</script>

<style>
#articleDetail {
  width: 614px;
  padding: 2rem 2rem 1rem;
  margin: 3rem 0rem 1rem;
}

@media screen and (min-width: 940px) {
  #articleDetail {
    margin-right: 1rem;
  }
}
</style>