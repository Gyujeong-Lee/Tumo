<template>
  <div class="d-flex justify-content-center">
    <v-sheet 
      elevation="8"
      rounded
      id="portfolioDetail"
      class="p-2"
      width="auto"
      height="auto"
    >
    <div v-if="itsMe" class="d-flex justify-end">
      <div>
        <v-icon color="#00BFFE" class="me-1" @click="drawUpdatePortfolio">mdi-pencil</v-icon>
        <el-popconfirm
          v-if="itsMe"
          @confirm="deletePortfolio"
          confirm-button-type='danger'
          cancel-button-type='primary'
          confirm-button-text='삭제'
          cancel-button-text='취소'
          icon="el-icon-warning"
          icon-color="red"
          title="정말 포트폴리오를 삭제하시겠어요?"
        >
          <v-btn icon slot="reference"><v-icon color="CE1D28">mdi-delete</v-icon></v-btn>
        </el-popconfirm>
        <v-btn v-else icon><v-icon>mdi-comment-multiple-outline</v-icon></v-btn>
      </div>
    </div>
    <div class="d-flex flex-column flex-sm-row justify-content-center">
      <div class="d-flex flex-column">
        <h2 style="color:#CE1D28">Portfolio</h2>
        <h3 style="font-weight:bold" class="my-auto">{{ portfolio.title }}</h3>
        <UpdatePortfolio v-if="Object.keys(portfolio).length" :portfolio="portfolio"/>
        <UpdateAssets v-if="assets.length" :assets="assets" :userIdx="userIdx"/>
        <PortfolioChart v-if="assets.length && Object.keys(portfolio).length" :portfolio="portfolio" :assets="assets" />
      </div>
      <div class="d-flex align-center justify-center">
        <div class="d-flex flex-column border p-2" id="portfolioInfo">
          <p>총 자산 : {{ amount.cursum }}원</p>
          <p style="font-weight:bold">현재 수익률 : {{ amount.percent }}%</p>
          <p>목표 수익률 : {{ portfolio.goal }}%</p>
        </div>
      </div>
    </div>
    <div id="portfolioPortion">
      <h3 style="display:inline">Portion</h3>
      <v-icon v-if="itsMe" color="#00BFFE" class="mb-2" @click="drawUpdateAssets">mdi-pencil</v-icon>
      <div class="d-flex flex-column border pt-2" id="assetInfo">
        <div id="domesticStock">
          <!-- 추후 국내, 해외 주식 비중 추가할 예정 -->
          <h5>국내 주식(100%)</h5>
          <div class="d-grid">
            <div class="row">
              <!-- 여기에 종목 이름 -->
              <div class="col-12 col-sm-4 pe-0" v-for="(asset, idx) in assets" :key="idx">
                <p class="mt-1 mb-0" style="font-weight:bolder" :class="{ 'text-danger': asset.percent > 0, 'text-primary': asset.percent < 0 }">
                {{ asset.name }} ({{(asset.curprice*asset.quantity / portfolio.cursum * 100).toFixed(0)}}%)
                </p>
                <ul class="ps-1">
                  <li>
                    목표 가격 : {{ asset.goal }}원
                  </li>
                  <li>
                    현재 가격 : {{ asset.curprice }}원
                  </li>
                  <li>
                    구매 수량 : {{ asset.quantity }}주
                  </li>
                  <li v-if="asset.percent>0" class="text-danger">
                    수익률 : {{ asset.percent }}%
                  </li>
                  <li v-else-if="!asset.percent">
                    수익률 : {{ asset.percent }}%
                  </li>
                  <li v-else style="color:#00BFFE">
                    수익률 : {{ asset.percent }}%
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div id="foreignStock" class="mt-3">
          <h5>해외 주식(0%)</h5>
        </div>
      </div>
    </div>
    <div id="portfolioDescription">
      <h3>Description</h3>
      <p v-html="portfolio.content"></p>
    </div>
    </v-sheet>
  </div>
</template>

<script>
// import Chart from 'chart.js'
import axios from 'axios'
import PortfolioChart from '@/components/portfolio/PortfolioChart.vue'
import UpdatePortfolio from './UpdatePortfolio.vue'
import UpdateAssets from '../../components/portfolio/UpdateAssets.vue'

export default {
  name: 'PortfolioDetail',
  data: function () {
    return {
      userIdx: this.$route.params.userIdx,
      portfolioIdx: this.$route.params.portfolioIdx,
      portfolio: {},
      // 수정 보낼 때 삭제해야 할 데이터 portion
      assets: [],
      amount: {},
      itsMe: false,
    }
  },
  components: {
    PortfolioChart,
    UpdatePortfolio,
    UpdateAssets,
  },

  created: function () {
    // 본인 인증
    if (this.$store.state.user_info.id == this.$route.params.userIdx) {
      // console.log('check')
      this.itsMe = true
    }
    // 포트폴리오 요청
    axios({
      method: 'GET',
      url: `/api/portfolio/list/${this.userIdx}`
    })
    .then(res => {
      for (const i in res.data.portfolio) {
        if (res.data.portfolio[i].portfolio_idx == this.portfolioIdx) {
          this.portfolio = res.data.portfolio[i]
        }
      }
    })
    .catch((error) => {
      // Error 😨
      if (error.response) {
        if (error.response.status === 500) {
          this.$alert("존재하지 않는 포트폴리오입니다.", "실패", 'error')
          this.$router.go(-1)
        }
      } else if (error.request) {

        console.log(error.request);
      } else {
        console.log('Error', error.message);
      }
      console.log(error.config);
    });
    // 개별 자산 요청 
    axios({
      method: 'GET',
      url: `/api/portfolio/asset/${this.portfolioIdx}`
    })
    .then(res => {
      // console.log(res)
      for (let i=0; i < res.data.Asset.length; i++) {
        res.data.Asset[i].curprice = res.data.Asset[i].curprice.toFixed(0)
        if ( res.data.Asset[i].curprice > 1000) {
          res.data.Asset[i].curprice =  (res.data.Asset[i].curprice).toLocaleString()
        }
        if ( res.data.Asset[i].goal > 1000) {
          res.data.Asset[i].goal = (res.data.Asset[i].goal).toLocaleString()
        }
      }
      this.assets = res.data.Asset
      if (res.data.amount.cursum > 1000) {
        res.data.amount.cursum = (res.data.amount.cursum).toLocaleString()
      }
      this.amount = res.data.amount
      this.$store.state.portfolioAssets = this.portfolio.assets
      this.$store.state.portfolioAmount = this.portfolio.amount
    })
    .catch(err => {
      console.log(err)
    })
  },
  methods: {
    drawUpdatePortfolio: function () {
      //모달 띄우기
      this.$store.state.drawUpdatePortfolio = true
    },
    drawUpdateAssets: function () {
      //모달 띄우기 자산관리
      this.$store.state.drawUpdateAssets = true
    },
    deletePortfolio: function () {
      //삭제
      axios({
        method: 'DELETE',
        url: `/api/portfolio/list/${this.portfolioIdx}`
      })
      .then(res => {
        this.$alert("포트폴리오가 삭제되었습니다.", "삭제 완료", `${res.data.message}`)
        // 이동
        this.$router.push({name: 'main'})
      })
      .catch(err => {
        console.log(err)
      })
    },
    toLocaleString: function (x) {
      return x.toLocaleString()
    },

  }
}
</script>

<style scoped>
  h2, h3 {
    font-family: "Otomanopee One", sans-serif
  }

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