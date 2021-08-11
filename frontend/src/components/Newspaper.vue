<template>
  <div id="KeywordNews">
    <div v-for="(keyword, idx) in keywords" :key="idx" class="news">
      <div @click="moveToSearch(keyword)">
        <p>{{ idx+1 }}. {{ keyword }}</p>
        <img src="@/assets/main/newspaper.png" alt="newspaper" class="newspaper">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Newspaper',
  props: {
    keyList: {
      type: Array
    }
  },
  methods: {
    moveToSearch: function (keyword) {
      this.$router.push({ name: 'search', params: { keyword: keyword }})
    }
  },
  computed: {
    keywords: function () {
      return this.keyList.map(word => {
        return word.text
      })
    }
  }
}
</script>

<style>
#KeywordNews {
  position: relative;
  height: 350px;
}

#KeywordNews > div:nth-child(1) {
  top: -50px;
}

#KeywordNews > div:nth-child(2) {
  top: 70px;
}

#KeywordNews > div:nth-child(3) {
  top: 190px;
}

.news {
  position: absolute;
  width: 300px;
  height: 150px;
  padding-top: 50px;
  left: 0;
  overflow-y: hidden;
  border-bottom: 2px solid;
  box-shadow: 3px;
}

.news > div {
  position: relative;
  cursor: pointer;
  transition: 0.5s;
}

.news > div:hover {
  transform: translateY(-50px);
}

.news p {
  position: absolute;
  width: 220px;
  top: 40px;
  left: 18px;
  text-align: center;
  font-weight: 700;
  font-size: 1.4rem;
}

.newspaper {
  width: 100%;
}
</style>