<template>
  <div class="container d-flex justify-center mb-5">
    <div class="d-flex flex-column">
      <img src="@/assets/Login/logo.png" alt="mainlogo" class="w-50 mb-5"/>
      <!-- <img src="../../public/Tumo_Korean.png" alt="CI" class="w-100 mb-5"> -->
      <v-form
        ref="form"
        v-model="valid"
      >
        <!-- 서비스 내 이름 -->
        <v-text-field
          v-model="credentials.name"
          :rules="nameRules"
          :counter="10"
          label="Name"
          required
        ></v-text-field>

        <!-- 이메일 계정 -->
        <v-text-field
          v-model="credentials.email"
          :rules="emailRules"
          label="E-mail"
          required
        ></v-text-field>

        <!-- 비밀번호 -->
        <v-text-field
          v-model="credentials.password"
          :rules="passwordRules"
          label="Password"
          type="password"
          required
        ></v-text-field>

        <!-- 비밀번호 확인 -->
        <v-text-field
          v-model="credentials.passwordConfirmation"
          :rules="confirmationRules"
          label="Password Confirmation"
          type="password"
          required
        ></v-text-field>

        <!-- 관심 키워드 입력 -->
        <v-text-field
          v-model="keyword"
          label="Keyword"
          @keypress.enter="add_keyword"
        ></v-text-field>
        
        <!-- 선택된 키워드 or default -->
        <div id="keyword_items">
          <v-chip 
          v-for="(value, keyword, idx) in credentials.keywords" 
          :key="idx"
          class="me-2"
          color="#00BFFE"
          text-color="white"
          >
          {{ value }}
          {{ keyword }}
          </v-chip>
        </div>

        <!-- 약관 동의 -->
        <v-checkbox
          v-model="checkbox"
          :rules="[v => !!v || 'You must agree to continue!']"
          label="Do you agree?"
          required
        ></v-checkbox>

          <p>회원이신가요? <a href="/">로그인</a></p>        
        <v-btn
          id="cancel_btn"
          class="mr-4 "
          @click="cancel"
        >
          cancel
        </v-btn>
        <v-btn 
          id="signup_btn"    
          @click="signup"
          :disabled="!valid"
          >
          signup
        </v-btn>
      </v-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'signup',
    components: {
    },    
    data: () => {
      return {
        valid: true,
        credentials: {
          name: "",
          email: "",
          password: "",
          passwordConfirmation: "",
          keywords: {
            
          },
        },
        keyword: "",
        
        // 동의 여부
        checkbox: false, 

        // 회원가입 규칙
        nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters',
        ],
        emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        passwordRules: [
        v => !!v || 'password is required',
        ],
        confirmationRules: [
        v => !!v || 'password Confirmation is required',
        ],
       
      }
    },
    methods: {
      // 취소 -> 로그인 페이지로
      cancel: function () {
        this.$router.push('/')
      },
      // sign up axios 요청 보내기
      signup: function () {
      },
      // keyword 추가하기
      add_keyword: function () {
        //serve로 보낼 user data에 추가
        this.credentials.keywords[this.keyword] = true
        this.keyword = ""
      },
    },
  }
</script>

<style scoped>
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
  a {
  text-decoration: none;
  }

</style>