<template>
  <v-dialog
    persistent
    width="640"
    v-model="isDrawCreatePortfolio" 
  >
    <v-card id="createPortfolio">
      <h1 class="text-center mb-5"><v-icon large color="#00BFFE" class="me-2">mdi-chart-pie</v-icon>Portfolio</h1>
      <v-form
        ref="form"
        v-model="valid"
      >
        <div class="w-100">
          <label for="formTitle">제목</label>
          <v-text-field
            solo
            dense
            counter=20
            :rules="titleRules"
            v-model="data.title" 
            id="formTitle"
          ></v-text-field>
        </div>
        <div class="w-100">
          <label for="formTitle">목표 수익률(%)</label>
          <v-text-field
            solo
            dense
            v-model="data.goal" 
            placeholder="포트폴리오의 목표 수익률"
          ></v-text-field>
        </div>
        <div>
          <label for="content">내용</label>
          <Tiptap v-model="data.content"/>
        </div>
        <br>
        <!-- 상품 검색 -->
        <div class="d-flex flex-column flex-sm-row align-items-center">
          <div>
            <label for="stockType">상품 종류</label>
            <v-select
              :items="items"
              dense
              solo
              v-model="type"
              id="stockType"
            ></v-select>
          </div>
          <div class="w-100" v-if="!searchItems.length">
            <label for="searchStock">검색</label>
            <v-text-field
              id="searchStock"
              solo
              dense
              counter=20
              placeholder="Enter"
              v-model="searchItem"
            ></v-text-field>
          </div>
          <div class="w-100" v-else>
            <label for="searchStock">검색</label>
            <SearchCorp v-if="searchItems.length" 
            :searchItems="searchItems" 
            :tmpAsset="tmpAsset"
            v-on:checked="canSubmit"/>
            
          </div>
          <v-btn 
          color="#00BFFE" 
          @click="searchStock" 
          class="ms-2 text-white"
          v-if="!isSubmit"
          >검색</v-btn>  
          <v-btn color="error" 
          @click="addStock" 
          class="ms-2"
          v-else
          >추가</v-btn>  
        </div>
        <!-- 선택한 종목 담기 -->
        <div>
          <v-simple-table class="w-100 border">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">
                    상품명
                  </th>
                  <th class="text-center">
                    매수 가격
                  </th>
                  <th class="text-centers">
                    목표 가격
                  </th>
                  <th class="text-center">
                    수량(주)
                  </th>
                </tr>
              </thead>
              <tbody>
                <PortfolioAssetList v-for="(asset, idx) in data.assets" :key="idx" :asset="asset"/>
              </tbody>
            </template>
          </v-simple-table>
        </div>
        <div class="d-flex justify-content-end mt-5">
          <v-btn class="me-5" color="error" @click="closeModal">작성 취소</v-btn>
          <v-btn color="#00BFFE" :disabled="!valid" @click="submitForm" class="text-white">작성 완료</v-btn>
        </div>
      </v-form>
    </v-card>
  </v-dialog>
</template>   

<script>
import axios from 'axios'
import Tiptap from '@/components/Tiptap.vue'
import PortfolioAssetList from '../../components/portfolio/PortfolioAssetList.vue'
import SearchCorp from '../../components/portfolio/SearchCorp.vue'

export default {
  name: 'CreatePortfolio',
  components: {
    Tiptap,
    PortfolioAssetList,
    SearchCorp,
  },
  data: function () {
    return {
      valid: true,
      isSubmit: false,
      inputTag: '',
      searchItem: '',
      searchItems: [],
      pageNum: 1,
      items: ['국내주식', '해외주식', '국내채권', '해외채권'],
      type: "국내주식",
      // 요청 보내기 전 임시 데이터 뭘 넣을 수 있을 지 몰라 object로 만듬
      tmpAsset: {
        name: '',
        stock_code: null,
        price: null,
        goal: null,
        quantity: null,
      },
      // axios 요청 보낼 데이터
      data: {
        userIdx: null,
        title: null,
        content: null,
        goal: null,
        assets: [],
      },

    }
  },
  methods: {
    popTag: function (idx) {
      this.data.tags.splice(idx, 1)
    },
    closeModal: function () {
      this.$store.state.drawCreatePortfolio = false
    },
    submitForm: function () {
      for (let i = 0; i < this.data.assets.length; i++) {
       delete this.data.assets[i].name
      }
      console.log(typeof(this.data.goal))
      // 포폴 제작 axios 요청
      axios({
        method: 'POST',
        url: '/api/portfolio/list',
        data: this.data
      })
      .then(res => {
        console.log(res)
        this.closeModal()
        this.$alert("성공적으로 포트폴리오가 작성되었습니다.", "작성 완료", 'success')
        .then(() => {
          if (this.$route.name === 'main') {
            this.$router.go(this.$router.currentRoute)
          }
        })
      })
      .catch(err => {
        console.log(err)
      })
      // alert
      this.closeModal
    },
    searchStock: function () {      
      axios({
        method: 'GET',
        url: `/api/company/search/${this.searchItem}`,
      })
      .then(res => {
        // console.log(res)
        const searchResult = res.data
        this.searchItems = searchResult
      })
      .catch(err => {
        console.log(err)
      })
    },
    addStock: function () {
      // 이름과 코드
      // 포트폴리오에 자산 추가
      this.isSubmit = false
      // this.tmpAsset.name = this.searchItem
      //딥 카피
      let temp = JSON.parse(JSON.stringify(this.tmpAsset))
      this.data.assets.push(temp)
      this.searchItem = ''
      this.searchItems = []
      console.log(this.data.assets)
    },
    canSubmit: function () {
      this.isSubmit = true
    }
  },
  computed: {
    isDrawCreatePortfolio: function () {
      return this.$store.state.drawCreatePortfolio
    },
    titleRules: function () {
      return [
        v => !!v || '제목을 적어주세요.',
        v => (v && v.length <= 20) || '제목이 너무 길어요...',
      ]
    },
  },
  created: function () {
    this.data.userIdx = this.$store.state.user_info.id
  }
}
</script>

<style>
#createPortfolio {
  padding: 5% 7%;
}

#createPortfolio h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 800;
}

#createPortfolio label, 
#createPortfolio span {
  font-family: 'Nanum Gothic', sans-serif;
  font-weight: 700;
}

#createPortfolio form {
  width: 100%;
  margin-top: 5%;
}

#createPortfolio form > div > div:first-child {
  margin-right: 5%;
}
  
#createPortfolio h1 {
  color: #00BFFE;
}
</style>