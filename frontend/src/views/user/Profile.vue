<template>
  <div id="highest_block" class="container d-flex flex-column" 
  >
    <div class="d-flex justify-center mb-5">
      <!-- img와 (팔로우 요청, 정보 수정) 분기 -->
      <div id="user_image_block" class="d-flex flex-column me-5">
        <img src="@/assets/temp_user_image.jpg" alt="user image" id="user_image">
        <p v-if="itsMe" @click="edit_profile" type="button">Edit</p>
        <!-- v-else-if로 팔로우 언팔로우 분기처리 -->
        <p v-else-if="!isFollow">Follow</p>
        <p v-else> Unfollow</p>
      </div>
      <!-- 유저 정보 -->
      <div id="user_info_block">
        <h2>{{ user_info.nickname }}'s profile</h2>
          <span type="button" @click="openFollowerList" class="me-5"> Follwer : {{ user_info.followerCnt }}</span>
          <span type="button" @click="openFollowingList"> Follwing : {{ user_info.followingCnt }}</span>
          <FollowerList :followerList="followerList"/>
          <FollowingList :followingList="followingList"/>
          <div id="hash_tags" class="mt-5">
            <v-chip 
            v-for="(tag, idx) in user_info.tags" 
            :key="idx"
            class="me-2"
            color="#00BFFE"
            text-color="white"
            >
            {{ tag }}
            </v-chip>
          </div>
          <!-- 소개 -->
          <p class="fw-bold mt-3">{{ user_info.introduce }}</p>
      </div>
    </div>
    <!-- 포트폴리오  -->
    <div class="d-flex justify-center my-auto">
      <div class="col-5 me-5">
        <h3>portfolio</h3>
        <Portfolio v-if="user_info.userIdx" :userIdx="user_info.userIdx"/>
      </div>
    <!-- 작성글, 스크랩 탭 -->
      <div class="col-5">
        <h3>activity</h3>
        <Activity v-if="user_info.userIdx" :userIdx="user_info.userIdx"/>
      </div>
    </div>
  </div>
</template>

<script>
import Activity from '@/components/profile/Activity.vue'
import Portfolio from '@/components/profile/Portfolio.vue'
import FollowerList from '@/components/profile/followlist/FollowerList.vue'
import FollowingList from '@/components/profile/followlist/FollowingList.vue'
import axios from 'axios'

export default {
  name: "Profile",
  data: function () {
    return {
      portfolios: [],
      // img, follower, following, tag
      user_info: [],
      isFollow: false,
      followerList: [		
        {
          "user_idx" : 1,
          "nickname" : "gyoo",
          "introduce" : "안녕하세요.\n잘부탁드립니다."
        },
        {
          "user_idx" : 2,
          "nickname" : "joon2",
          "introduce" : ""
        }
      ],
      followingList: [		
        {
          "user_idx" : 1,
          "nickname" : "gyoo",
          "introduce" : "안녕하세요.\n잘부탁드립니다."
        },
        {
          "user_idx" : 2,
          "nickname" : "joon2",
          "introduce" : ""
        }
      ],
    }
  },
  components: {
    Activity,
    Portfolio,
    FollowerList,
    FollowingList,
  },
  //유저 데이터 받아오기
  created: function () {
    // 추후 파라미터 닉네임으로 변경 예정
    axios({
      method: 'GET',
      url: `/sns/profile/${this.$route.params.nickname}`,
    })
    .then (res => {
      console.log(res)
      this.user_info = res.data.users
    })
    .catch (err => {
      console.log(err)
    })
  },
  computed: {
    itsMe: function () {
      if (this.$store.state.user_info.nickname === this.$route.params.nickname) {
        return true
      } else {
        return false
      }
    },
  },
  methods: {
    // 로그인 유저와 프로필 유저의 팔로우 관계, 버튼 분기 처리
    checkFollow: function () {
      const login_user_idx = this.$store.state.user_info.id
      const profile_user_idx = this.user_info.userIdx
      axios({
        method: 'GET',
        url: `sns/follow/${login_user_idx}/${profile_user_idx}`
      })
      .then(res => {
        console.log(res)
        this.isFollow = res.isFollow

      })
      .catch(err => {
        console.log(err)
      })
    },
    // profile 유저의 팔로워 리스트
    openFollowerList: function () {
      axios({
        method: 'GET',
        url: `/sns/follower/${this.user_info.userIdx}`,
      })
      .then(res => {
        console.log(`follower${res}`)
        // this.followerList에 추가할 것. 
      })
      .catch(err => {
        console.log(err)
      })
      this.$store.state.drawFollowerList = true
    },
    // profile 유저의 팔로잉 리스트
    openFollowingList: function () {
      axios({
        method: 'GET',
        url: `/sns/following/${this.user_info.userIdx}`,
      })
      .then(res => {

        console.log(`following${res}`)
        // this.followingList에 추가할 것. 
      })
      .catch(err => {
        console.log(err)
      })
      this.$store.state.drawFollowingList = true
    },
    edit_profile: function () {
      // 프로필 수정 페이지로 이동
    }
  }
}
</script>

<style scoped>
 #highest_block {
   padding-top: 6rem;
 }
 #user_image {
   width: 5rem;
   height: 5rem;
   margin-bottom: 1rem;
 }
 h2, h3, p {
  text-align: center;
  font-family: 'Otomanopee One', sans-serif
 }
#activityTabs span {
  font-family: 'Otomanopee One', sans-serif;
}

#activityTabs {
  padding-top: 6rem;
  position: sticky;
  top: 0;
  background-color: white;
  z-index: 1;
}
p {
 color: #00BFFE; 
}
</style>