<template>
  <div>
    <v-list-item 
    v-if="!isPublic"
    v-bind:class="{ read: note.readAt }"
    @click="readNote(note)"
    >
      <span style="color: #00BFFE">{{ note.nickname }}</span>님이 <span style="color: #CE1D28"> 팔로우</span> 요청을 보냈습니다.
      <v-btn
      v-if="!accepted"
      small class="ms-2" style="background-color:#00BFFE; color:white"
      @click="acceptFollow">수락</v-btn>
    </v-list-item>
    <v-list-item 
    v-else
    v-bind:class="{ read: note.readAt }"
    @click="readNote(note)"
    >
      <span style="color: #00BFFE">{{ note.nickname }} </span>님이 <span style="color: #CE1D28"> 팔로우</span>하셨습니다.
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
    notification: Object,
    followNotifications: Array,
  },
  computed: {
    isPublic: function () {
      if (this.$store.state.user_info.disclosure === 'public') {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    readNote: function (note) {
      // axios 요청 보내서 읽음 처리
      if (!this.note.readAt) {
        axios({
          method: 'PUT',
          url: `/api/sns/alarm/${note.noteIdx}`
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
      }
      // follow한 사람 profile
      this.$router.push({name: 'profile', params: {nickname: note.nickname}})
    },
    acceptFollow: function () {
      // axios 팔로우 승낙
      axios({
        method: 'POST',
        url: '/api/sns/follow/acception',
        data: {
          userIdx: this.note.userIdx,
          followingIdx: this.$store.state.user_info.id
        }
      })
      .then(res => {
        console.log(res)
        // 알림센터에서 삭제?
        this.accepted = true
        axios({
          method: 'DELETE',
          url: `/api/sns/alarm/${this.note.notificationIdx}`
        })
        .then(res => {
          console.log(`${res} 삭제 완료`)

          let notification_idx = this.notification.notificationIdx
          let idx = this.myAssets.findIndex(function(ob) {return ob.notificationIdx === notification_idx})
          this.followNotifications.splice(idx, 1)
        })
        .catch(err => {
          console.log(err)
        })
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