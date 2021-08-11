<template>
  <div id="explore">
    <CorporationCarousel/>
  </div>
</template>

<script>
import CorporationCarousel from '@/components/explore/CorporationCarousel'

export default {
  name: 'Explore',
  components: {
    CorporationCarousel,
  }
}
</script>

<style>
#explore {
  height: 100%;
  margin: 50px auto;
}
</style>