<template>
  <v-dialog
    persistent
    width="550"
    v-model="isDrawFollowerList" 
  >
    <v-sheet>
      <h3>Follower</h3>
      <v-list>
        <v-list-item v-for="(follower, idx) in list" :key="idx">
          {{ follower.nickname }}
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
  name: 'FollowerList',
  data: function () {
    return {
      userId: this.userIdx,
      list: this.followerList
    }
  },
  props: {
    followerList: Array
  },
  methods: {
    closeModal: function () {
      this.$store.state.drawFollowerList = false
    }
  },
  computed: {
    isDrawFollowerList: function () {
      return this.$store.state.drawFollowerList
    },
  }
}
</script>

<style>

</style>