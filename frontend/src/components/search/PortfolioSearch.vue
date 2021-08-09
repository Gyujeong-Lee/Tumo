<template>
  <div v-if="resultExist">
    <h3>포트폴리오 검색 결과</h3>
    <div class="d-flex flex-row my-autoss">
      <v-card elevation="2" shaped v-for="(portfolio, idx) in portfolios" :key="idx" class="me-3">
        <v-card-title>{{ portfolio.title }}</v-card-title>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h3>포트폴리오 검색 결과가 없습니다...</h3>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'PortfolioSearch',
  data: function () {
    return {
      searchWord: this.searchItem,
      // 출력 확인을 위해 true로 바꿔놓음
      resultExist: true,
      portfolios: [ 
        {
          "user_idx" : 1,
          "nickName" : "이규빈",
          "portfolio_idx" : 1,
          "title" : "포트폴리오1",
          "contents" : "내용",
          "goal" : 10
        },
        {
          "user_idx" : 1,
          "nickName" : "이규빈",
          "portfolio_idx" : 2,
          "title" : "포트폴리오2",
          "contents" : "내용2",
          "goal" : 10
        }
      ],
    }
  },
  props: {
    searchItem: String,
  },
  created: function () {
    // 현재 오류 출력 back 작업 후 해결 예정
    axios({
      method: 'GET',
      url: `/api/portfolio/search/${this.$route.params.keyword}/1`
    })
    .then(res => {
      // console.log(res)
      if (res.request.status === 204) {
        console.log('자료 x')
      } else {
        this.resultExist = true
        // 응답 담기
        // this.portfolios = res.data.portfolios
      }
    })
    .catch(err => {
      console.log(err)
    })
  },

}
</script>