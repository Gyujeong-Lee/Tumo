<template>
  <div class="d-flex justify-content-center">
    <v-sheet 
      elevation="8"
      rounded
      id="portfolioDetail"
    >
      <h1>portfolio</h1>
    </v-sheet>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'PortfolioDetail',
  data: function () {
    return {
      userIdx: this.$route.params.userIdx,
      portfolioIdx: this.$route.params.portfolioIdx,
      portfolio: {},
    }
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/api/portfolio/list/${this.userIdx}`
    })
    .then(res => {
      console.log(res)
      for (const tmp in res.data.portfolio) {
        if (tmp.portfolioIdx === this.portfolioIdx) {
          this.portfolio = tmp
        }
      }
    })
    .catch(err => {
      console.log(err)
    })
  }
}
</script>

<style>

</style>