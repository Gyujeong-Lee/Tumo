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
              dense
              outlined
              hide-details
              type="email"
              label="이메일"
              class="mb-4"
              v-model="credentials.email"
              :rules="emailRules"
            ></v-text-field>
            <v-text-field
              dense
              outlined
              hide-details
              type="password"
              label="비밀번호"
              class="mb-4"
              v-model="credentials.password"
              :rules="passwordRules"
            ></v-text-field>
            <v-btn @click="userLogin" :disabled="!valid" :loading="isLoading" class="w-100" color="primary">로그인</v-btn>
            <EmailAuth v-if="$store.state.drawEmailAuth" :userIdx="userIdx" :email="credentials.email"/>
          </v-form>
          <v-alert v-if="error" dense text type="error" style="font-size: 0.8rem;">회원 정보를 다시 확인해 주세요.</v-alert>
          <v-alert v-if="isOauthUserError" dense text type="error" style="font-size: 0.8rem;">소셜 로그인으로 가입된 이메일입니다.<br>GOOGLE 로그인을 클릭해주세요.</v-alert>
          <p @click="drawModal" class="text-primary" style="cursor: pointer;" >비밀번호를 잊으셨나요?</p>
          <FindPassword v-if="$store.state.drawFindPassword" />
          <p class="my-auto">투모에 처음 오셨나요? <router-link :to="{ name: 'signup' }">가입하기</router-link></p>
          <hr>
          <div id="my-signin2"></div><p/>
          <v-alert v-if="isNotOauthUserError" dense text type="error" style="font-size: 0.8rem;">일반 회원으로 가입된 이메일입니다.<br>일반 로그인 창을 이용해주세요.</v-alert>
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
  </div>

</template>

<script>
import axios from 'axios'
import FindPassword from '@/components/account/FindPassword'
import EmailAuth from '@/components/account/EmailAuth'

export default {
  name: "Login",
  components: {
    FindPassword,
    EmailAuth,
  },
  data: function() {
    return {
      valid: true,
      error: false,
      isLoading: false,
      userIdx: null,
      credentials: {
        email: "",
        password: "",
      },
      googleUser: null,
      isOauthUserError: false,
      isNotOauthUserError: false,
    };
  },
  methods: {
    userLogin: function () {
      this.isNotOauthUserError = false;
      this.isLoading = true
      axios({
        method: 'POST',
        url: '/api/user/login',
        data: this.credentials
      })
      .then(res => {
        const message = res.data.message
        if (message === 'fail') {
          this.error = true
        } else if (message === 'isOauthUser') {
          this.isOauthUserError = true;
        } else if (message === 'temp') {
          this.userIdx = res.data.userIdx
          this.$store.state.drawEmailAuth = true
        } else {
          const userData = {
            ...res.data.userDto,
            'token': res.headers.authorization,
            'tags': res.data.tags
          }
          localStorage.setItem('userData', JSON.stringify(userData))
          this.$store.commit('LOGIN', userData)
          this.$router.push({ name: 'main'})
        }
        this.isLoading = false
      })
      .catch(err => {
        console.log(err)
      })
    },
    drawModal: function () {
      this.$store.state.drawFindPassword = true
    },
    onSuccess: function (googleUser) {
      // eslint-disable-next-line
      this.isOauthUserError = false;
      this.googleUser = googleUser.getBasicProfile();
      const id_token = googleUser.getAuthResponse().id_token;
      this.oauthLogin(id_token);
    },
    onFailure: function (error) {
      // eslint-disable-next-line
      console.log(error);
    },
    oauthLogout: function () {
      const authInst = window.gapi.auth2.getAuthInstance();
      authInst.signOut();
    },
    oauthLogin: function (id_token) {
      axios({
        method: 'POST',
        url: '/api/user/oauth-login',
        data: id_token
      })
      .then(res => {
        const message = res.data.message
        if (message === 'isNotOauthUser') {
          // 일반 회원으로 이미 가입된 이메일
          this.isNotOauthUserError = true;
          this.oauthLogout();
        } else if (message === 'null') {
          // 가입되지 않은 회원으로 회원 가입 페이지로 이동
          this.$router.push({ name: "oauthSignup", params: { email: res.data.email } })
        } else if (message === 'success') {
          const userData = {
            ...res.data.userDto,
            'token': res.headers.authorization,
            'tags': res.data.tags
          }
          localStorage.setItem('userData', JSON.stringify(userData))
          this.$store.commit('LOGIN', userData)
          this.$router.push({ name: 'main'})
        } else {
          this.error = true
        }
      })
      .catch(err => {
        console.log(err)
        this.oauthLogout();
      })
    },
  },
  computed: {
    emailRules: function () {
      return [(v) => /.+@.+\..+/.test(v) || "E-mail이 유효하지 않습니다."]
    },
    passwordRules: function () {
      return [(v) => !!v]
    }
  },
  mounted: function () {
    if (this.$store.state.config.Authorization) {
      this.$router.push({ name: 'main' })
    }
    window.gapi.signin2.render('my-signin2', {
      scope: 'profile email',
      width: 260,
      height: 50,
      longtitle: true,
      theme: 'dark',
      onsuccess: this.onSuccess,
      onfailure: this.onFailure,
    });
  }
};
</script>

<style>
#login h2 {
  font-family: 'Jua', sans-serif;
  margin-bottom: 0;
}

#login a, #login p {
  text-decoration: none;
  font-family: 'Noto Sans KR', sans-serif;
}

#login button img {
  height: 20px;
  position: absolute;
  left: 0;
}

#login {
  width: 360px;
}

#loginForm {
  padding: 2rem 3rem 1.5rem;
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
</style>
