<template>
  <div id="explore">
    <CorporationCarousel />
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