<template>
  <div v-if="portfolios.length">
    <h4>포트폴리오 검색 결과</h4>
    <div class="d-flex flex-row my-auto">
      <v-card :elevation="elevation" shaped v-for="(portfolio, idx) in portfolios" :key="idx" class="me-3">
        <v-card-title type="button" @click="moveToDetail(portfolio)">{{ portfolio.title }}</v-card-title>
        <v-card-subtitle type="button" @click="moveToProfile(portfolio.nickname)">{{ portfolio.nickname }}</v-card-subtitle>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h4 style="color:#CE1D28">포트폴리오 검색 결과가 없습니다...</h4>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'PortfolioSearch',
  data: function () {
    return {
      elevation: 4,
      searchWord: this.searchItem,
      // 출력 확인을 위해 true로 바꿔놓음
      resultExist: true,
      pageNum: 0,
      portfolios: [
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
      url: `/api/portfolio/search/${this.$route.params.keyword}/${this.pageNum}`
    })
    .then(res => {
      // console.log(res)
      if (res.request.status === 200) {
        this.resultExist = true
        // 응답 담기
        this.portfolios = res.data.portfolio
      }
    })
    .catch(err => {
      console.log(err)
    })
  },
  methods: {
    moveToDetail: function (portfolioInfo) {
      // console.log(portfolioInfo)
      // 포트폴리오 상세 페이지로 이동
      this.$router.push({name: 'portfolioDetail', params: {userIdx: `${portfolioInfo.user_idx}`, portfolioIdx: portfolioInfo.portfolio_idx}})
    },
    moveToProfile: function (userName) {
      // 유저 프로필 페이지로 이동 
      // console.log(userName)
      this.$router.push({name: 'profile', params: {nickname: userName}})

    }
  }

}
</script>