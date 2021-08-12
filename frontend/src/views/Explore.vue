<template>
  <div id="explore">
    <CorporationCarousel/>
  </div>
</template>

<script>
import _ from 'lodash'
import CorporationCarousel from '@/components/explore/CorporationCarousel'

export default {
  name: 'Explore',
  components: {
    CorporationCarousel,
  },
  data: function () {
    return {
      selectedKeyword: ''
    }
  },
  created: function () {
    const tags = this.$store.state.user_info.tags
    this.selectedKeyword = _.sample(tags) ?? ''
    console.log(this.selectedKeyword)
  }
}
</script>

<style>
#explore {
  height: 100%;
  margin: 50px auto;
}
</style>