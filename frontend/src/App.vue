<template>
  <v-app>
    <!-- navbar는 jwt token 여부에 따라 토글한다. -->
    <Navbar v-if="$store.state.config.Authorization"/>
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
  mounted: function () {
    const userData = JSON.parse(localStorage.getItem('userData'))
    if (userData) {
      this.$store.commit('LOGIN', userData)
      this.$router.push({ name: 'main'})
    }
  }
}
</script>