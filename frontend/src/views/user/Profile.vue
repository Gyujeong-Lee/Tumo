<template>
  <div id="highest_block" class="container d-flex flex-column">    
    <div class="d-flex justify-center mb-5">
      <!-- img와 (팔로우 요청, 정보 수정) 분기 -->
      <div id="user_image_block" class="d-flex flex-column me-5">
        <img src="@/assets/temp_user_image.jpg" alt="user image" id="user_image">
        <p v-if="itsMe" @click="edit_profile" type="button">Edit</p>
        <!-- v-else-if로 팔로우 언팔로우 분기처리 -->
        <p type="button" v-else-if="!isFollow" @click="askFollow">Follow</p>
        <p type="button" v-else @click="askUnfollow"> Unfollow</p>
      </div>
      <!-- 유저 정보 -->
      <div id="user_info_block">
        <v-badge icon="mdi-star" color="#ffd700" v-if="gold">
          <h2>{{ user_info.nickname }}'s profile</h2>
        </v-badge>
        <v-badge icon="mdi-star" color="#c0c0c0" v-else-if="silver">
          <h2>{{ user_info.nickname }}'s profile</h2>
        </v-badge>
        <v-badge icon="mdi-star" color="#347b57" v-else>
          <h2>{{ user_info.nickname }}'s profile</h2>
        </v-badge>
        <div>
          <span type="button" @click="openFollowerList" class="me-5"> Follwer : {{ user_info.followerCnt }}</span>
          <span type="button" @click="openFollowingList"> Follwing : {{ user_info.followingCnt }}</span>
          <FollowerList v-if="followerList.length" :followerList="followerList"/>
          <FollowingList v-if="followingList.length" :followingList="followingList"/>
        </div>
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
    <div v-if="isPublic">
      <!-- 포트폴리오  -->
      <div class="d-grid justify-center">
        <div class="row my-auto">
          <div class="col-12 col-sm-6">
            <h3>portfolio</h3>
            <Portfolio v-if="user_info.userIdx" :userIdx="user_info.userIdx"/>
          </div>
        <!-- 작성글, 스크랩 탭 -->
          <div class="col-12 col-sm-6">
            <h3>activity</h3>
            <Activity v-if="user_info.userIdx" :userIdx="user_info.userIdx"/>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="mt-10">
      <v-sheet>
        <h2><v-icon>mdi-lock</v-icon>  비공개 계정입니다.</h2>
      </v-sheet>
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
      isPublic: false,
      isFollow: false,
      gold: false,
      silver: false,
      bronze: false,
      portfolios: [],
      // created 될 때 랭크 기준 부여
      // img, follower, following, tag
      user_info: [],
      followerList: [		
      ],
      followingList: [		
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
    axios({
      method: 'GET',
      url: `/api/sns/profile/${this.$route.params.nickname}`,
    })
    .then (res => {
      this.user_info = res.data.users
      if (res.data.users.disclosure === 'public') {
        this.isPublic = true
      }
      const profileUserIdx = res.data.users.userIdx
      const loginUserIdx = this.$store.state.user_info.id

      if (profileUserIdx === loginUserIdx) {
        this.isPublic = true
      } else {
        // 팔로잉 여부 조회
        axios({
          method: 'GET',
          url: `/api/sns/follow/${loginUserIdx}/${profileUserIdx}`,
        })
        .then(res => {
          this.isFollow = res.data.isFollow
          if (res.data.isFollow) {
            this.isPublic = true
          }
        })
        .catch(err => {
          console.log(err)
        })
      }
      // 랭크 조회
      axios({
        method: 'GET',
        url: `/api/portfolio/rank/${this.$route.params.nickname}`,
      })
      .then(res => {
        const rank = res.data.rank
        if (rank <= 10) {
          this.gold = true
        } else if ( 10 < rank && rank <= 20 ) {
          this.silver = true
        } else {
          this.bronze = true
        }
      })
      .catch(err => {
        console.log(err)
      })
    })
    .catch((error) => {
      // Error 😨
      if (error.response) {
        if (error.response.status === 500) {
          this.$alert("존재하지 않는 유저입니다.", "실패", 'error')
          this.$router.go(-1)
        }
      } else if (error.request) {
        console.log(error.request);
      } else {
        console.log('Error', error.message);
      }
      console.log(error.config);
    });
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
    askFollow: function () {
      // follow 요청
      const login_user_idx = this.$store.state.user_info.id
      const profile_user_idx = this.user_info.userIdx
      //axios
      axios({
        method: 'POST',
        url: `/api/sns/follow`,
        data: {
          followingIdx: profile_user_idx,
          userIdx: login_user_idx,
        }
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
      //btn 분기
      this.isFollow = true
    },
    askUnfollow: function () {
      // unfollow
      const login_user_idx = this.$store.state.user_info.id
      const profile_user_idx = this.user_info.userIdx
      axios({
        method: 'DELETE',
        url: `/api/sns/follow/${login_user_idx}/${profile_user_idx}`
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })

      //btn 분기
      this.isFollow = false
    },
    // profile 유저의 팔로워 리스트
    openFollowerList: function () {
      axios({
        method: 'GET',
        url: `/api/sns/follower/${this.user_info.userIdx}`,
      })
      .then(res => {
        // console.log(res)
        this.followerList = res.data.followers
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
        url: `/api/sns/following/${this.user_info.userIdx}`,
      })
      .then(res => {
        this.followingList = res.data.followers
        // console.log(this.followingList)
      })
      .catch(err => {
        console.log(err)
      })
      this.$store.state.drawFollowingList = true
    },
    edit_profile: function () {
      // 프로필 수정 페이지로 이동
      this.$router.push({ name: 'updateInfo' })
    }
  }
}
</script>

<style scoped>
 #highest_block {
   padding-top: 5rem;
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
 .gold {
   color: #ffd700;
 }
 .silver {
   color: #c0c0c0;
 }
 .bronze {
   color: #CD7F32;
 }
</style>