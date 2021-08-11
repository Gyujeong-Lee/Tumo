<template>
  <v-sheet 
  :elevation="elevation"
  rounded
  class="articleFeed mx-2 my-5"
  @mouseover="elevation=10"
  @mouseleave="elevation=4"
  height="auto"
  width="auto">
    <div>
      <p style="font-weight:bold" type="button" @click="moveToDetail">{{ myScrap.title }}</p>
      <div class="d-flex flex-row">
        <img src="@/assets/main/user.png" alt="user_img" style="width: 35px;" class="me-3">
        <router-link class="text-secondary nickname" :to="{ name: 'profile', params: { nickname: `${myScrap.nickname}` }}">
          @{{ myScrap.nickname }}
        </router-link>
      </div>
    </div>
    <!-- <Loading v-else/> -->
  </v-sheet>
</template>

<script>
import axios from 'axios'
// import Loading from '@/components/Loading.vue'

export default {
  name: 'ScrapList',
  data: function () {
    return {
      myScrap: this.scrap,
      elevation: 4,
    }
  },
  props: {
    scrap: {
      type: Object
    }
  },
  components: {
    // Loading,
  },
  methods: {
    moveToDetail: function () {
      axios({
        method: 'GET',
        url: `/api/article/${this.myScrap.boardIdx}/${this.myScrap.userIdx}`
      })
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
        this.$router.push({name: 'articleDetail', params: {boardIdx: this.myScrap.boardIdx}})
      })
    }
  }
}
</script>