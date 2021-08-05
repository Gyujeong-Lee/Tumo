<template>
  <v-dialog
    v-model="dialog"
    persistent
    width="450"
    id="deleteAccount"
  >
    <v-card>
      <v-card-title class="text-h5">
        Tumo 계정 삭제
      </v-card-title>
      <v-card-text>Tumo 계정을 삭제합니다. <br>계정 삭제 시 개인 정보는 전부 사라집니다.</v-card-text>
      <v-card-actions class="d-flex flex-column">
        <v-form
          ref="form"
          v-model="valid"
          class="w-100"
        >
          <v-text-field
            dense
            placeholder="삭제를 원하시면 계정 이메일을 입력해주세요."
            class="w-100 px-3"
            :rules="emailRules"
            v-model="email"
          ></v-text-field>
          <v-btn
            dense
            block
            class="my-3"
            color="error darken-1"
            :disabled="!valid"
            @click="deleteAccount"
          >
            Delete
          </v-btn>
          <v-btn
            block
            class="my-3"
            color="primary darken-1"
            @click="$store.state.drawDeleteAccount = false"
          >
            Cancel
          </v-btn>
        </v-form>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'

export default {
  name: 'DeleteAccount',
  data: function () {
    return {
      email: null,
      valid: true,
    }
  },
  methods: {
    deleteAccount: function () {
      const data = {
        userIdx: this.$store.state.user_info.id
      }
      axios({
        method: 'DELETE',
        url: '/api/user/',
        data: data,
      })
      .then(res => {
        console.log(res)
        alert('계정이 삭제되었습니다. ㅜㅜ')
        this.$store.state.drawDeleteAccount = false
        this.$store.commit('LOGOUT')
        this.$router.push({ name: 'Login' })
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
  computed: {
    dialog: function () {
      return this.$store.state.drawDeleteAccount
    },
    emailRules: function () {
      return [
        (v) => /.+@.+\..+/.test(v) || "E-mail이 유효하지 않습니다.",
        (v) => v === this.$store.state.user_info.email || "E-mail이 일치하지 않습니다."
      ]
    },
  }

}
</script>

<style>

</style>