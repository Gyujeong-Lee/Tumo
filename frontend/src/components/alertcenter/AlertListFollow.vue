<template>
  <div>
    <v-list-item 
    v-bind:class="{ read: note.readAt }"
    @click="readNote(note.notificationIdx)"
    >
      {{ note.nickname }}님이 팔로우 요청을 보냈습니다.
      <v-btn
      v-if="!accepted"
      small class="ms-2" style="background-color:#00BFFE; color:white"
      @click="acceptFollow">수락</v-btn>
    </v-list-item>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'AlertListFollow',
  data: function () {
    return {
      note: this.notification,
      accepted: false,
    }
  },
  props: {
    notification: Object
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
      this.note.readAt = 'true'
      this.$store.state.unreadAlert = this.$store.state.unreadAlert -1
    },
    acceptFollow: function () {
      // axios 팔로우 승낙
      axios({
        method: 'POST',
        url: '/api/sns/follow/acception',
        data: {
          userIdx: this.$store.state.user_info.id,
          otherIdx: this.note.userIdx
        }
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
      // 알림센터에서 삭제?
      this.accepted = true
      axios({
        method: 'DELETE',
        url: `/api/sns/alarm/${this.note.notificationIdx}`
      })
      .then(res => {
        console.log(`${res} 삭제 완료`)
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
}
</script>

<style>
  .read {
    background-color: gainsboro;
    color: whitesmoke;
  }
</style>