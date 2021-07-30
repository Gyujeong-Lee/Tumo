<template>
  <div id="mainPage" class="h-100">
    <div class="d-flex justify-content-center h-100">
      <FeedList/>
      <SubFeed/>
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
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

#mainPage label {
  font-family: 'Noto Sans KR', sans-serif;
}

@media screen and (min-width: 940px) {
  #mainPage {
    min-width: 930px;
  }
}

</style>