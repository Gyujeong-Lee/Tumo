<template>
<div>
  <v-sheet 
    :elevation="elevation"
    rounded
    class="articleFeed mx-2 my-5"
    @mouseover="elevation=10"
    @mouseleave="elevation=4"
    height="auto"
    width="auto">
    <h5 type="button" @click="moveToDetail(bestPortfolio.portfolio_idx)" >
      {{ bestPortfolio.title }}
    </h5>
    <div v-if="portfolios.length">
      <PortfolioChart v-if="assets.length && Object.keys(bestPortfolio).length" :portfolio="bestPortfolio" :assets="assets"/>
      <div class="d-flex align-center justify-center mt-3">
        <div class="d-flex flex-column border p-2" id="portfolioInfo">
          <p>총 자산 : {{ amount.cursum }}원</p>
          <p v-if="bestPortfolio.percent > 0" class="text-danger">현재 수익률 : {{ amount.percent / 100 }} %</p>
          <p v-else class="text-primary">현재 수익률 : {{ amount.percent }}%</p>
          <p >목표 수익률 : {{ bestPortfolio.goal }}%</p>
        </div>
      </div>
    </div>
  </v-sheet>
  <v-tabs grow class="p-0">
    <v-tab grow @click="viewMore">
    <span>더보기</span>
   </v-tab>
  </v-tabs>
  <v-sheet
  v-if="clickMore"
  :elevation="elevation"
  rounded
  class="articleFeed mx-2 my-5"
  @mouseover="elevation=10"
  @mouseleave="elevation=4"
  >
    <div class="d-flex align-center mt-3">
      <div class="d-flex flex-column p-2">
        <h5 type="button" class="mb-3">다른 포트폴리오</h5>
        <p type="button" v-for="(portfolio, idx) in portfolios" :key="idx" 
        @click="moveToDetail(portfolio.portfolio_idx)">
          {{ portfolio.title }}
        </p>
      </div>
    </div>
  </v-sheet>
</div>
</template>

<script>
import axios from 'axios'
import * as _ from 'lodash'
import PortfolioChart from  '@/components/portfolio/PortfolioChart.vue'

export default {
  name: 'Portfolio',
  data: function () {
    return {
      clickMore: false,
      userId: this.userIdx,
      elevation: 4,
      // 수익률이 가장 높은 포트폴리오
      bestPortfolio: {},
      // best portfolio asset
      assets: [],
      amount: {},
      portfolios: [],
    }
  },
  components: {
    PortfolioChart,
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
      this.bestPortfolio = _.maxBy(res.data.portfolio, 'percent')
      console.log(this.bestPortfolio)
      //여기부터 개별 자산
      axios({
        method: 'GET',
        url: `/api/portfolio/asset/${this.bestPortfolio.portfolio_idx}`
      })
      .then(res=> {
        console.log(res)
        this.assets = res.data.Asset
        this.amount = res.data.amount
      })
      .catch(err => {
        console.log(err)
      })
      //여기까지
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
    },
    viewMore: function () {
      if (this.clickMore) {
        this.clickMore = false
      } else {
        this.clickMore = true
      }
    }
  }
}
</script>

<style scoped>
  h5 {
    color: #00BFFE;
  }
  p {
    font-weight: bold;
  }
</style>