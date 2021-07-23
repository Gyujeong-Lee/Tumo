<template>
  <div id="mainPage" class="h-100">
    <div class="d-flex justify-content-center h-100">
      <div id="subfeed" class="border border-dark">
        <p>123</p>
      </div>
      <div id="mainfeed">
        <FeedList/>
      </div>
    </div>
  </div>
</template>

<script>
import FeedList from '@/components/main/FeedList'

export default {
  name: 'Main',
  components: {
    FeedList,
  },
  data: function () {
    return {
    }
  }
}
</script>

<style>
@media screen and (min-width: 576px) {
  #mainPage {
    min-width: 930px;
  }

  #mainfeed {
    width: 614px;
    padding-left: 2rem;
  }

  #subfeed {
    width: 300px;
  }
}

#subfeed * {
  position: sticky;
  top: 0px;
}

</style>