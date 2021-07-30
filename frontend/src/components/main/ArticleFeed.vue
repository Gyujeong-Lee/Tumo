<template>
  <v-sheet 
    elevation="4"
    rounded
    class="mx-2 my-5"
    height="auto"
    width="auto"
    id="articleFeed">
    <div class="d-flex justify-content-between mb-3">
      <div class="d-flex align-items-center">
        <img src="@/assets/main/user.png" alt="user" style="width: 35px;">
        <div class="d-flex align-items-center">
          <h6 class="my-0 mx-3">{{ data.title }}</h6>
          <p class="my-0 text-secondary">@nickName</p>
        </div>
      </div>
      <div>
        <v-btn icon large v-if="isscrap" @click="cancelScrapArticle" color="yellow"><v-icon color="yellow">mdi-bookmark</v-icon></v-btn>
        <v-btn icon large v-else @click="scrapArticle"><v-icon>mdi-bookmark-outline</v-icon></v-btn>
      </div>
    </div>
    <div v-html="data.content" class="mb-5"></div>
    <div class="d-flex justify-content-between">
      <div>
        <v-btn icon large v-if="islike" @click="cancelLikeArticle" color="error"><v-icon color="error">mdi-heart</v-icon></v-btn>
        <v-btn icon large v-else @click="likeArticle"><v-icon>mdi-heart-outline</v-icon></v-btn>
        <span>{{ likes }}</span>
      </div>
      <div>
        <v-btn icon large @click="drawComments"><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
      </div>
      <v-btn icon large><v-icon>mdi-share-variant-outline</v-icon></v-btn>
    </div>
    <Comments v-if="commentDrawer" :data="commentData"/>
  </v-sheet>
</template>

<script>
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
      islike: this.data.islike,
      isscrap: this.data.isscrap,
      likes: this.data.likes,
      commentDrawer: false,
      commentData: [],
    }
  },
  methods: {
    likeArticle: function () {
      this.islike = !this.islike
      this.likes += 1
      // axios 요청
    },
    cancelLikeArticle: function () {
      this.islike = !this.islike
      this.likes -= 1
      // axios 요청
    },
    scrapArticle: function () {
      this.isscrap = !this.isscrap
      // axios 요청
    },
    cancelScrapArticle: function () {
      this.isscrap = !this.isscrap
      // axios 요청
    },
    drawComments: function () {
      if (this.commentDrawer) {
        this.commentData = []
      } else {
        // axios 요청
        // .then
        const data = {
          "comment" :[
            {
            "comment_idx" : 1,
            "content":"댓글 내용입니다",
            "user_idx" : 1,
            "nickName" : "이규빈"
            },
            {
            "comment_idx" : 2,
            "content":"댓글 내용입니다",
            "user_idx" : 1,
            "nickName" : "이규빈"
            },
            {
            "comment_idx" : 3,
            "content":"댓글 내용입니다",
            "user_idx" : 1,
            "nickName" : "이규빈"
            },
          ]
        }
        this.commentData = data.comment
      }
      this.commentDrawer = !this.commentDrawer
    }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap');

#articleFeed {
  padding-top: 1rem;
  padding-bottom: 0.5rem;
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
  padding-left: 8%;
  padding-right: 8%;
}

#articleFeed > div:first-child {
  padding-left: 1rem;
  padding-right: 1rem;
}

</style>