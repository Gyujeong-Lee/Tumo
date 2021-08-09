<template>
  <v-sheet 
    elevation="6"
    rounded="xl"
    class="mx-2 my-5"
    height="auto"
    width="auto">
    <!-- 포문이 아니라 하나만 출력할 것임 -->
    <p type="button" @click="moveToDetail(portfolios[0].portfolio_idx)"> {{ portfolios[0].title }} </p>
  </v-sheet>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Portfolio',
  data: function () {
    return {
      userId: this.userIdx,
      // 이 중 하나 정해서 출력해야 함.
      portfolios: [ 
        {
          "portfolio_idx":1,
          "userIdx":1,
          "title":"나의 첫 포트폴리오",
          "content":"처음으로 만든 포트폴리오",
          "goal":10.0
        },
        {
          "portfolio_idx":2,
          "userIdx":1,
          "title":"나의 두번쨰 포트폴리오",
          "content":"처음으로 만든 포트폴리오",
          "goal":10.9
        }
      ],
    }
  },
  props: {
    userIdx: Number,
  },
  created: function () {
    // 포트폴리오 가져오기
    axios({
      method: 'GET',
      url: `/api/portfolio/list/${this.userId}`
    })
    .then(res => {
      // for문 돌려서 가장 수익률이 높은 포트폴리오 가져오기
      console.log(res)
      this.portfolios = res.data.portfolio
    })
    .catch(err => {
      console.log(err)
    })
  },
  methods: {
    moveToDetail: function (idx) {
      console.log(this.userId)
      console.log(idx)
      this.$router.push({name: 'portfolioDetail', params: {userIdx: this.userId, portfolioIdx: idx}})
    }
  }
}
</script>