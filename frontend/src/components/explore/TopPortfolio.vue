<template>
  <div v-if="portfolioList" id="top-portfolio">
    <div v-if="portfolioList[1]" class="explore-portfolio">
      <img src="@/assets/explore/portfolio/silver.png" alt="silver">
      <PortfolioCard :feed="portfolioList[1]" :idx="1"/>
    </div>
    <div>
      <div v-if="portfolioList[0]" class="explore-portfolio">
        <img src="@/assets/explore/portfolio/gold.png" alt="gold">
        <PortfolioCard :feed="portfolioList[0]" :idx="0"/>
      </div>
      <img src="@/assets/explore/portfolio/person.png" alt="person" class="w-100">
    </div>
    <div v-if="portfolioList[2]" class="explore-portfolio">
      <img src="@/assets/explore/portfolio/bronze.png" alt="bronze">
      <PortfolioCard :feed="portfolioList[2]" :idx="2"/>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import PortfolioCard from './PortfolioCard.vue'

export default {
  name: 'TopPortfolio',
  components: {
    PortfolioCard
  },
  props: {
    selectedKeyword: {
      type: String
    }
  },
  data: function () {
    return {
      portfolioList: null
    }
  },
  methods: {
    getTopPortfolio: function () {
      axios({
        method: 'GET',
        url: '/api/portfolio/explore'
      })
      .then(res => {
        this.portfolioList = res.data.portfolio.slice(0, 3)
      })
    },
    getKeywordPortfolio: function () {
      axios({
        method: 'GET',
        url: `/api/portfolio/search/${this.selectedKeyword}`
      })
      .then(res => {
        const portfolioList = res.data.portfolio
        portfolioList?.length >= 3 ? this.portfolioList = portfolioList.slice(0, 3) : this.getTopPortfolio()
      })
    }
  },
  created: function () {
    this.getKeywordPortfolio()
  }
}
</script>

<style>
#top-portfolio {
  min-width: 940px;
  margin: 5rem 10% 0;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5rem;
}

#top-portfolio > div {
  width: 350px;
}

.explore-portfolio {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.explore-portfolio img {
  width: 80px;
  margin-bottom: 1rem;
}

</style>