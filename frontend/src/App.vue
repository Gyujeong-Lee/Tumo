<template>
  <v-app>
    <v-main>
      <!-- navbar는 jwt token 여부에 따라 토글한다. -->
      <Navbar/>
      <router-view/>
      <CreateArticle/>
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
}
</script>