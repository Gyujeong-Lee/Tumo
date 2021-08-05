<template>
  <v-sheet 
    :elevation="elevation"
    rounded
    class="mx-2 my-5"
    @mouseover="elevation=10"
    @mouseleave="elevation=4"
    id="articleFeed">
    <div class="d-flex justify-content-between mb-3">
      <div class="d-flex align-items-center">
        <img src="@/assets/main/user.png" alt="user" style="width: 35px;">
        <div class="d-flex flex-column ms-3">
          <h4 class="my-0">{{ title }}</h4>
          <div class="d-flex" style="font-size: 0.75em;">
            <router-link 
              class="my-0 text-secondary nickname" 
              :to="{ name: 'profile', params: { nickname: `${nickname}` }}"
            >
              @{{ nickname }}
            </router-link>
            <p class="my-0 mx-3 text-primary"><v-icon small color="primary">mdi-chart-bar</v-icon> {{ stock }}</p>
          </div>
        </div>
      </div>
      <div>
        <v-btn icon large v-if="isScrap" @click="cancelScrapArticle" color="yellow"><v-icon color="yellow">mdi-bookmark</v-icon></v-btn>
        <v-btn icon large v-else @click="scrapArticle"><v-icon>mdi-bookmark-outline</v-icon></v-btn>
      </div>
    </div>
    <div v-html="content" class="mb-5" @click="moveToDetail" style="cursor: pointer;"></div>
    <div class="mb-3">
      <v-chip v-for="(tag, idx) in tags" :key="idx" label class="px-3"># {{ tag }}</v-chip>
    </div>
    <!-- Btn Group -->
    <div class="d-flex justify-content-between">
      <div>
        <v-btn icon large v-if="isLike" @click="cancelLikeArticle" color="error"><v-icon color="error">mdi-heart</v-icon></v-btn>
        <v-btn icon large v-else @click="likeArticle"><v-icon>mdi-heart-outline</v-icon></v-btn>
        <span>{{ likes }}</span>
      </div>
      <v-btn icon large @click="drawComments"><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
      <v-btn icon large><v-icon>mdi-share-variant-outline</v-icon></v-btn>
    </div>
    <Comments v-if="commentDrawer" :data="commentData" :boardIdx="boardIdx"/>
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
    data: {
      type: Object
    }
  },
  data: function () {
    return {
      ...this.data,
      commentDrawer: false,
      commentData: [],
      elevation: 4,
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
    drawComments: function () {
      if (this.commentDrawer) {
        this.commentDrawer = !this.commentDrawer
        this.commentData = []
      } else {
        // axios 요청
        this.getComments()
      }
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
        this.commentDrawer = !this.commentDrawer
      })
      .catch(err => {
        console.log(err)
      })
    },
    moveToDetail: function () {
      // 게시물 상세 정보 axios 요청 보내서 selectedArticle에 저장
      axios({
        method: 'GET',
        url: `/article/${this.boardIdx}/${this.userIdx}`
      })
      // .then router push
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
        this.$router.push({ name: 'articleDetail', params: { boardIdx: this.boardIdx }})
      })
      // 댓글정보도 받아오기
    },
  }
}
</script>

<style>
#articleFeed {
  padding-top: 1rem;
  padding-bottom: 0.5rem;
  transition: 0.5s;
}

#articleFeed h6 {
  font-family: 'Nanum Gothic', sans-serif;
  font-weight: 800
}

#articleFeed p {
  font-family: 'Nanum Gothic', sans-serif;
  font-weight: 400;
}

#articleFeed > * {
  margin-left: 8%;
  margin-right: 8%;
}

#articleFeed > div:first-child {
  margin-left: 1rem;
  margin-right: 1rem;
}

.nickname {
  text-decoration: none;
}

.nickname:hover {
  text-decoration: underline;
}

</style>