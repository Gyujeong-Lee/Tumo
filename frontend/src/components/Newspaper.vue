<template>
  <div id="KeywordNews">
    <div v-for="(keyword, idx) in keywords" :key="idx" class="position-relative">
      <img :src="require(`@/assets/main/subfeed/${idx+1}.png`)" alt="number" class="rank">
      <div class="news">
        <div @click="moveToSearch(keyword)">
          <p>{{ keyword | truncate(10) }}</p>
          <img src="@/assets/main/newspaper.png" alt="newspaper" class="newspaper">
        </div>
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
  filters: {
    truncate: function (text, length) {
      return text.length > length ? text.substring(0, length) + '...' : text
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
  font-family: 'Noto Sans KR', sans-serif;
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
  width: 260px;
  height: 150px;
  padding-top: 50px;
  left: 50px;
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
  width: 200px;
  top: 35px;
  left: 10px;
  text-align: center;
  font-weight: 700;
  font-size: 1.4rem;
}

.newspaper {
  width: 100%;
}

.rank {
  position: absolute;
  width: 40px;
  top: 100px;
}
</style>