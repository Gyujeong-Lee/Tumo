<template>
  <div class="container d-flex justify-center my-5 h-100">
    <div id="userUpdate" class="d-flex flex-column align-items-center h-100 fw-bold mt-5">
      <v-form
        ref="form"
        v-model="valid"
        id="userUpdateForm"
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
          <v-icon v-if="name_checked" color="success" class="checkBtn">mdi-check-bold</v-icon>
        </div>

        <!-- 이메일 계정 -->
        <div id="emailInput" class="position-relative">
          <v-text-field
            :value="credentials.email"
            label="E-mail"
            disabled
          ></v-text-field>
        </div>

        <!-- 자기소개 -->
        <v-text-field
          :value="credentials.introduce"
          v-model="credentials.introduce"
          label="Introduce"
        ></v-text-field>

        <!-- 관심 키워드 입력 -->
        <v-text-field
          v-model="keyword"
          label="Hash Tag"
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
            update
          </v-btn>
        </div>
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
        introduce: "",
        email: "",
        keywords: [],
      },
      keyword: "",

      // 중복검사 여부
      name_exist: false,
      name_checked: false,
    }
  },
  // 닉네임 및 이메일 중복 검사 하기 전 조건에 맞는 입력값인지 확인
  watch: {
    credentials: {
      handler: function () {
        // console.log('work')
        if (this.credentials.name && this.credentials.name.length <= 10) {
          if (this.credentials.name === this.$store.state.user_info.nickname) {
            this.name_exist = false
          } else {
            this.name_exist = true
          }
        } else {
          this.name_exist = false
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
        url: `/user/nickname/${this.credentials.name}`
      })
      .then(res => {
        const message = res.data.message
        if (message === "success") {
          this.name_checked = true
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
    // 취소 -> 로그인 페이지로
    cancel: function () {
      this.$router.push('/')
    },
    // sign up axios 요청 보내기
    signup: function () {
      // request에 필요한 data
      const data = {
        introduce: null,
        nickname: this.credentials.name,
        tag: this.credentials.keywords
      }
      // axios 요청
      axios({
        method: 'POST',
        url: '/user/signup',
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
  },
  created: function () {
    this.credentials.name = this.$store.state.user_info.nickname
    this.credentials.email = this.$store.state.user_info.email
    this.credentials.introduce = this.$store.state.user_info.introduce
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

#userUpdate {
  width: 60%;
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

#userUpdateForm {
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

@media screen and (max-width: 599px){
  #userUpdate {
    width: 100%;
  }

  #nickNameInput > button {
    right: 0;
  }

  #emailInput > button {
    right: 0;
  }
}
</style>