<template>
  <div v-if="resultExist">
    <h3>기업 검색 결과</h3>
    <div class="d-flex flex-row my-autoss">
      <v-card elevation="2" shaped v-for="(company, idx) in companies" :key="idx" class="me-3">
        <v-card-title>{{ company.name }}</v-card-title>
      </v-card>
    </div>
  </div>
  <div v-else>
    <h3>기업 검색 결과가 없습니다...</h3>
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
      url: `/api/company/search/${this.$route.params.keyword}/1`
    })
    .then(res => {
      // console.log(res)ㄴ
      if (res.request.status === 204) {
        console.log('자료 x')
      } else {
        this.resultExist = true
        // 응답 담기
        // this.companies = res.data.companies
      }
    })
    .catch(err => {
      console.log(err)
    })
  },

}
</script>