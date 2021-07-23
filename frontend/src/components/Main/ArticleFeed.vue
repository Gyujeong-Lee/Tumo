<template>
  <div class="mb-4 p-3 border border-dark">
    {{ data }}
    <hr>
    <div class="d-flex mb-3">
      <div style="background-color: gray;">프로필</div>
      <p class="my-0 mx-3">{{ data.user_idx }}</p>
    </div>
    <h5>{{ data.title }}</h5>
    <p>{{ data.content }}</p>
    <div class="d-flex justify-content-between">
      <div>
        <v-btn icon large v-if="islike" @click="cancelLikeArticle" color="error"><v-icon color="error">mdi-heart</v-icon></v-btn>
        <v-btn icon large v-else @click="likeArticle"><v-icon>mdi-heart-outline</v-icon></v-btn>
        <span>{{ likes }}</span>
      </div>
      <v-btn icon large><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
      <div>
        <v-btn icon large v-if="isscrap" @click="cancelScrapArticle"><v-icon>mdi-bookmark</v-icon></v-btn>
        <v-btn icon large v-else @click="scrapArticle"><v-icon>mdi-bookmark-outline</v-icon></v-btn>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ArticleFeed',
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
    }
  }
}
</script>

<style>

</style>