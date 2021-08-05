<template>
  <div class="container d-flex justify-center my-5 h-100">
    <div class="d-flex flex-column align-items-center h-100 fw-bold">
      <img src="@/assets/login/logo.png" alt="mainlogo" class="w-50 mb-5"/>
      <!-- <img src="../../public/Tumo_Korean.png" alt="CI" class="w-100 mb-5"> -->
      {{nickname}}님의 이메일 {{email}}의 인증이 완료됐습니다.<br>

      <p class="text-center py-5 my-3"><router-link :to="{ name: 'Login' }">로그인 하러가기</router-link></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ConfirmEmail',
  components: {
  },    
  data: () => {
    return {
      // 이메일 인증 완료
      success: true,

      nickname: "",
      email: ""
    }
  },
  mounted: function () {

    // 서버로 전송할 인증코드 정보
    const data = {
        userIdx: parseInt(this.$route.params.useridx),
        code: this.$route.params.code
      };

    axios({
      method: 'POST',
      url: '/user/confirm-email',
      data: data
    })
    .then(res => {
      const message = res.data.message
      if (message !== "success") {
        alert('잘못된 요청입니다.');
        this.$router.push({ name: 'Login' })
      }
      this.nickname = res.data.nickname;
      this.email = res.data.email;
    })
    .catch(err => {
      console.log(err)
    })
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

#btnGroup {
  display: flex;
  flex-direction: row;
}


#signup_btn {
  background-color: #00BFFE;
  color: white;
}

#cancel_btn {
  background-color: #CE1D28;
  color: white;
}

.keywords_tag {
  background-color: #00BFFE;
}

a, p {
text-decoration: none;
font-family: 'Noto Sans KR', sans-serif;
}

#nickNameInput > button {
  position: absolute;
  right: 0;
  top: 20%;
}

#emailInput > button {
  position: absolute;
  right: 0;
  top: 20%;
}

#signupForm {
  width: 80%;
}

#btnGroup {
  justify-content: center;
}

.checkBtn {
  position: absolute;
  left: 100%;
  top: 20%;
  padding-left: 8px;
}

@media screen and (min-width: 500px){
  #btnGroup {
    justify-content: flex-end;
  }
}
</style>