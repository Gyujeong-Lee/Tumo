<template>
  <div class="d-flex justify-content-center">
    <v-sheet 
      elevation="4"
      rounded
      id="articleDetail"
    >
      <div class="d-flex justify-content-between mb-3">
        <div class="d-flex align-items-center">
          <img src="@/assets/main/user.png" alt="user" style="width: 55px;">
          <div class="d-flex flex-column ms-3">
            <h4 class="my-0">{{ title }}</h4>
            <div class="d-flex" style="font-size: 1em;">
              <router-link 
                class="my-0 text-secondary nickname" 
                :to="{ name: 'profile', params: { nickname: `${nickname}` }}"
              >
                @{{ nickname }}
              </router-link>
              <p class="my-0 mx-3 text-primary"><v-icon dense color="primary">mdi-chart-bar</v-icon> {{ stock }}</p>
            </div>
          </div>
        </div>
        <div>
          <p class="my-0">작성 : {{ createAt.substring(2, 16) }}</p>
          <p class="my-0">수정 : {{ updateAt.substring(2, 16) }}</p>
        </div>
      </div>
      <div v-html="content" class="mb-5"></div>
      <div class="mb-3">
        <v-chip v-for="(tag, idx) in tags" :key="idx" label class="px-3"># {{ tag }}</v-chip>
      </div>
      <!-- Btn Group -->
      <div class="d-flex justify-content-between mx-5">
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
      <div>
        <Comments v-if="!isLoading" :data="commentData" :boardIdx="boardIdx"/>
      </div>
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
      commentData: [],
      isLoading: true,
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
        url: '/sns/favor',
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
        url: `/sns/favor/${this.$store.state.user_info.id}/${this.boardIdx}`
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
        url: '/sns/scrap',
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
        url: `/sns/scrap/${this.$store.state.user_info.id}/${this.boardIdx}`
      })
      .then(() => {
      })
    },
    getComments: function () {
      axios({
        method: 'GET',
        url: `/article/comment/${this.boardIdx}/0`
      })
      .then(res => {
        if (res.status === 200) {
          this.commentData = res.data.commentList
        }
        this.isLoading = false
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
  created: function () {
    this.getComments()
    // 새로고침 시 data 없음
  }
}
</script>

<style>
#articleDetail {
  width: 614px;
  padding: 2rem;
  margin-top: 3rem;
  margin-bottom: 1rem;
}

@media screen and (min-width: 940px) {
  #articleDetail {
    margin-right: 1rem;
  }
}
</style>