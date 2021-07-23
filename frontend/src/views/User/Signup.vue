<template>
  <div class="container d-flex justify-center mb-5 h-100">
    <div class="d-flex flex-column align-items-center h-100 fw-bold">
      <img src="@/assets/login/logo.png" alt="mainlogo" class="w-50 mb-5"/>
      <!-- <img src="../../public/Tumo_Korean.png" alt="CI" class="w-100 mb-5"> -->
      <v-form
        ref="form"
        v-model="valid"
        id="signupForm"
      >
        <!-- 서비스 내 이름 -->
        <div id="nickNameInput" class="position-relative">
          <v-text-field
            v-model="credentials.name"
            :rules="nameRules"
            :counter="10"
            label="Name"
            required
          >
          </v-text-field>
          <v-btn
          :disabled="!name_exist"
          small
          @click="name_check"
          >중복검사</v-btn>
        </div>

        <!-- 이메일 계정 -->
        <div id="emailInput" class="position-relative">
          <v-text-field
            v-model="credentials.email"
            :rules="emailRules"
            label="E-mail"
            required
          ></v-text-field>
          <v-btn
          :disabled="!email_exist"
          small
          @click="email_check"
          >중복검사</v-btn>
        </div>

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
          v-for="(keyword, idx) in credentials.keywords" 
          :key="idx"
          class="me-2"
          color="#00BFFE"
          text-color="white"
          close
          @click:close="delete_keyword(keyword)"
          >
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
        <div id="btnGroup" class="my-3">
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
            :disabled="!valid || !name_checked"
            >
            signup
          </v-btn>
        </div>
        <p class="text-center py-5 my-3">회원이신가요? <router-link :to="{ name: 'Login' }">로그인</router-link></p>        
      </v-form>

      <!-- Footer -->
      <p class="text-center fw-bold mt-5">@ All rights reserved by Team Tumo</p>
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
        // 요구사항 만족
        valid: true,

        // 서버와 통신할 데이터
        credentials: {
          name: "",
          email: "",
          password: "",
          passwordConfirmation: "",
          keywords: [],
        },
        keyword: "",
        // 동의 여부
        checkbox: false, 

      // 회원가입 규칙

        // 중복검사 여부
        name_exist: false,
        name_checked: false,
        email_exist: false,
        email_checked: false,

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
    // 닉네임 및 이메일 중복 검사 하기 전 조건에 맞는 입력값인지 확인
    watch: {
      credentials: {
        handler: function () {
          // console.log('work')
          if (this.credentials.name && this.credentials.name.length <= 10) {
            this.name_exist = true
          } else {
            this.name_exist = false
          }
          if (this.credentials.email && /.+@.+\..+/.test(this.credentials.email)) {
            this.email_exist = true
          } else {
            this.email_exist = false
          }
        },
        deep: true
      }
    },
    methods: {
      // 닉네임 중복검사 axios 요청 보낼 것.
      name_check: function () {
        this.name_checked = true
        // checked = true
      },
      // 닉네임 중복검사 axios 요청 보낼 것.
      email_check: function () {
        
      },
      // 취소 -> 로그인 페이지로
      cancel: function () {
        this.$router.push('/')
      },
      // sign up axios 요청 보내기
      signup: function () {
        this.name_checked = false
      },
      // keyword 추가하기
      add_keyword: function () {
        //serve로 보낼 user data에 추가
        if (this.keyword.trim()) {
          this.credentials.keywords.push(this.keyword)
        }
        this.keyword = ""
      },
      // keyword 제거
      delete_keyword: function (keyword) {
        // console.log(keyword)
        const idx_keyword = this.credentials.keywords.indexOf(keyword)
        this.credentials.keywords.splice(idx_keyword, 1)
      }
    },
  }
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

@media screen and (max-width: 500px){
  #btnGroup {
    justify-content: center;
  }
}

@media screen and (min-width: 500px){
  #btnGroup {
    justify-content: flex-end;
  }
}

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

</style>