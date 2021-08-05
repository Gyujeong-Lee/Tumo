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
            <h4 class="my-0">{{ data.title }}</h4>
            <div class="d-flex" style="font-size: 1em;">
              <p class="my-0 text-secondary nickname" >@{{ data.nickname }}</p>
              <p class="my-0 mx-3 text-primary"><v-icon dense color="primary">mdi-chart-bar</v-icon> {{ data.stock }}</p>
            </div>
          </div>
        </div>
        <div>
          <p class="my-0">작성 : {{ data.createAt.substring(2, 16) }}</p>
          <p class="my-0">수정 : {{ data.updateAt.substring(2, 16) }}</p>
        </div>
      </div>
      <div v-html="data.content" class="mb-5"></div>
      <div class="mb-3">
        <v-chip v-for="(tag, idx) in data.tags" :key="idx" label class="px-3"># {{ tag }}</v-chip>
      </div>
      <!-- Btn Group -->
      <div class="d-flex justify-content-between mx-5">
        <div>
          <v-btn icon large v-if="data.isLike" @click="cancelLikeArticle" color="error"><v-icon color="error">mdi-heart</v-icon></v-btn>
          <v-btn icon large v-else @click="likeArticle"><v-icon>mdi-heart-outline</v-icon></v-btn>
          <!-- <span>{{ data.likes }}</span> -->
        </div>
        <v-btn icon large><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
        <div>
          <v-btn icon large v-if="data.isScrap" @click="cancelScrapArticle" color="yellow"><v-icon color="yellow">mdi-bookmark</v-icon></v-btn>
          <v-btn icon large v-else @click="scrapArticle"><v-icon>mdi-bookmark-outline</v-icon></v-btn>
        </div>
        <v-btn icon large><v-icon>mdi-share-variant-outline</v-icon></v-btn>
      </div>
      <div>
        <Comments :data="commentData"/>
      </div>
    </v-sheet>
    <SubFeed/>
  </div>
</template>

<script>
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
      data: this.$store.state.selectedArticle,
      commentData: [],
    }
  },
  methods: {
    likeArticle: function () {
      this.data.isLike = !this.data.isLike
      this.data.likes += 1
      // axios 요청
    },
    cancelLikeArticle: function () {
      this.data.isLike = !this.data.isLike
      this.data.likes -= 1
      // axios 요청
    },
    scrapArticle: function () {
      this.data.isScrap = !this.data.isScrap
      // axios 요청
    },
    cancelScrapArticle: function () {
      this.data.isScrap = !this.data.isScrap
      // axios 요청
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
  padding: 2rem;
  margin-top: 3rem;
  margin-bottom: 1rem;
}

</style>