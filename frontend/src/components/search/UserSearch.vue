<template>
  <div v-if="users.length">
    <h4>유저 검색 결과</h4>
    <div class="d-flex flex-row my-autoss">
      <v-card elevation="2" shaped v-for="(user, idx) in users" :key="idx" class="me-3" @click="moveToProfile(user.nickname)">
        <div class="d-flex flex-column p-1">
          <div class="d-flex justify-center mt-1">
            <img src="@/assets/main/user.png" alt="user_img" style="width: 30px; height: 30px">
          </div>
          <v-card-title class="text-center">{{ user.nickname }}</v-card-title>
          <v-card-subtitle>{{ user.introduce }}</v-card-subtitle>
        </div>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h4 style="color:#CE1D28">유저 검색 결과가 없습니다...</h4>
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
      pageNum: 0,
      users: [],
    }
  },
  props: {
    searchItem: String,
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/api/sns/search/${this.$route.params.keyword}/${this.pageNum}`
    })
    .then(res => {
      // console.log(res)
      if (res.request.status === 204) {
        // console.log(유저 자료 x)
      } else {
        this.resultExist = true
        // 응답 담기
        this.users = res.data.users
        // console.log(this.users)
      }
    })
    .catch(err => {
      console.log(err)
    })
  },
  methods: {
    moveToProfile: function (userName) {
      this.$router.push({name: 'profile', params: {nickname: userName}})
    } 
  }
  

}
</script>