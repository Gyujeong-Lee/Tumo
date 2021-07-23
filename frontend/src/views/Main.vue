<template>
  <div id="mainPage" class="h-100">
    <div class="d-flex justify-content-center h-100">
      <SubFeed/>
      <FeedList/>
    </div>
  </div>
</template>

<script>
import FeedList from '@/components/main/FeedList'
import SubFeed from '@/components/main/SubFeed'

export default {
  name: 'Main',
  components: {
    FeedList,
    SubFeed,
  },
  data: function () {
    return {
    }
  }
}
</script>

<style>
@media screen and (min-width: 940px) {
  #mainPage {
    min-width: 930px;
  }
}

</style>