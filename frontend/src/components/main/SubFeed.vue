<template>
  <div>
    <div id="subfeed">
      <div class="w-100 d-flex justify-content-center">
        <img src="@/assets/main/wordcloud.png" alt="wordcloud" class="w-75">
      </div>
      <div>
        <h1 class="mb-4 fw-bold text-center">TOP KeyWords</h1>
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
@import url('https://fonts.googleapis.com/css2?family=Gothic+A1:wght@800;900&display=swap');

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
  margin-left: 3rem;
}

#subfeed > div:last-child img {
  width: 100%;
}

#subfeed h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 900;
}
</style>