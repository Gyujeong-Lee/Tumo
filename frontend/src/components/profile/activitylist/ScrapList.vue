<template>
  <v-sheet 
    elevation="5"
    rounded="xl"
    class="mx-2 my-5"
    height="auto"
    width="auto">
    <p class="p-3" type="button" @click="moveToDetail">{{ scrap.title }}</p>
  </v-sheet>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ScrapList',
  data: function () {
    return {
      myScrap: this.scrap
    }
  },
  props: {
    scrap: {
      type: Object
    }
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