<template>
  <v-sheet 
    :elevation="elevation"
    rounded
    class="portfolioFeed mx-2 my-5"
    @mouseover="elevation=10"
    @mouseleave="elevation=4"
  >
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div class="d-flex align-items-center">
        <img src="@/assets/main/user.png" alt="user_img" style="width: 35px;">
        <div class="d-flex align-items-center">
          <h4 class="mb-0 mx-3">{{ feed.title }}</h4>
          <router-link 
            class="text-secondary nickname" 
            :to="{ name: 'profile', params: { nickname: `${feed.nickname}` }}"
            style="font-size: 1rem;"
          >
            @{{ feed.nickname }}
          </router-link>
        </div>
      </div>
    </div>

    <div class="chartContent">
      <canvas :id="`myChart${idx}`" width="130" height="130"></canvas>
      <div v-if="myChart" class="d-grid chart w-100">
        <div class="row">
          <div class="col">
            <p>전체 투자 가격</p>
            <p>전체 수익률</p>
            <p>목표 수익률</p>
          </div>
          <div class="col" align="right">
            <p>{{ $data.amount.sum }}원</p>
            <p><span :class="{ 'text-danger': $data.amount.percent > 0, 'text-primary': $data.amount.percent < 0 }">{{ $data.amount.percent }}</span> %</p>
            <p><span :class="{ 'text-danger': feed.goal > 0, 'text-primary': feed.goal < 0 }">{{ feed.goal }}</span> %</p>
          </div>
        </div>
        <div class="row mt-3">
          <p class="col-4">주요 개별자산</p>
          <p class="col-5" align="right">매수 가격</p>
          <p class="col" align="right">수익률</p>
        </div>
        <div v-for="(asset, idx) in topAssets" :key="idx" class="row">
          <p class="col-4">{{ asset.stock_code }}</p>
          <p class="col-5" align="right">{{ asset.price }} 원</p>
          <p class="col" align="right"><span :class="{ 'text-danger': asset.percent > 0, 'text-primary': asset.percent < 0 }">{{ asset.percent }}</span> %</p>
        </div>
      </div>
    </div>
  </v-sheet>
</template>

<script>
import axios from 'axios'
import Chart from 'chart.js/auto'

export default {
  name: 'PortfolioFeed',
  props: {
    feed: {
      type: Object
    },
    idx: {
      type: Number
    } 
  },
  data: function () {
    return {
      elevation: 4,
      myChart: false,
      topAssets: [],
    }
  },
  mounted: function () {
    axios({
      method: 'GET',
      url: `/api/portfolio/asset/${this.feed.portfolio_idx}`
    })
    .then(res => {
      const defaultData = {
        amount: {
          sum: 0,
          cursum: 0,
          percent: 0
        },
        Asset: []
      }
      const data = res.data ? res.data : defaultData
      Object.assign(this.$data, data)
      this.topAssets = data.Asset.slice(0, 3)
      const stockData = data.Asset.map(data => {
        return data.stock_code
      })
      const priceData = data.Asset.map(data => {
        return data.price
      })
      return { stockData, priceData }
    })
    .then(res => {
      const ctx = document.getElementById(`myChart${this.idx}`)
      const myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: res.stockData,
          datasets: [{
            label: 'portfolio',
            data: res.priceData,
            backgroundColor: [
              'rgb(255, 99, 132)',
              'rgb(54, 162, 235)',
              'rgb(255, 206, 86)',
              'rgb(75, 192, 192)',
              'rgb(153, 102, 255)',
              'rgb(255, 159, 64)'
            ],
            hoverOffset: 4
          }]
        },
        options: {
          plugins: {
            legend: {
              display: false,
              },
            },
          responsive: false,
        }
      })
      this.myChart = true
      console.log(myChart)
    })
  },
}
</script>

<style>
.portfolioFeed {
  padding: 1rem 0rem;
  transition: 0.5s;
}

.portfolioFeed > * {
  margin: 0% 7%;
}

.portfolioFeed > div:first-child {
  margin: 0rem 1rem;
}

.chart p {
  margin-bottom: 0.2rem;
}

.chart > div:nth-child(2) > p {
  font-size: 0.75rem;
}

.chartContent {
  display: flex;
  align-items: center;
}

.chartContent canvas {
  margin: 0.5rem 2rem 0.5rem 0rem;
}

@media screen and (max-width: 600px) {
  .chartContent {
    flex-direction: column;
  }
  
  .chartContent canvas {
    margin: 0.5rem 0rem 1.5rem;
  }
}
</style>