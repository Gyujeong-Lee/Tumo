<template>
  <div id="comments">
    <hr>
    <div v-if="isLoading">로딩중 입니다.</div>
    <div v-else>
      <h3 v-if="!commentData.length" class="text-center">댓글이 없습니다.</h3>
      <div v-else>
        <div v-for="(data, idx) in commentData" :key="idx">
          <div class="d-flex align-items-center mb-1">
            <span>{{ data.userIdx }}</span>
            <!-- <img src="@/assets/main/user.png" alt="user" style="width: 1em;"> -->
            <span class="px-2 fw-bold">{{ data.nickname }}</span>
          </div>
          <p>{{ data.content }}</p>
        </div>
        <v-pagination
          v-model="pageNum"
          :length="6"
          prev-icon="mdi-menu-left"
          next-icon="mdi-menu-right"
          @input="getComments"
        ></v-pagination>
      </div>
      <hr>
      <!-- 댓글 작성 폼 -->
      <form class="d-flex pb-2 w-100">
        <v-icon>mdi-chat-processing-outline</v-icon>
        <input v-model="content" type="text" placeholder="댓글 작성" class="w-100 mx-3">
        <v-btn color="primary" class="fw-bold" text plain :disabled="!content.trim()" @click="createComment">게시</v-btn>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Comments',
  props: {
    boardIdx: {
      type: Number
    }
  },
  data: function () {
    return {
      commentData: [],
      content: '',
      pageNum: 1,
      isLoading: true,
    }
  },
  methods: {
    getComments: function () {
      axios({
        method: 'GET',
        url: `/article/comment/${this.boardIdx}/${this.pageNum - 1}`
      })
      .then(res => {
        if (res.data.commentList) {
          this.commentData = res.data.commentList
        }
        this.isLoading = false
      })
      .catch(err => {
        console.log(err)
      })
    },
    createComment: function () {
      const data = {
        boardIdx: this.boardIdx,
        userIdx: this.$store.state.user_info.id,
        content: this.content
      }
      // axios 요청
      axios({
        method: 'POST',
        url: '/article/comment',
        data: data
      })
      .then(() => {
        const pushData = {
          ...data,
          nickname: this.$store.state.user_info.nickname
        }
        // pageNum이 0이면 4개 slicing 및 앞에 추가
        // pageNum이 0이 아니면 getComment pageNum 0 & pageNum 0
        this.commentData.push(pushData)
        this.content = ''
      })
      .catch(err => {
        console.log(err)
      })
    },
  },
  created: function () {
    this.getComments()
  }
}
</script>

<style>
#comments input:focus {
  outline: none;
}

</style>