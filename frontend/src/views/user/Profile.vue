<template>
  <div id="highest_block" class="container d-flex flex-column" 
  >
    <div class="d-flex justify-center mb-5">
      <!-- img와 (팔로우 요청, 정보 수정) 분기 -->
      <div id="user_image_block" class="d-flex flex-column me-5">
        <img src="@/assets/temp_user_image.jpg" alt="user image" id="user_image">
        <p 
        v-if="itsMe"
        @click="edit_profile"
        >Edit</p>
        <!-- v-else-if로 팔로우 언팔로우 분기처리 -->
        <p 
        v-else-if="!follow_flag"
        >Follow</p>
        <p v-else> Unfollow</p>
      </div>
      <!-- 유저 정보 -->
      <div id="user_info_block">
        <h2>{{ this.$route.params.nickname }}'s profile</h2>
          <span> Follwer : {{ user_info }}</span>
          <span> Follwing : {{ user_info }}</span>

          <div id="hash_tags" class="mt-5">
            <v-chip 
            v-for="(hash_tag, idx) in hash_tags" 
            :key="idx"
            class="me-2"
            color="#00BFFE"
            text-color="white"
            >
            {{ hash_tag }}
            </v-chip>
          </div>
          <!-- 소개 -->
          <p class="fw-bold mt-3">한 줄 소개</p>
      </div>
    </div>
    <!-- 포트폴리오  -->
    <div class="d-flex justify-center my-auto">
      <div class="col-5">
        <h3>portfolio</h3>
      </div>

    <!-- 작성글, 스크랩 탭 -->
      <div class="col-5">
        <h3>activity</h3>
        <v-tabs grow :color="tabColor" class="p-0" id="activityTabs">
          <v-tab id="articleBtn" @click="selectArticles">
            <v-icon large class="me-3">mdi-clipboard-text-outline</v-icon>
            <span>Article</span>
          </v-tab>
          <v-tab id="scrapBtn" @click="selectScrap">
            <v-icon large class="me-3">mdi-book-multiple-outline</v-icon>
            <span>Scrap</span>
          </v-tab>
        </v-tabs>
        <div v-if="selectedTab === 'newfeeds'">
            <!-- article -->
        </div>
        <div v-else-if="selectedTab === 'portfolio'">
          <!-- scrap -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Profile",
  data: function () {
    return {
      articles: [],
      portfolios: [],
      // img, follower, following
      user_info: [],
      hash_tags: ['삼성전자', 'ESG'],
    }
  },
  //DOM 생성, 유저 데이터 받아오기
  created: function () {
    //axios 요청 - 유저 정보 profile
      // const token = localStorage.getItem('jwt')
      // const config = {
      //   Authorization: `JWT ${token}`
      // }
      // axios({
      //   method: 'get',
      //   url: `${this.$store.state.domain}/accounts/mypage/`,
      //   headers: config
      // })
      // .then(res => {
      //   // console.log(res)
      //   this.reviews = res.data.reviews
      //   this.articles = res.data.articles
      //   this.articlecomments = res.data.articlecomments
      //   this.reviewcomments = res.data.reviewcomments
      //   // console.log(this.reviews)
      //   // console.log(this.reviewcomments)
      // })
      // .catch(err => {
      //   console.log(err)
      // })
    },
  computed: {
    itsMe: function () {
      if (this.$store.state.user_info.nickname === this.$route.params.nickname) {
        return true
      } else {
        return false
      }
    },
    follow_flag: function () {
      // axios 요청 보내서 현재 로그인한 사용자가 프로필 유저의 팔로워인지 검사

      return true
    }
  },
  methods: {
    edit_profile: function () {
      // 사진
      // hash tag 추가, 삭제 - vanila setattribute?로 x 띄우기 등
      // 한 줄 소개
      // nick name? 이건 고려해봐야 함
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

</style>