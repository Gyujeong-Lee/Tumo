<template>
  <v-dialog
    v-model="dialog"
    persistent
    width="500"
  >
    <v-card class="p-3" v-if="!isEmailChecked">
      <v-card-title class="text-h5">
        이메일을 입력해 주세요.
      </v-card-title>
      <v-card-text>Tumo 가입 때 사용한 이메일을 입력해 주세요. <br>해당 메일 주소로 임시 비밀번호를 보내드립니다.</v-card-text>
      <v-card-actions class="d-flex flex-column">
        <v-form
          ref="form"
          v-model="valid"
          class="w-100 d-flex flex-column align-items-center"
        >
          <v-text-field
            dense
            label="이메일"
            :rules="emailRules"
            v-model="email"
            class="w-100 px-2"
          ></v-text-field>
          <v-alert v-if="isError" text type="error" dense class="w-100">존재하지 않는 계정입니다.</v-alert>
          <div class="d-flex justify-content-between w-100">
            <v-btn
              color="error darken-1"
              @click="$store.state.drawFindPassword = false"
            >
              Cancel
            </v-btn>
            <v-btn
              color="primary darken-1"
              :disabled="!valid"
              :loading="isLoading"
              @click="findPassword"
            >
              Check
            </v-btn>
          </div>
        </v-form>
      </v-card-actions>
    </v-card>
    <v-card v-else class="d-flex flex-column align-items-center pb-5">
      <img src="@/assets/user/email.png" alt="email" class="w-50">
      <v-card-title class="text-h5">
        성공적으로 메일을 전송하였습니다.
      </v-card-title>
      <v-card-text class="text-center">보내드린 임시 비밀번호로 로그인 하신 후, 비밀번호를 변경해 주세요.</v-card-text>
      <v-btn
        color="primary darken-1"
        @click="$store.state.drawFindPassword = false"
        class="w-75"
      >
        Confirm
      </v-btn>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'

export default {
  name: 'FindPassword',
  data: function () {
    return {
      valid: true,
      email: null,
      isEmailChecked: false,
      isError: false,
      isLoading: false,
    }
  },
  methods: {
    findPassword: function () {
      this.isLoading = true
      axios({
        method: 'GET',
        url: `/api/user/findpwd/${this.email}`
      })
      .then(res => {
        const message = res.data.message
        if (message === "fail") {
          this.isError = true
        } else {
          this.isEmailChecked = true
        }
        this.isLoading = false
      })
      .catch(err => {
        console.log(err)
      })
      
    }
  },
  computed: {
    dialog: function () {
      return this.$store.state.drawFindPassword
    },
    emailRules: function () {
      return [(v) => /.+@.+\..+/.test(v) || "E-mail이 유효하지 않습니다."]
    },
  }
}
</script>

<style>

</style>