<template>
  <v-dialog
    persistent
    width="550"
    v-model="isDrawFollowerList" 
  >
    <v-sheet>
      <h3>Follower</h3>
      <v-list>
        <v-list-item v-for="(follower, idx) in followerList" :key="idx">
          {{ follower.nickname }}
        </v-list-item>
      </v-list>
      <button @click="closeModal" style="background-color:#00BFFE">닫기</button>
    </v-sheet>
  </v-dialog>
</template>

<script>
import axios from 'axios'

export default {
  name: 'FollowerList',
  data: function () {
    return {
      userId: this.userIdx,
      followerList: [		
        {
          "user_idx" : 1,
          "nickname" : "gyoo",
          "introduce" : "안녕하세요.\n잘부탁드립니다."
        },
        {
          "user_idx" : 2,
          "nickname" : "joon2",
          "introduce" : ""
        }
      ]
    }
  },
  props: {
    userIdx: Number,
  },
  created: function () {
    console.log('follower 생성')
    // 팔로워 리스트
    axios({
      method: 'GET',
      url: `/sns/follower/${this.userId}`,
    })
    .then(res => {
      console.log(`follower${res}`)
      // this.followingList에 추가할 것. 
    })
    .catch(err => {
      console.log(err)
    })
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