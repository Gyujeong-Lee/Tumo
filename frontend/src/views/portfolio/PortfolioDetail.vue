<template>
  <div class="d-flex justify-content-center">
    <v-sheet 
      elevation="8"
      rounded
      id="portfolioDetail"
      class="p-5"
      width="auto"
      height="auto"
    >
    <div class="d-flex justify-content-center">
      <div class="d-flex flex-column">
        <h1>Portfolio</h1>
        <h2>{{ portfolio.title }}</h2>
        <PortfolioChart v-if="assets.length && Object.keys(portfolio).length" :portfolio="portfolio" :assets="assets" />
      </div>
      <div class="d-flex align-center">
        <div class="d-flex flex-column border p-2" id="portfolioInfo">
          <p>총 자산 : {{ amount.cursum }}원</p>
          <p>현재 수익률 : {{ amount.percent }}%</p>
          <p>목표 수익률 : {{ portfolio.goal }}%</p>
          <p>Tag</p>
        </div>
      </div>
    </div>
    <div id="portfolioPortion">
      <h3>Portion</h3>
      <div class="d-flex flex-column border p-2" id="assetInfo">
        <div id="domesticStock">
          <!-- 추후 국내, 해외 주식 비중 추가할 예정 -->
          <h5>국내 주식(100%)</h5>
          <div class="d-flex">
            <!-- 여기에 종목 이름 -->
            <div v-for="(asset, idx) in assets" :key="idx">
              <p class="mt-1 mb-0" style="font-weight:bolder" :class="{ 'text-danger': asset.percent > 0, 'text-primary': asset.percent < 0 }">{{ asset.name }} ({{asset.curprice / portfolio.cursum * 100}}%)</p>
              <ul>
                <li>
                  목표 가격 : {{ asset.goal }}원
                </li>
                <li>
                  현재 가격 : {{ asset.curprice }}원
                </li>
                <li v-if="asset.percent>0" class="text-danger">
                  수익률 : {{ asset.percent }}%
                </li>
                <li v-else style="color:#00BFFE">
                  수익률 : {{ asset.percent }}%
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div id="foreignStock">
          <h5>해외 주식(0%)</h5>
        </div>
      </div>
    </div>
    <div id="portfolioDescription">
      <h3>Description</h3>
      <p>{{ portfolio.content }}</p>
    </div>
    </v-sheet>
  </div>
</template>

<script>
// import Chart from 'chart.js'
import axios from 'axios'
import PortfolioChart from '@/components/portfolio/PortfolioChart.vue'

export default {
  name: 'PortfolioDetail',
  data: function () {
    return {
      userIdx: this.$route.params.userIdx,
      portfolioIdx: this.$route.params.portfolioIdx,
      portfolio: {},
      // 수정 보낼 때 삭제해야 할 데이터 portion
      assets: [],
      amount: {}
    }
  },
  components: {
    PortfolioChart
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/api/portfolio/list/${this.userIdx}`
    })
    .then(res => {
      for (const i in res.data.portfolio) {
        if (res.data.portfolio[i].portfolio_idx == this.portfolioIdx) {
          this.portfolio = res.data.portfolio[i]
          this.$store.state.selectedPortfolio = this.portfolio
          // console.log(this.portfolio)
        }
      }
    })
    .catch(err => {
      console.log(err)
    })
    axios({
      method: 'GET',
      url: `/api/portfolio/asset/${this.portfolioIdx}`
    })
    .then(res => {
      console.log(res)
      res.data.amount.percent = res.data.amount.percent / 100
      for (let i=0; i < res.data.Asset.length; i++) {
        res.data.Asset[i].percent = res.data.Asset[i].percent / 100
      }
      this.assets = res.data.Asset
      this.amount = res.data.amount
      this.$store.state.portfolioAssets = this.portfolio.assets
      this.$store.state.portfolioAmount = this.portfolio.amount
      //amount도 담아야 함.
      // console.log(this.assets)
    })
    .catch(err => {
      console.log(err)
    })
  },
}
</script>

<style scoped>
  #portfolioInfo {
    border: black;
    border-width: 2px;
    margin-left: 1rem;
  }
  #assetInfo {
    border: black;
  }

  #portfolioPortion {
    margin-top: 1rem;
  }

  #portfolioDescription {
    margin-top: 1rem;  
  }
  .plus {
    color:'#CE1D28'
  }
  .minus {
    color:'#00BFFE'
  }
  ul {
    list-style: none;
  }
</style>