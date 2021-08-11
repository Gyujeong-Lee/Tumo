<template>
  <div v-if="resultExist">
    <h4>기업 검색 결과</h4>
    <div class="d-flex flex-row my-auto">
      <v-card elevation="2" shaped v-for="(company, idx) in companies" :key="idx" class="me-3" @click="moveToDetail(company.name)">
        <v-card-title>{{ company.name }}</v-card-title>
        <v-card-subtitle>{{ company.industry }}</v-card-subtitle>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h4 style="color:#CE1D28">기업 검색 결과가 없습니다...</h4>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CorpSearch',
  data: function () {
    return {
      searchWord: this.searchItem,
      resultExist: false,
      pageNum: 0,
      companies: [
        {
            "code" : "1234",
            "name" : "삼성중공업",
            "industry" : "공업",
            "type" : "KOSPI"
        },
        {
            "code" : "1234",
            "name" : "삼성전자",
            "industry" : "전자",
            "type" : "KOSPI"
        }
    ],
  }
},
  props: {
    searchItem: String,
  },
  created: function () {
    // console.log(this.$route.params.keyword)
    axios({
      method: 'GET',
      url: `/api/company/search/${this.$route.params.keyword}`
    })
    .then(res => {
      console.log(res)
      if (res.request.status === 204) {
        console.log('자료 x')
      } else {
        this.resultExist = true
        // 응답 담기
        this.companies = res.data
      }
    })
    .catch(err => {
      console.log(err)
    })
  },
  methods: {
    moveToDetail: function (corpName) {
      console.log(corpName)
      // 기업 정보 페이지로 이동
      // this.$router.push({name: corporationDetail, params: {}})
    },
  }

}
</script>