<template>
  <v-dialog
    v-model="dialog"
    persistent
    width="550"
    id="updatePassword"
  >
    <v-card>
      <v-stepper v-model="e1">
        <!-- step header -->
        <v-stepper-header>
          <v-stepper-step
            :complete="e1 > 1"
            step="1"
          >
            비밀번호 확인
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            :complete="e1 > 2"
            step="2"
          >
            비밀번호 변경
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step step="3">
            변경 완료
          </v-stepper-step>
        </v-stepper-header>

        <!-- step items -->
        <v-stepper-items>
          <!-- step1 -->
          <v-stepper-content step="1">
            <div class="mx-5 pt-5 pb-3">
              <h4 class="text-center">현재 비밀번호를 입력해 주세요.</h4>
              <div class="d-flex align-items-center my-5">
                <v-text-field
                  dense
                  hide-details
                  placeholder="Password"
                  type="password"
                  v-model="password"
                  :disabled="isCheckPassword"
                ></v-text-field>
                <v-btn class="ms-5" color="primary" :disabled="isCheckPassword" @click="checkPassword" :loading="step1Loading">Check</v-btn>
              </div>
              <v-alert v-if="checkResult === 'success'" type="success" dense text>비밀번호가 확인 되었습니다.</v-alert>
              <v-alert v-else-if="checkResult === 'error'" type="error" dense text>비밀번호를 다시 확인해주세요.</v-alert>
            </div>

            <div class="d-flex justify-content-between mx-4">
              <v-btn color="error" @click="$store.state.drawUpdatePassword = false">
                Cancel
              </v-btn>

              <v-btn
                :disabled="!isCheckPassword"
                color="primary"
                @click="e1 = 2"
              >
                Continue
              </v-btn>
            </div>

          </v-stepper-content>

          <!-- step2 -->
          <v-stepper-content step="2">
            <v-form
              ref="form"
              v-model="valid"
            >
              <div class="mx-5 pt-5 pb-3">
                <h4 class="text-center pb-5">변경할 비밀번호를 입력해주세요.</h4>
                <v-text-field
                  label="비밀번호"
                  dense
                  v-model="updatedPassword"
                  :rules="passwordRules"
                  type="password"
                  class="mx-5 my-5"
                ></v-text-field>
                <v-text-field
                  label="비밀번호 확인"
                  v-model="updatedPasswordConfirmation"
                  dense
                  :rules="confirmationRules"
                  type="password"
                  class="mx-5 my-5"
                ></v-text-field>
                <v-alert v-if="checkResult2 === 'error'" type="error" class="mx-4 mb-5" dense text>변경 전과 동일한 비밀번호입니다.</v-alert>
              </div>

              <div class="d-flex justify-content-between mx-5 mb-3">
                <v-btn color="error" @click="$store.state.drawUpdatePassword = false">
                  Cancel
                </v-btn>

                <v-btn
                  color="primary"
                  @click="updatePassword"
                  :disabled="!valid"
                  :loading="step2Loading"
                >
                  Continue
                </v-btn>
              </div>
            </v-form>
          </v-stepper-content>

          <!-- step3 -->
          <v-stepper-content step="3">
            <div class="d-flex flex-column align-items-center">
              <img src="@/assets/user/key.png" alt="key" class="w-25">
              <br>
              <h4 class="text-center">비밀번호가 성공적으로 변경되었습니다.</h4>
              <v-btn @click="$store.state.drawUpdatePassword = false" color="primary" class="w-75 my-5">
                Confirm
              </v-btn>
            </div>
          </v-stepper-content>
        </v-stepper-items>
      </v-stepper>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'

export default {
  name: 'UpdatePassword',
  data: function () {
    return {
      e1: 1,
      valid: true,
      // 기존, 수정할 비밀번호
      password: null,
      updatedPassword: null,
      updatedPasswordConfirmation: null,
      // 비밀번호 확인 여부
      isCheckPassword: false,
      isUpdatePassword: false,
      // 확인 결과
      checkResult: null,
      checkResult2: null,
      // 로딩 표시
      step1Loading: false,
      step2Loading: false,
    }
  },
  methods: {
    checkPassword: function () {
      this.step1Loading = true
      const data = {
        userIdx: this.$store.state.user_info.id,
        password: this.password
      }
      axios({
        method: 'POST',
        url: '/user/checkpassword',
        data: data,
      })
      .then(res => {
        const message = res.data.message
        if (message === "fail") {
          this.checkResult = 'error'
        } else {
          this.isCheckPassword = true
          this.checkResult = 'success'
        }
        this.step1Loading = false
      })
      .catch(err => {
        console.log(err)
        this.step1Loading = false
      })
    },
    updatePassword: function () {
      this.step2Loading = true
      const data = {
        userIdx: this.$store.state.user_info.id,
        password: this.updatedPassword
      }
      axios({
        method: 'PUT',
        url: '/user/password',
        data: data
      })
      .then(res => {
        const message = res.data.message
        if (message === 'fail') {
          this.checkResult2 = 'error'
          this.updatedPassword = null,
          this.updatedPasswordConfirmation = null
        } else {
          this.e1 = 3
        }
      })
      .catch(err => {
        console.log(err)
      })
      this.step2Loading = false
    }
  },
  computed: {
    dialog: function () {
      return this.$store.state.drawUpdatePassword
    },
    passwordRules: function () {
      return [
        v => !!v || '비밀번호는 필수 입력사항입니다.',  
      ]
    },
    confirmationRules: function () {
      return [
        v => !!v || '비밀번호는 필수 입력사항입니다.',
        v => (!!v && v) === this.updatedPassword || '비밀번호가 일치하지 않습니다.' 
      ]
    },

  } 
}
</script>

<style>

</style>