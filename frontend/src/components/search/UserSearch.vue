<template>
  <div v-if="resultExist">
    <h3>유저 검색 결과</h3>
    <div class="d-flex flex-row my-autoss">
      <v-card elevation="2" shaped v-for="(user, idx) in users" :key="idx" class="me-3">
        <v-card-title>{{ user.nickName }}</v-card-title>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h3>유저 검색 결과가 없습니다...</h3>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'UserSearch',
  data: function () {
    return {
      searchWord: this.searchItem,
      resultExist: false,
      users: [
        {
          "userIdx" : 1,
          "nickName" : "이규정",
          "introduce" : "안녕하세요.\n잘 부탁드립니다."
        },
        {
          "userIdx" : 3,
          "nickName" : "이규빈",
          "introduce" : "안녕하세요.\n잘 부탁드립니다.22"
        }
      ],
    }
  },
  props: {
    searchItem: String,
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/api/sns/search/${this.$route.params.keyword}/1`
    })
    .then(res => {
      // console.log(res)
      if (res.request.status === 204) {
        console.log('자료 x')
      } else {
        this.resultExist = true
        // 응답 담기
        // this.users = res.data.users
      }
    })
    .catch(err => {
      console.log(err)
    })
  },

}
</script>