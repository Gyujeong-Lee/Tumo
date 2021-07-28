<template>
  <div class="container d-flex flex-column" id="highest_block">
    <div class="d-flex justify-center mb-5">
      <!-- img와 (팔로우 요청, 정보 수정) 분기 -->
      <div id="user_image_block" class="d-flex flex-column me-5">
        <img src="@/assets/temp_user_image.jpg" alt="user image" id="user_image">
        <p v-if="itsMe">Edit</p>
        <p v-else>Follow</p>
      </div>
      <!-- 유저 정보 -->
      <div id="user_info_block">
        <h2>{{ this.$route.params.nickname }}'s Profile</h2>
          <span> Follwer : </span>
          <span> Follwing : </span>
          <p>hash tag</p>
          <p>안녕하세요</p>
      </div>
    </div>
    <!-- 포폴 / 작성글, 스크랩 -->
    <div class="d-flex justify-center my-auto">
      <div class="col-6">
        <h3>portfolio</h3>
      </div>
      <div class="col-6">
        <h3>activity</h3>
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
      user_info: [],
      

    }
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
  //DOM 생성 유저 데이터 받아오기
  created: function () {
    //axios
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
 }

</style>