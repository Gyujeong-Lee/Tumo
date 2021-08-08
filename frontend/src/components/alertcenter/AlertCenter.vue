<template>
  <v-list>
    <!-- 팔로우 요청, 좋아요, 댓글, 스크랩 알림 따로 처리 -->
    <AlertListFollow v-for="(notification, idx) in followNotifications" :key="idx" :notification="notification" />
    <!-- 그 외 -->
    <AlertListActivity v-for="(activityNotification, idx) in activityNotifications" :key="'A'+idx" :activityNotification="activityNotification" />
  </v-list>
</template>

<script>
import axios from 'axios'
import AlertListFollow from '../alertcenter/AlertListFollow.vue'
import AlertListActivity from '../alertcenter/AlertListActivity.vue'
export default {
  name: 'AlertCenter',
  data: () => ({
    // vuex로 처리할 것
    followNotifications: [
      // 더미 데이터, 삭제할 것
      {
        "notificationIdx" : 7,
        "userIdx" : 2,
        "nickname" : "aaa",
        "type" : 1,
        "boardIdx" : -1,
        "readAt" : null,
        "createAt" : "2021-07-20 11:16:00"
      },
      {
        "notificationIdx" : 5,
        "userIdx" : 3,
        "nickname" : "aaa",
        "type" : 1,
        "boardIdx" : -1,
        "readAt" : "2021-07-10 06:16:00",
        "createAt" : "2021-05-05 11:16:05"
      }
    ],
    activityNotifications: [
      {
        "notificationIdx" : 5,
        "userIdx" : 2,
        "nickname" : "gyu",
        "type" : 2,
        "boardIdx" : -1,
        "readAt" : null,
        "createAt" : "2021-07-20 11:16:00"
      },
      {
        "notificationIdx" : 5,
        "userIdx" : 3,
        "nickname" : "dong",
        "type" : 3,
        "boardIdx" : -1,
        "readAt" : "2021-07-10 06:16:00",
        "createAt" : "2021-05-05 11:16:05"
      }
    ],
  }),
  components: {
    AlertListFollow,
    AlertListActivity,
  },
  created: function () {
    const userIdx = this.$store.state.user_info.id
    // type = 1번 : follow 요청 / 2번 : 좋아요 / 3번 : 댓글 / 4번 : 스크랩
    axios({
      method: 'GET',
      url: `/api/sns/alarm/${userIdx}`
    })
    .then (res => {
      console.log(res.data)
      // 결과 넣기
      for (const tmp in res.data.notification) {
        if (tmp.type === 1) {
          this.followNotifications.push(tmp)
        }
        else {
          this.activityNotifications.push(tmp)
        }
      }
    })
    .catch (err => {
      console.log(err)
    })
  },
}
</script>