<template>
  <v-list-item 
  v-if="note.type==2"
  v-bind:class="{ read: note.readAt }"
  @click="readNote(note.notificationIdx)"
  >
    <span style="color: #00BFFE">{{ note.nickname }}</span>님이 <span style="color: #CE1D28"> 좋아요</span>를 눌렀습니다.
  </v-list-item>
  <v-list-item v-else-if="note.type==3"
  v-bind:class="{ read: note.readAt }"
  @click="readNote(note.notificationIdx)"
  >
    <span style="color: #00BFFE">{{ note.nickname }}</span>님이 <span style="color: #CE1D28"> 댓글</span>을 남겼습니다.
  </v-list-item>
  <v-list-item v-else-if="note.type==4"
  v-bind:class="{ read: note.readAt }"
  @click="readNote(note.notificationIdx)"
  >
    <span style="color: #00BFFE">{{ note.nickname }}</span>님이 <span style="color: #CE1D28"> 스크랩</span> 했습니다.
  </v-list-item>

</template>

<script>
import axios from 'axios'

export default {
  name: 'AlertListActivity',
  data: function () {
    return {
      note: this.activityNotification
    }
  },
  props: {
    activityNotification: Object
  },
  methods: {
    readNote: function (noteIdx) {
      if (!this.note.readAt) {
        // axios 요청 보내서 읽음 처리
          axios({
            method: 'PUT',
            url: `/api/sns/alarm/${noteIdx}`
          })
          .then (res => {
            console.log(res)
          })
          .catch(err => {
            console.log(err)
          })
          // front 읽음 처리
          this.note.readAt = true
          this.$store.state.unreadAlert = this.$store.state.unreadAlert -1
      }
    
      // 상세 페이지 이동 api 문서 수정 후 변경 
      axios({
        method: 'GET',
        url: `/api/article/${this.note.boardIdx}/${this.$store.state.user_info.id}`
      })
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
        this.$router.go({ name: 'articleDetail', params: {userIdx: this.$store.state.user_info.id, boardIdx: this.note.boardIdx }})
      })
    } 
  }
}
</script>

<style>
  .read {
    background-color: gainsboro;
    color: whitesmoke;
  }
</style>