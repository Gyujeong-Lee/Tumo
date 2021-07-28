<template>
  <v-app>
      <Navbar/>
    <v-main>
      <!-- navbar는 jwt token 여부에 따라 토글한다. -->
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
}
</script>