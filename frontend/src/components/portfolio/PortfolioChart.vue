<template>
  <div>
    <canvas v-if="$route.name === 'portfolioDetail'" id="portfolioChart" width="300" height="300"></canvas>  
    <canvas v-else id="portfolioChart" width="100" height="100"></canvas>  
  </div>
</template>

<script>
import Chart from 'chart.js/auto'

export default {
  name: 'PortfolioChart',
  data: function () {
    return {
      curPortfolio: this.portfolio,
      curAssets: this.assets,
      stocks: [],
      stockPortion: []
    }
  },
  props: {
    portfolio: Object,
    assets: Array,
  },
  mounted: function () {
    // const stocks = []
    // const stockPortion = []
    for (let i = 0; i < this.curAssets.length; i ++) {
      //개별 주식
      this.stocks.push(this.curAssets[i].name)
      //개별 주식 비중 = 개별 주식의 현재가격 / 포트폴리오 현재가치 * 100
      const portion = (this.curAssets[i].curprice* this.curAssets[i].quantity) / this.curPortfolio.cursum * 100
      this.stockPortion.push(portion)
    }

    // dataset.data에서 NaN 오류 발생 (새로고침 시, 마운티드 속성 때문인가)
    const ctx = document.getElementById('portfolioChart')
    const myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: this.stocks,
          datasets: [{
            label: 'portfolio',
            data: this.stockPortion,
            backgroundColor: [
              '#00BFFE',
              '#CE1D28',
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
              //차이점 밑에 두개
              display: true,
              },
            },
          responsive: true,
        }
      })
      console.log(myChart)
  },

}
</script>

<style>

</style>