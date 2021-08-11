<template>
  <v-dialog
  persistent
  width="640"
  v-model="isDrawUpdateAssets" 
  >
    <v-card id="updateAssets">
      <h1 class="text-center mb-5"><v-icon large color="#00BFFE" class="me-2">mdi-chart-pie</v-icon>개별 자산</h1>
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
              <UpdateAssetList v-for="(asset, idx) in myAssets" :key="idx" :asset="asset" :portfolioIdx="myAssets[0].portfolio_idx"/>
            </tbody>
          </template>
        </v-simple-table>
      </div>

      <div class="d-flex justify-content-end mt-5">
        <v-btn class="me-5" color="error" @click="closeModal">작성 취소</v-btn>
        <v-btn color="#00BFFE" @click="complete" class="text-white">수정 완료</v-btn>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'
import SearchCorp from '../portfolio/SearchCorp.vue'
import UpdateAssetList from '../portfolio/UpdateAssetList.vue'

export default {
  name: 'UpdateAssets',
  data: function () {
    return {
      userId: this.userIdx,
      isSubmit: false,
      inputTag: '',
      searchItem: '',
      searchItems: [],
      pageNum: 1,
      items: ['국내주식', '해외주식', '국내채권', '해외채권'],
      type: "국내주식",
      myAssets: this.assets,
      tmpAsset: {
        name: '',
        stock_code: null,
        price: null,
        goal: null,
        quantity: null,
      },
    }
  },
  props: {
    assets: Array,
    userIdx: String,
  },
  components: {
    UpdateAssetList,
    SearchCorp
  },
  computed: {
    isDrawUpdateAssets: function () {
      return this.$store.state.drawUpdateAssets
    },
  },
  created: function () {
    console.log('자산 수정 모달 생성')
  },
  methods: {
    popTag: function (idx) {
      this.data.tags.splice(idx, 1)
    },
    closeModal: function () {
      this.$store.state.drawUpdateAssets = false
    },
    complete: function () {
      // alert
      this.closeModal()
    },
    searchStock: function () {      
      //검색
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
      //딥 카피
     let temp = JSON.parse(JSON.stringify(this.tmpAsset))
     this.myAssets.push(temp)
     this.searchItem = ''
     this.searchItems = []
     console.log(this.myAssets)

    },
    canSubmit: function () {
      this.isSubmit = true
    }
  }
}
</script>

<style scoped>
#updateAssets {
  padding: 5% 7%;
}

#updateAssets h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 800;
}

#updateAssets label, 
#updateAssets span {
  font-family: 'Nanum Gothic', sans-serif;
  font-weight: 700;
}

#updateAssets form {
  width: 100%;
  margin-top: 5%;
}

#updateAssets form > div > div:first-child {
  margin-right: 5%;
}
  
#updateAssets h1 {
  color: #00BFFE;
}
</style>