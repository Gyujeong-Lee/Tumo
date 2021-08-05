<template>
  <v-dialog
    v-model="dialog"
    persistent
    width="410"
  >
    <v-card class="p-3">
      <v-card-title class="text-h5">
        이메일 인증을 해주세요.
      </v-card-title>
      <v-card-text class="pb-2">가입하신 이메일 <span class="fw-bold h6">{{ email }}</span>로 <br>발송된 메일을 확인하신 후 이메일 인증을 해주세요.</v-card-text>
      <v-card-actions class="d-flex flex-column">
        <v-alert v-if="message === 'success'" type="success" class="w-100" dense>메일이 성공적으로 전송되었습니다.</v-alert>
        <v-alert v-else-if="message === 'fail'" type="error" class="w-100" dense>이미 인증이 완료된 계정입니다.</v-alert>
        <div class="d-flex justify-content-between align-items-end w-100">
          <div class="d-flex flex-column">
            <label for="" style="font-size: 0.75em;">혹시 이메일을 받지 못하셨나요??</label>
            <v-btn color="primary" @click="sendAuthEmail" :loading="isLoading" :disabled="message === 'fail'">이메일 재전송</v-btn>
          </div>
          <v-btn
            color="secondary darken-1"
            @click="$store.state.drawEmailAuth = false"
          >
            확인
          </v-btn>
        </div>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'

export default {
  name: 'EmailAuth',
  props: {
    email: {
      type: String
    },
    userIdx: {
      type: Number
    }
  },
  data: function () {
    return {
      isLoading: false,
      message: null,
    }
  },
  methods: {
    sendAuthEmail: function () {
      this.isLoading = true
      this.message = null
      axios({
        method: 'GET',
        url: `/user/resend-confirm-email/${this.userIdx}`
      })
      .then(res => {
        const status = res.status
        if (status === 204) {
          this.message = 'fail'
        } else if (status === 200) {
          this.message = 'success'
        } else {
          alert('오류가 발생했습니다.')
        }
        this.isLoading = false
      })
      .catch(err => {
        console.log(err)
        this.isLoading = false
      })
    }
  },
  computed: {
    dialog: function () {
      return this.$store.state.drawEmailAuth
    }
  }
}
</script>

<style>

</style>