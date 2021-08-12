<template>
  <v-dialog
    persistent
    width="550"
    v-model="isDrawFollowingList" 
  >
    <v-sheet>
      <h3>Following</h3>
      <hr>
      <v-list v-for="(following, idx) in list" :key="idx">
        <v-list-item @click="moveToOther(following.nickname)" style="font-weight:bold">
          {{ following.nickname }}
        </v-list-item>
      </v-list>
      <div class="d-flex justify-end">
        <v-btn @click="closeModal" style="background-color:#00BFFE; color:white">close</v-btn>
      </div>
    </v-sheet>
  </v-dialog>
</template>

<script>

export default {
  name: 'FollowingList',
  data: function () {
    return {
      list: this.followingList
    }
  },
  props: {
    followingList: Array,
  },
  methods: {
    closeModal: function () {
      this.$store.state.drawFollowingList = false
    },
    moveToOther: function (name) {
      this.$router.push({name: 'profile', params: {nickname : name}})
    } 
  },
  computed: {
    isDrawFollowingList: function () {
      return this.$store.state.drawFollowingList
    },
  }
}
</script>

<style>

</style>