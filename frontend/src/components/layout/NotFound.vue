<template>
  <div class="d-flex flex-column justify-center align-center">
    <Loading/>
    <div>
      <h1><span style="color:#00BFFE; font-weight: bolder">투자를</span> <span style="color:#CE1D28; font-weight: bolder">모으다</span></h1>
    </div>
    <div class="d-flex flex-column mt-10">
      <h1 style="font-weight: bolder">404 Page Not Found</h1>
      <h3>메인으로</h3>
      <v-btn icon @click="moveToMain"><v-icon x-large>mdi-arrow-left-circle</v-icon></v-btn>
    </div>
  </div>
</template>

<script>
import Loading from '../Loading.vue'
export default {
  name: 'NotFound',
  data: function () {
    return {

    }
  },
  components: {
    Loading,
  },
  methods: {
    moveToMain: function () {
      this.$router.push({name: 'main'})
    }
  }
}
</script>

<style>

</style>