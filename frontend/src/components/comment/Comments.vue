<template>
  <div id="comments">
    <hr class="mt-2">
    <div v-if="isLoading">로딩중 입니다.</div>
    <div v-else>
      <h3 v-if="!commentData.length" class="text-center">댓글이 없습니다.</h3>
      <div v-else>
        <div v-for="(data, idx) in commentData" :key="idx">
          <div class="d-flex align-items-center">
            <img src="@/assets/main/user.png" alt="user" style="width: 1em;">
            <span class="px-2 fw-bold">{{ data.nickname }}</span>
            <span class="me-2" style="font-size: 0.75rem;">{{ data.updateAt.substring(0, 16) }}</span>
            <el-popconfirm
              v-if="data.userIdx === $store.state.user_info.id"
              @confirm="deleteComment(data.commentIdx)"
              confirm-button-type='danger'
              cancel-button-type='primary'
              confirm-button-text='삭제'
              cancel-button-text='취소'
              icon="el-icon-warning"
              icon-color="red"
              title="댓글을 삭제하시겠습니까?"
            >
              <v-btn x-small icon slot="reference"><v-icon color="secondary">mdi-close-circle-outline</v-icon></v-btn>
            </el-popconfirm>
          </div>
          <p style="font-size: 0.9rem;">{{ data.content }}</p>
        </div>
        <v-pagination
          v-model="pageNum"
          :length="pageCnt"
          @input="getComments"
          color="#00BFFE"
          prev-icon="mdi-menu-left"
          next-icon="mdi-menu-right"
        ></v-pagination>
      </div>
      <hr>
      <!-- createComment Form -->
      <form class="d-flex w-100">
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
      isLoading: true,
      pageNum: 1,
      pageCnt: 1,
    }
  },
  methods: {
    getComments: function () {
      axios({
        method: 'GET',
        url: `/api/article/comment/${this.boardIdx}/${this.pageNum - 1}`
      })
      .then(res => {
        const commentList = res.data.commentList
        if (commentList) {
          this.commentData = commentList
          this.pageCnt = res.data.totalPageCnt
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
      axios({
        method: 'POST',
        url: '/api/article/comment',
        data: data
      })
      .then(() => {
        this.$message({
          message: '댓글이 성공적으로 작성되었습니다.',
          type: 'success',
          offset: 70,
        })
        this.content = ''
        this.pageNum = 1
        this.getComments()
      })
    },
    deleteComment: function (commentIdx) {
      axios({
        method: 'DELETE',
        url: `/api/article/comment/${commentIdx}`
      })
      .then(() => {
        this.$message({
          message: '댓글이 성공적으로 삭제되었습니다.',
          type: 'success',
          offset: 70,
        })
        if (this.commentData.length === 1) {
          if (this.pageNum === 1) {
            this.commentData.pop()
          } else {
            this.pageNum -= 1
            this.pageCnt -= 1
            this.getComments()
          }
        } else {
          this.getComments()
        }
      })
    }
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