<template>
  <div class="container h-100 d-flex flex-column justify-content-center">
    <div class="d-flex justify-content-center align-items-center">
      <!-- Login 화면 좌측 -->
      <div class="d-flex flex-column align-items-center" id="login">
        <img src="@/assets/login/logo.png" alt="mainlogo" style="width: 90%"/>
        <h2><span style="color: #CE1D28">투자</span>를 <span style="color: #00BFFE">모으다</span></h2>
        <div class="d-flex flex-column text-center" id="loginForm">
          <v-form ref="form" v-model="valid">
            <v-text-field
              v-model="credentials.email"
              :rules="emailRules"
              type="email"
              outlined
              dense
              hide-details
              label="E-mail"
              class="mb-4"
            ></v-text-field>
            <v-text-field
              v-model="credentials.password"
              :rules="passwordRules"
              type="password"
              outlined
              dense
              hide-details
              label="Password"
              class="mb-4"
            ></v-text-field>
            <v-btn :disabled="!valid" color="primary" class="w-100" @click="userLogin">로그인</v-btn>
          </v-form>
          <router-link :to="{ name: 'Login' }">비밀번호를 잊으셨나요?</router-link>
          <p class="my-auto">투모에 처음 오셨나요? <router-link :to="{ name: 'signup' }">가입하기</router-link></p>
          <hr>
          <v-btn color="error"><img src="@/assets/login/google.png" alt="googleIcon">Google 로그인</v-btn>
          <v-btn color="success"><img src="@/assets/login/naver.png" alt="naverIcon">Naver 로그인</v-btn>
          <v-btn color="yellow"><img src="@/assets/login/kakao.png" alt="kakaoIcon">Kakao 로그인</v-btn>
        </div>
      </div>

      <!-- Login 화면 우측 -->
      <div id="loginImage">
        <img src="@/assets/login/mobile_frame.png" alt="mobile_frame" class="w-100">
        <b-carousel
          id="carousel"
          :interval="3000"
          fade
        >
          <b-carousel-slide v-for="num in ['1', '2', '3']" :key="num">
            <template #img>
              <img
                class="w-100"
                :src="require(`@/assets/login/mobile/${num}.png`)"
                :alt="num"
              >
            </template>
          </b-carousel-slide>
        </b-carousel>
        <img src="@/assets/login/user.png" alt="user" id="userImage">
      </div>
    </div>

    <!-- Footer -->
    <p class="text-center fw-bold mt-5">@ All rights reserved by Team Tumo</p>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  name: "Login",
  components: {},
  data: function() {
    return {
      valid: true,
      credentials: {
        email: "",
        password: "",
      },
      emailRules: [(v) => /.+@.+\..+/.test(v) || "E-mail이 유효하지 않습니다."],
      passwordRules: [(v) => !!v],
    };
  },
  methods: {
    userLogin: function () {
      axios({
        method: 'POST',
        url: '/user/login',
        data: this.credentials
      })
      .then(res => {
        const message = res.data.message
        if (message === '로그인 실패') {
          alert('로그인 실패')
        } else {
          const userData = {
            'token': res.headers.authorization,
            'userIdx': res.data.userDto.userIdx,
            'nickname': res.data.userDto.nickname,
          }
          // local Storage에 저장 및 state 변경
          localStorage.setItem('userData', JSON.stringify(userData))
          this.$store.commit('LOGIN', userData)
          // main으로 이동
          // this.$router.push({ name: 'main'})
        }
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

@media screen and (max-width: 768px){
  #loginImage {
    display: none;
  }
}

@media screen and (min-width: 768px){  
  #loginImage {
    margin-left: 9%;
    width: 260px;
    position: relative;
  }

  #carousel img {
    height: 393px;
  }
}

h2 {
  font-family: 'Jua', sans-serif;
  margin-bottom: 0;
}

a, p {
  text-decoration: none;
  font-family: 'Noto Sans KR', sans-serif;
}

button img {
  height: 20px;
  position: absolute;
  left: 0;
}

#login {
  width: 360px;
}

#loginForm {
  padding-left: 3rem;
  padding-right: 3rem;
  padding-top: 2rem;
  padding-bottom: 1.5rem;
  width: 100%;
}

#loginForm > * {
  margin-bottom: 1rem;
  font-weight: 700;
}

#carousel {
  width: 84.5%;
  margin-left: 9.5%;
  position: absolute;
  top: 11.7%;
}

#carousel img {
  height: 393px;
}

#userImage {
  position: absolute;
  width: 300px;
  left: 40%;
  top: 53%;
  z-index: 1;
}

</style>
