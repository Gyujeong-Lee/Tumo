<template>
  <div id="highest_block" class="container d-flex flex-column" >
    <h1>Search</h1>
    <div>
      <h2>User</h2>
      <UserSearch v-if="searchItem" :searchItem="searchItem" />
    </div>
    <div>
      <h2>Corp</h2>
      <CorpSearch v-if="searchItem" :searchItem="searchItem" />
    </div>
    <div>
      <h2>PortfolioSearch</h2>
      <PortfolioSearch v-if="searchItem" :searchItem="searchItem" />
    </div>
    <hr>
    <div>
      <h2>Feed</h2>
      <FeedSearch v-if="searchItem" :searchItem="searchItem"/>
    </div>
  </div>
</template>

<script>
import UserSearch from '@/components/search/UserSearch.vue'
import CorpSearch from '@/components/search/CorpSearch.vue'
import PortfolioSearch from '@/components/search/PortfolioSearch.vue'
import FeedSearch from '@/components/search/FeedSearch.vue'


export default {
  name: 'SearchResult',
  data: function () {
    return {
    }
  },
  created: function () {
    // 유저 검색
    // 기업 검색
    // 피드 검색(default) + 포폴 검색(tab 클릭 시)
  },
  components: {
    UserSearch,
    CorpSearch,
    FeedSearch,
    PortfolioSearch,
  },
  computed: {
    searchItem: function () {
     return this.$route.params.keyword
    }
  }
}
</script>

<style scoped>
 #highest_block {
   padding-top: 3rem;
 }
 h1, h2, p, span {
   color: #00BFFE;
 }
 div {
   margin-bottom: 1rem;
 }
</style>