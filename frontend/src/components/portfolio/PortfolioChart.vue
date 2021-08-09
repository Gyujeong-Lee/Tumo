<script>
// import VueCharts from 'vue-chartjs'
import { Pie } from 'vue-chartjs'

export default {
  extends: Pie,
  name: 'PortfolioChart',
  data: function () {
    return {
      curPortfolio: this.portfolio,
      curAssets: this.assets,
      chartdata: {
        // 개별 자산 목록
        labels: ['삼성전자', 'SK'],
        datasets: [
          {
            // 원 바깥 데이터
            label: 'Data One',
            backgroundColor: '#9575CD',
            // 자산의 비중, 자산의 수익률
            data: [40, 20]
          },{
            //원 안쪽 데이터
            label: 'Data Two',
            backgroundColor: '#00BFFE',
            // 자산의 비중, 자산의 수익률
            data: [30, 40]
          }
        ],
      }
    }
  },
  props: {
    portfolio: Object,
    assets: Array,
  },
  methods: {

  },
  mounted () {
    // for (let i = 0; i < this.curAssets.length; i++) {
    //   this.chartdata.labels.push(this.curAssets[i].name)
    //   const dataset = {
    //     label: this.curAssets[i].name,
    //     data: [this.curAssets[i].curprice, this.curAssets[i].percent],
    //   }
    //   if (i === 0) {
    //     dataset.backgroundColor = '#00BFFE'
    //   } else if (i === 1) {
    //     dataset.backgroundColor = '#CE1D28'
    //   } else {
    //     // 추가 색상 결정해야 함
    //     dataset.backgroundColor = '#9575CD'
    //   }

    //   this.chartdata.datasets.push(dataset)
    // }
    this.renderChart(this.chartdata, this.options)
  },

}
</script>

<style>

</style>