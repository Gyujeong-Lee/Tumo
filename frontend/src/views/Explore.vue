<template>
  <div id="explore">
    <h1>Top 기업</h1>
    <CorporationCarousel />
    <h1 style="margin-top: 10rem; margin-bottom: 3rem;">Top 포트폴리오</h1>
    <TopPortfolio :selectedKeyword="selectedKeyword" />
  </div>
</template>

<script>
import _ from 'lodash'
import CorporationCarousel from '@/components/explore/CorporationCarousel'
import TopPortfolio from '../components/explore/TopPortfolio'

export default {
  name: 'Explore',
  components: {
    CorporationCarousel,
    TopPortfolio,
  },
  data: function () {
    return {
      selectedKeyword: ''
    }
  },
  created: function () {
    const tags = this.$store.state.user_info.tags
    this.selectedKeyword = _.sample(tags) ?? ''
  }
}
</script>

<style>
#explore {
  margin: 50px auto 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
}
</style>