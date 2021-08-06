<template>
  <div>
    <div id="subfeed">
      <div class="w-100 d-flex justify-content-center">
        <img src="@/assets/main/wordcloud.png" alt="wordcloud" class="w-75">
      </div>
      <div>
        <h1 class="mb-4 fw-bold text-center">TOP KeyWords</h1>
      </div>
      <Loading />
    </div>
  </div>
</template>

<script>
import Loading from '@/components/Loading'

export default {
  name: 'SubFeed',
  components: {
    Loading,
  }
}
</script>

<style>
@media screen and (min-width: 576px) {
  #subfeed {
    width: 300px;
  }
}

@media screen and (max-width: 940px) {
  #subfeed {
    display: none;
  }  
}

#subfeed {
  position: sticky;
  top: 48px;
  padding-top: 2rem;
  margin-left: 2rem;
}

#subfeed h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 900;
}
</style>