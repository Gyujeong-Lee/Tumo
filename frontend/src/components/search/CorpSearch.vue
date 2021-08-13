<template>
  <div v-if="companies.length">
    <h4>기업 검색 결과</h4>
    <div class="d-flex flex-column flex-sm-row  my-auto">
      <v-card  :elevation="elevation" shaped v-for="(company, idx) in companies" :key="idx" class="me-3 mt-3" @click="moveToDetail(company.name)">
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
      elevation: 4,
      searchWord: this.searchItem,
      resultExist: false,
      pageNum: 0,
      companies: [
      ],
  }
},
  props: {
    searchItem: String,
  },
  created: function () {
    axios({
      method: 'GET',
      url: `/api/company/search/${this.$route.params.keyword}`
    })
    .then(res => {
      if (res.request.status === 200) {
        this.resultExist = true
        // 응답 담기s
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
      this.$router.push({name: 'corporationDetail', params: {companyName: corpName}})
    },
  }

}
</script>