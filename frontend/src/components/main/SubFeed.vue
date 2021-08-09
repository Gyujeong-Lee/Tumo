<template>
  <div id="subfeed" class="d-flex flex-column align-items-center">
    <vue-word-cloud
      style="
        height: 240px;
        width: 240px;
      "
      :words="hotkeyList"
      font-family="Jua"
      id="wordcloud"
    />
    <div>
      <h1 class="mb-4 fw-bold text-center">TOP KeyWords</h1>
    </div>
    <div v-for="(data, idx) in hotkeyList.slice(0, 3)" :key="idx">{{ idx+1 }}. {{ data.text }}</div>
    <Loading />
  </div>
</template>

<script>
import axios from 'axios'
import Loading from '@/components/Loading'

export default {
  name: 'SubFeed',
  components: {
    Loading,
  },
  data: function () {
    return  {
      hotkeyList: [],
    }
  },
  created: function () {
    axios({
      method: 'GET',
      url: '/api/feed/hotkeyword'
    })
    .then(res => {
      this.hotkeyList = res.data.hotkeyList.map(data => {
        return {
          text: data.keyword, 
          weight: data.amount,
          rotation: Math.random() * 360,
          color: "#" + Math.round(Math.random() * 0xFFFFFF).toString(16),
        }
      })
    })
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
    display: none !important;
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

#wordcloud {
  margin: 2rem 0rem;
}

</style>