<template>
  <div class="container d-flex justify-center my-5 h-100">
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
            @input="name_checked = false"
          >
          </v-text-field>
          <v-btn
          :disabled="!name_exist"
          small
          @click="name_check"
          >중복검사</v-btn>
          <v-icon v-if="name_checked" color="success" class="checkBtn">mdi-check-bold</v-icon>
        </div>

        <!-- 이메일 계정 -->
        <div id="emailInput" class="position-relative">
          <v-text-field
            v-model="credentials.email"
            :rules="emailRules"
            label="E-mail"
            required
            @input="email_checked = false"
          ></v-text-field>
          <v-btn
          :disabled="!email_exist"
          small
          @click="email_check"
          >중복검사</v-btn>
          <v-icon v-if="email_checked" color="success" class="checkBtn">mdi-check-bold</v-icon>
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
        <div class="d-flex align-items-center w-100">
          <v-text-field
            v-model="keyword"
            label="Hash Tag"
            @keypress.enter="add_keyword"
          ></v-text-field>
          <v-btn color="primary" class="ms-5" @click="add_keyword">추가</v-btn>
        </div>
        
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
          :rules="[v => !!v || '동의하셔야 회원가입이 가능합니다.']"
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
            :disabled="!valid || !name_checked || !email_checked"
          >
            signup
          </v-btn>
        </div>
        <p class="text-center py-5 my-3">회원이신가요? <router-link :to="{ name: 'Login' }">로그인</router-link></p>        
      </v-form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

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

      // 중복검사 여부
      name_exist: false,
      name_checked: false,
      email_exist: false,
      email_checked: false,
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
      axios({
        method: 'GET',
        url: `/api/user/nickname/${this.credentials.name}`
      })
      .then(res => {
        const message = res.data.message
        if (message === "success") {
          this.name_checked = true
          this.name_exist = false
          console.log('ok')
        } else {
          alert('이미 존재하는 닉네임입니다.')
          this.name_checked = false
        }
      })
      .catch(err => {
        console.log(err)
      })
    },
    // 이메일 중복검사 axios 요청 보낼 것.
    email_check: function () {
      axios({
        method: 'GET',
        url: `/api/user/email/${this.credentials.email}`
      })
      .then(res => {
        const message = res.data.message
        if (message === "success") {
          this.email_checked = true
        } else {
          alert('이미 존재하는 이메일입니다.')
          this.email_checked = false
        }
      })
      .catch(err => {
        console.log(err)
      })
    },
    // 취소 -> 로그인 페이지로
    cancel: function () {
      this.$router.push('/')
    },
    // sign up axios 요청 보내기
    signup: function () {
      // request에 필요한 data
      const data = {
        email: this.credentials.email,
        introduce: null,
        nickname: this.credentials.name,
        password: this.credentials.password,
        tag: this.credentials.keywords
      }
      // axios 요청
      axios({
        method: 'POST',
        url: '/api/user/signup',
        data: data
      })
      .then(res => {
        const message = res.data.message
        if (message === "success") {
          alert('정상적으로 가입 되었습니다.')
          this.$router.push({ name: 'Login' })
        }
      })
      .catch(err => {
        console.log(err)
      })
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
    },
  },
  computed: {
    // 회원가입 규칙
    nameRules: function () {
      return [
        v => !!v || '닉네임은 필수 입력사항입니다.',
        v => (v && v.length <= 10) || '닉네임은 10자를 넘을 수 없습니다.',
      ]
    },
    emailRules: function () {
      return [
        v => !!v || '이메일은 필수 입력사항입니다.',
        v => /.+@.+\..+/.test(v) || '이메일이 유효하지 않습니다.',
      ]
    },
    passwordRules: function () {
      return [
        v => !!v || '비밀번호는 필수 입력사항입니다.',  
      ]
    },
    confirmationRules: function () {
      return [
        v => !!v || '비밀번호는 필수 입력사항입니다.',
        v => (!!v && v) === this.credentials.password || '비밀번호가 일치하지 않습니다.' 
      ]
    },
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