<template>
  <v-dialog
    persistent
    width="640"
    v-model="isDrawUpdatePortfolio" 
  >
    <v-card id="updatePortfolio">
      <h1 class="text-center mb-5"><v-icon large color="#00BFFE" class="me-2">mdi-chart-pie</v-icon>Portfolio</h1>
      <v-form
        ref="form"
        v-model="valid"
      >
        <div class="w-100">
          <label for="formTitle">제목</label>
          <v-text-field
            solo
            dense
            counter=20
            :rules="titleRules"
            v-model="data.title" 
            id="formTitle"
          ></v-text-field>
        </div>
        <div class="w-100">
          <label for="formTitle">목표 수익률(%)</label>
          <v-text-field
            solo
            dense
            v-model="data.goal" 
            placeholder="포트폴리오의 목표 수익률"
          ></v-text-field>
        </div>
        <div>
          <label for="content">내용</label>
          <Tiptap v-model="data.content"/>
        </div>
        <br>
        <div class="d-flex justify-content-end mt-5">
          <v-btn class="me-5" color="error" @click="closeModal">작성 취소</v-btn>
          <v-btn color="#00BFFE" :disabled="!valid" @click="submitForm" class="text-white">수정 완료</v-btn>
        </div>
      </v-form>
    </v-card>
  </v-dialog>
</template>   

<script>
import axios from 'axios'
import Tiptap from '@/components/Tiptap.vue'

export default {
  name: 'UpdatePortfolio',
  components: {
    Tiptap,
  },
  data: function () {
    return {
      valid: true,
      isSubmit: false,
      pageNum: 1,
      // axios 요청 보낼 데이터
      data: {
        userIdx: this.portfolio.userIdx,
        title: this.portfolio.title,
        content: this.portfolio.content,
        goal: this.portfolio.goal,
      },
    }
  },
  props: {
    portfolio: Object
  },
  methods: {
    popTag: function (idx) {
      this.data.tags.splice(idx, 1)
    },
    closeModal: function () {
      this.$store.state.drawUpdatePortfolio = false
    },
    submitForm: function () {
      // 포폴 수정 axios 요청
      axios({
        method: 'PUT',
        url: '/api/portfolio/list',
        data: this.data
      })
      .then(res => {
        console.log(res)
        this.$alert("성공적으로 포트폴리오를 수정하였습니다.", "수정 완료", 'success')
      })
      .catch(err => {
        console.log(err)
      })
      // alert
      this.closeModal
    },
  },
  computed: {
    isDrawUpdatePortfolio: function () {
      return this.$store.state.drawUpdatePortfolio
    },
    titleRules: function () {
      return [
        v => !!v || '제목을 적어주세요.',
        v => (v && v.length <= 20) || '제목이 너무 길어요...',
      ]
    },
  },
  created: function () {
    console.log('모달 생성')
    this.data.userIdx = this.$store.state.user_info.id
  }
}
</script>

<style>
#updatePortfolio {
  padding: 5% 7%;
}

#updatePortfolio h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 800;
}

#updatePortfolio label, 
#updatePortfolio span {
  font-family: 'Nanum Gothic', sans-serif;
  font-weight: 700;
}

#updatePortfolio form {
  width: 100%;
  margin-top: 5%;
}

#updatePortfolio form > div > div:first-child {
  margin-right: 5%;
}
  
#updatePortfolio h1 {
  color: #00BFFE;
}
</style>