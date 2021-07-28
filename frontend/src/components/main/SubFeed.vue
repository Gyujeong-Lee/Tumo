<template>
  <div>
    <div id="subfeed">
      <div class="w-100 d-flex justify-content-center">
        <img src="@/assets/main/wordcloud.png" alt="wordcloud" class="w-75">
      </div>
      <div>
        <h3 class="mb-4 fw-bold text-center">TOP <img src="@/assets/main/three.png" alt="three" style="width: 3rem;">KeyWords</h3>
      </div>
      <button @click="drawModal" class="btn btn-primary">open modal</button>
      <button @click="logout" class="btn btn-danger">로그 아웃..</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SubFeed',
  methods: {
    drawModal: function () {
      this.$store.state.drawCreateArticle = true
    },
    logout: function () {
      localStorage.removeItem('userData')
      this.$store.commit('LOGOUT')
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap');

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
  top: 0px;
  padding-top: 5rem;
  margin-left: 3rem;
}

#subfeed > div:last-child img {
  width: 100%;
}

#subfeed h3 {
  font-family: 'Fredoka One', cursive;
}
</style>