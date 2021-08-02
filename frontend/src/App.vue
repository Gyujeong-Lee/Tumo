<template>
  <v-app>
    <!-- navbar는 jwt token 여부에 따라 토글한다. -->
    <Navbar/>
    <!-- <Navbar v-if="$store.state.config.Authorization"/> -->
    <v-main>
      <router-view/>
      <CreateArticle v-if="$store.state.drawCreateArticle"/>
    </v-main>
    <Footer/>
  </v-app>
</template>

<script>
import Navbar from './components/layout/Navbar.vue'
import Footer from './components/layout/Footer.vue'
import CreateArticle from '@/views/CreateArticle'

export default {
  name: 'App',
  data: () => ({
    //
  }),
  components: {
    Navbar,
    Footer,
    CreateArticle,
  },
  created: function () {
    const userData = JSON.parse(localStorage.getItem('userData'))
    if (userData) {
      this.$store.commit('LOGIN', userData)
      // this.$router.push({ name: 'main'})
    }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gothic+A1:wght@800;900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Otomanopee+One&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

</style>