<template>
  <canvas id="portfolioChart"></canvas>
</template>

<script>
import Chart from 'chart.js/auto'

export default {
  name: 'PortfolioChart',
  data: function () {
    return {
      curPortfolio: this.portfolio,
      curAssets: this.assets,
    }
  },
  props: {
    portfolio: Object,
    assets: Array,
  },
  mounted: function () {
    const stocks = []
    const stockPortion = []

    for (let i = 0; i < this.curAssets.length; i ++) {
      //개별 주식
      stocks.push(this.curAssets[i].name)
      //개별 주식 비중 = 개별 주식의 현재가격 / 포트폴리오 현재가치 * 100
      const portion = this.curAssets[i].curprice / this.portfolio.cursum * 100
      stockPortion.push(portion)
    }

    const ctx = document.getElementById('portfolioChart')
    const myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: stocks,
          datasets: [{
            label: 'portfolio',
            data: stockPortion,
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