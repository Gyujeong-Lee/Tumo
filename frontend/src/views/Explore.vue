<template>
  <div id="explore">
    <h1 class="text-center">선택 키워드 : {{ selectedKeyword }}</h1>
    <CorporationCarousel />
    <TopPortfolio />
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
}
</style>