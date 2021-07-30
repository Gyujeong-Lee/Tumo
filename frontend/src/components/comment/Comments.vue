<template>
  <div id="comments">
    <hr>
    <div v-for="(data, idx) in commentData" :key="idx">
      <div class="d-flex align-items-center mb-1">
        <img src="@/assets/main/user.png" alt="user" style="width: 1em;">
        <span class="px-2 fw-bold">{{ data.nickName }}</span>
      </div>
      <p>{{ data.content }}</p>
    </div>
    <div class="w-100 d-flex justify-content-center">
      <img src="@/assets/comment/paginator.png" alt="paginator" class="w-75">
    </div>
    <hr>
    <!-- 댓글 작성 폼 -->
    <form class="d-flex justify-content-between pb-2">
     <input v-model="comment" type="text" placeholder="댓글 작성" class="w-75">
     <v-btn color="primary" class="fw-bold" text plain :disabled="!comment.trim()" @click="createComment">게시</v-btn>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Comments',
  props: {
    data: {
      type: Array
    }
  },
  data: function () {
    return {
      commentData: this.data,
      comment: '',
    }
  },
  methods: {
    createComment: function () {
      // axios 요청
      // .then commentData에 push (axios 재요청 X)
      let data = {
        "comment_idx": 1,
        "user_idx": 1,
        "nickName": "이규빈",
        "content": this.comment,
      }
      this.commentData.push(data)
      // alert
      this.comment = ''
    }
  }
}
</script>

<style>
#comments {
  padding-left: 8%;
  padding-right: 8%;
}

#comments input:focus {
  outline: none;
}

</style>