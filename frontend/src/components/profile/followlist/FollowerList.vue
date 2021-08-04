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
      <button @click="closeModal" style="background-color:#00BFFE">닫기</button>
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