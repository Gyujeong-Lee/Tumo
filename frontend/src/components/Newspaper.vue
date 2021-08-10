<template>
  <div id="KeywordNews">
    <div class="news">
      <div>
        <p>1. {{ keywords[0] }}</p>
        <img src="@/assets/main/newspaper.png" alt="newspaper" class="newspaper">
      </div>
    </div>
    <div class="news">
      <div>
        <p>2. {{ keywords[1] }}</p>
        <img src="@/assets/main/newspaper.png" alt="newspaper" class="newspaper">
      </div>
    </div>
    <div class="news">
      <div>
        <p>3. {{ keywords[2] }}</p>
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
  data: function () {
    return {
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
}

.news > div {
  position: relative;
  cursor: pointer;
}

.news > div:hover {
  transition: 0.5s;
  transform: translateY(-50px);
}

.news p {
  position: absolute;
  width: 250px;
  top: 40px;
  left: 5px;
  text-align: center;
  font-weight: 700;
  font-size: 1.4rem;
}

.newspaper {
  width: 100%;
}
</style>