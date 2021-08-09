<template>
  <v-sheet 
    :elevation="elevation"
    rounded
    class="portfolioFeed mx-2 my-5"
    @mouseover="elevation=10"
    @mouseleave="elevation=4"
  >
    <p class="p-3">{{ feed }}</p>
    <hr>
    <div>{{ $data }}</div>
  </v-sheet>
</template>

<script>
import axios from 'axios'

export default {
  name: 'PortfolioFeed',
  props: {
    feed: {
      type: Object
    },
  },
  data: function () {
    return {
      elevation: 4
    }
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/api/portfolio/asset/${this.feed.portfolio_idx}`
    })
    .then(res => {
      Object.assign(this.$data, res.data)
      console.log(this.$data)
    })
  }
}
</script>

<style>
.portfolioFeed {
  transition: 0.5s;
}
</style>