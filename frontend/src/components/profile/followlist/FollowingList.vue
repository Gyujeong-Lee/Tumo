<template>
  <v-dialog
    persistent
    width="550"
    v-model="isDrawFollowingList" 
  >
    <v-sheet>
      <h3>Following</h3>
      <v-list>
        <v-list-item v-for="(following, idx) in followingList" :key="idx">
          {{ following.nickname }}
        </v-list-item>
      </v-list>
      <button @click="closeModal" style="background-color:#00BFFE">닫기</button>
    </v-sheet>
  </v-dialog>
</template>

<script>
import axios from 'axios'

export default {
  name: 'FollowingList',
  data: function () {
    return {
      userId: this.userIdx,
      followingList: [		
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
    console.log('following 생성')
    // Following 리스트 요청
    axios({
      method: 'GET',
      url: `/sns/following/${this.userId}`,
    })
    .then(res => {
      console.log(res)
      // this.followingList에 추가할 것. 
    })
    .catch(err => {
      console.log(err)
    })
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