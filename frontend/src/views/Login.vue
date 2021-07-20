<template>
  <div class="container d-flex justify-content-center align-items-center h-100">
    <div class="d-flex flex-column align-items-center">
      <img src="@/assets/Login/logo.png" alt="mainlogo" class="w-100 mb-5"/>
      <div class="d-flex flex-column border rounded-3" id="loginForm">
        <v-form ref="form" v-model="valid">
          <v-text-field
            v-model="credentials.email"
            placeholder="E-mail"
            :rules="emailRules"
            label="E-mail"
            type="email"
          ></v-text-field>
          <v-text-field
            v-model="credentials.password"
            placeholder="password"
            :rules="passwordRules"
            label="password"
            type="password"
            class="pt-0"
          ></v-text-field>
          <v-btn :disabled="!valid" color="primary" class="w-100">로그인</v-btn>
        </v-form>
        <a href="#">비밀번호를 잊으셨나요?</a>
        <p class="my-auto">계정이 없으신가요? <a href="#">가입하기</a></p>
        <hr>
        <v-btn color="error"><img src="@/assets/Login/google.png" alt="googleIcon">Google 로그인</v-btn>
        <v-btn color="success"><img src="@/assets/Login/naver.png" alt="naverIcon">Naver 로그인</v-btn>
        <v-btn color="yellow"><img src="@/assets/Login/kakao.png" alt="kakaoIcon">Kakao 로그인</v-btn>
      </div>
    </div>
    <img src="@/assets/Login/mobile_frame.png" alt="mobile_frame">
  </div>
</template>

<script>
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
};
</script>

<style scoped>
@media screen and (max-width: 768px){
  #loginForm {
    margin-left: 10%;
    margin-right: 10%;
    padding-left: 3rem;
    padding-right: 3rem;
    padding-top: 2rem;
    padding-bottom: 1.5rem;
    width: 100%;
  }

  .container > img {
    display: none;
  }
}

@media screen and (min-width: 768px){
  #loginForm {
    padding-left: 3rem;
    padding-right: 3rem;
    padding-top: 2rem;
    padding-bottom: 1.5rem;
    width: 100%;
  }
  
  .container > img {
    margin-left: 15%;
    width: 260px;
  }
}

#loginForm > * {
  margin-bottom: 1rem;
  text-align: center;
}

.container > div {
  width: 360px;
}

a {
  text-decoration: none;
}

button {
  width: 100%;
}

button img {
  height: 20px;
  position: absolute;
  left: 0;
}
</style>
