<template>
  <div class="container d-flex justify-center">
    <div class="d-flex flex-column">
      <img src="../../public/Tumo_Korean.png" alt="CI" width="300" class="mb-5">
      <h1 class="mb-3">가입하기</h1>
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
      >
        <!-- 서비스 내 이름 -->
        <v-text-field
          v-model="name"
          :rules="nameRules"
          :counter="10"
          label="Name"
          required
        ></v-text-field>

        <!-- 이메일 계정 -->
        <v-text-field
          v-model="email"
          :rules="emailRules"
          label="E-mail"
          required
        ></v-text-field>

        <!-- 비밀번호 -->
        <v-text-field
          v-model="password"
          :rules="passwordRules"
          label="Password"
          required
        ></v-text-field>

        <!-- 비밀번호 확인 -->
        <v-text-field
          v-model="passwordConfirmation"
          :rules="confirmationRules"
          label="Password Confirmation"
          required
        ></v-text-field>

        <!-- 관심 키워드 -->
        <v-select
          v-model="select"
          :rules="[v => !!v || 'Item is required']"
          :keywords="keywords"
          label="Keyword"
          required
        ></v-select>

        <!-- 약관 동의 -->
        <v-checkbox
          v-model="checkbox"
          :rules="[v => !!v || 'You must agree to continue!']"
          label="Do you agree?"
          required
        ></v-checkbox>

        <v-btn
          class="mr-4"
          @click="submit"
        >
          submit
        </v-btn>
        <v-btn @click="clear">
          clear
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
        name: "",
        nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters',
        ],
        email: "",
        emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        password: "",
        passwordRules: [
        v => !!v || 'password is required',
        // v => /.+@.+\..+/.test(v) || 'E-mail must be valid', 비밀번호 규칙
        ],
        passwordConfirmation: "",
        confirmationRules: [
        v => !!v || '비밀번호 확인 is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        select: null,
        keywords: [
          'Item 1',
          'Item 2',
          'Item 3',
          'Item 4',
        ],
        checkbox: false,        
      }
    },
    methods: {
      validate () {
        this.$refs.form.validate()
      }
    },
  }
</script>
