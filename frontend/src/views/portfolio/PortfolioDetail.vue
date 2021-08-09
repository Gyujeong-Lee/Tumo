<template>
  <div class="d-flex justify-content-center">
    <v-sheet 
      elevation="8"
      rounded
      id="portfolioDetail"
      class="p-5"
    >
    <div class="d-flex justify-content-center">
      <div class="d-flex flex-column">
        <h1>Portfolio</h1>
        <h2>{{ portfolio.title }}</h2>
        <PortfolioChart v-if="assets.length" :portfolio="portfolio" :assets="assets" />
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
            <p v-for="(asset, idx) in assets" :key="idx">{{ asset.stock_code }}</p>
            <ul v-for="(asset, idx) in assets" :key="idx+'A'">
              <li>
                목표 가격 {{ asset.goal }}원
              </li>
              <li>
                현재 가격 {{ asset.curprice }}원
              </li>
              <li>
                수익률 {{ asset.percent }}%
              </li>
            </ul>
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
          console.log(this.portfolio)
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
  }
}
</script>

<style scoped>
  #portfolioInfo {
    border: black;
    border-width: 2px;
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
</style>