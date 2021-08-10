<template>
  <div v-if="!isLoading" class="d-flex justify-content-center">
    <v-sheet 
      elevation="8"
      rounded
      id="articleDetail"
    >
      <div class="d-flex justify-content-between mb-3">
        <div class="d-flex align-items-center">
          <img src="@/assets/main/user.png" alt="user_img" style="width: 45px;">
          <div class="ms-3">
            <h5 class="mb-0 fw-bold">{{ $data.title }}</h5>
            <div style="font-size: 0.9rem;">
              <router-link 
                class="my-0 text-secondary nickname" 
                :to="{ name: 'profile', params: { nickname: `${$data.nickname}` }}"
              >
                @{{ $data.nickname }}
              </router-link>
              <span class="mx-3 text-primary"><v-icon small color="primary">mdi-chart-bar</v-icon> {{ $data.stock }}</span>
            </div>
          </div>
        </div>
        <div style="font-size: 0.75rem; min-width: 113px;">
          <p class="my-0">작성 : {{ $data.createAt.substring(2, 16) }}</p>
          <p class="my-0">수정 : {{ $data.updateAt.substring(2, 16) }}</p>
        </div>
      </div>
      <!-- content & tags -->
      <div v-html="$data.content" class="my-3"></div>
      <div class="mb-3">
        <v-chip v-for="(tag, idx) in $data.tags" :key="idx" label class="px-2 me-2 mb-2">#{{ tag }}</v-chip>
      </div>
      <!-- Btn Group -->
      <div class="d-flex justify-content-between">
        <div>
          <v-btn icon v-if="$data.isLike" @click="cancelLikeArticle" color="error"><v-icon color="error">mdi-heart</v-icon></v-btn>
          <v-btn icon v-else @click="likeArticle"><v-icon>mdi-heart-outline</v-icon></v-btn>
          <span style="font-size: 0.85rem;">{{ $data.likes }}</span>
        </div>
        <div>
          <v-btn icon v-if="$data.isScrap" @click="cancelScrapArticle" color="yellow"><v-icon color="yellow">mdi-bookmark</v-icon></v-btn>
          <v-btn icon v-else @click="scrapArticle"><v-icon>mdi-bookmark-outline</v-icon></v-btn>
        </div>
        <div v-if="isMyArticle" >
          <v-btn icon @click="drawUpdateArticle"><v-icon>mdi-pencil-plus</v-icon></v-btn>
          <UpdateArticle v-if="$store.state.drawUpdateArticle" @update="update_content"/>
        </div>
        <div>
          <el-popconfirm
            v-if="isMyArticle"
            @confirm="deleteArticle"
            confirm-button-type='danger'
            cancel-button-type='primary'
            confirm-button-text='삭제'
            cancel-button-text='취소'
            icon="el-icon-warning"
            icon-color="red"
            title="정말 게시물을 삭제하시겠어요?"
          >
            <v-btn icon slot="reference" @click="drawDeleteArticle"><v-icon>mdi-delete</v-icon></v-btn>
          </el-popconfirm>
          <v-btn v-else icon><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
        </div>
        <v-btn icon><v-icon>mdi-share-variant-outline</v-icon></v-btn>
      </div>
      <Comments :boardIdx="$data.boardIdx"/>
    </v-sheet>
    <SubFeed/>
  </div>
</template>

<script>
import axios from 'axios'
import SubFeed from '@/components/main/SubFeed'
import Comments from '@/components/comment/Comments'
import UpdateArticle from './UpdateArticle'

export default {
  name: 'ArticleDetail',
  components: {
    SubFeed,
    Comments,
    UpdateArticle,
  },
  data: function () {
    return {
      ...this.$store.state.selectedArticle,
      isMyArticle: false,
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
    drawUpdateArticle: function () {
      this.$store.state.drawUpdateArticle = true
    },
    drawDeleteArticle: function () {
      this.$store.state.drawDeleteArticle = true
    },
    deleteArticle: function () {
      axios({
        method: 'DELETE',
        url: `/api/article/${this.boardIdx}`
      })
      .then(() => {
        this.$alert("게시물이 성공적으로 삭제되었습니다.", "삭제 성공", "success")
        .then(() => {
          this.$router.push({ name: 'main' })
        })
      })
    },
    update_content: function () {
      const temp = {
        ...this.$data,
        ...this.$store.state.selectedArticle,
      }
      Object.assign(this.$data, temp)
    }
  },
  created: function () {
    if (!this.$store.state.selectedArticle) {
      const userIdx = this.$route.params.userIdx
      const boardIdx = this.$route.params.boardIdx
      axios({
        method: 'GET',
        url: `/api/article/${boardIdx}/${userIdx}`
      })
      .then(res => {
        Object.assign(this.$data, res.data.feed)
        this.$store.state.selectedArticle = res.data.feed
      })
    }
    setTimeout(() => {
      this.isLoading = false
    }, 1000);
    if (this.userIdx === this.$store.state.user_info.id) {
      this.isMyArticle = true
    }
  }
}
</script>

<style>
#articleDetail {
  width: 526px;
  padding: 2rem 2rem 1rem;
  margin: 3rem 0rem auto;
}

.el-popconfirm__action {
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
}

@media screen and (min-width: 940px) {
  #articleDetail {
    margin-right: 3rem;
  }
}
</style>