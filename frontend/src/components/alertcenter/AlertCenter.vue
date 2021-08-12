<template>
  <v-list style="width: 20rem">
    <!-- 팔로우 요청, 좋아요, 댓글, 스크랩 알림 따로 처리 -->
    <div v-if="followNotifications.length">
      <AlertListFollow v-for="(notification, idx) in followNotifications" :key="idx" :followNotifications="followNotifications" :notification="notification" />
    </div>
    <div v-else>
      <v-list-item>팔로우 알림이 없습니다.</v-list-item>
    </div>
    <!-- 그 외 -->
    <AlertListActivity v-for="(activityNotification, idx) in activityNotifications" :key="'xxx'+idx" :activityNotification="activityNotification" />

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
    ],
    activityNotifications: [
    ],
  }),
  components: {
    AlertListFollow,
    AlertListActivity,
  },
  created: function () {
    console.log(this.$store.state.unreadAlert)
    const userIdx = this.$store.state.user_info.id
    // type = 1번 : follow 요청 / 2번 : 좋아요 / 3번 : 댓글 / 4번 : 스크랩
    axios({
      method: 'GET',
      url: `/api/sns/alarm/${userIdx}`
    })
    .then (res => {
      // console.log(res)
      // 총 알림 개수
      const cnt = res.data.notification.length
      this.$store.state.unreadAlert = cnt
      // 결과 넣기
      for (const tmp of res.data.notification) {
        //읽지 않은 알림 개수
        if (tmp.readAt) {
          this.$store.state.unreadAlert = this.$store.state.unreadAlert -1
        }
        // 분리해서 담기
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