<template>
  <v-list-item 
  v-if="note.type==2"
  v-bind:class="{ read: note.readAt }"
  @click="readNote(note.notificationIdx)"
  >
    {{ note.nickname }}님이 좋아요를 눌렀습니다.
  </v-list-item>
  <v-list-item v-else-if="note.type==3"
  v-bind:class="{ read: note.readAt }"
  @click="readNote(note.notificationIdx)"
  >
    {{ note.nickname }}님이 댓글을 남겼습니다.
  </v-list-item>
  <v-list-item v-else-if="note.type==4"
  v-bind:class="{ read: note.readAt }"
  @click="readNote(note.notificationIdx)"
  >
    {{ note.nickname }}님이 스크랩 했습니다.
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
    // 상세 페이지 이동 api 문서 수정 후 변경
    this.$router.push({name: 'articleDetail', params: {boardIdx: `${this.note.boardIdx}`}})
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