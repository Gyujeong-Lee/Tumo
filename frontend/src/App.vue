<template>
  <v-app>
    <v-main>
      <!-- navbar는 jwt token 여부에 따라 토글한다. -->
      <Navbar v-if="$store.state.config.Authorization"/>
      <router-view/>
      <CreateArticle v-if="$store.state.drawCreateArticle"/>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from './components/layout/Navbar.vue'
import CreateArticle from '@/views/CreateArticle'

export default {
  name: 'App',
  data: () => ({
    //
  }),
  components: {
    Navbar,
    CreateArticle,
  },
  mounted: function () {
    const userData = JSON.parse(localStorage.getItem('userData'))
    if (userData) {
      this.$store.commit('LOGIN', userData)
      // this.$router.push({ name: 'main'})
    }
  }
}
</script>