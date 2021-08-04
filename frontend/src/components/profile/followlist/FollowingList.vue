<template>
  <v-dialog
    persistent
    width="550"
    v-model="isDrawFollowingList" 
  >
    <v-sheet>
      <h3>Following</h3>
      <v-list>
        <v-list-item v-for="(following, idx) in list" :key="idx">
          {{ following.nickname }}
        </v-list-item>
      </v-list>
      <button @click="closeModal" style="background-color:#00BFFE">닫기</button>
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