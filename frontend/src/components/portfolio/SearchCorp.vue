<template>
  <v-select
  :items="searchCorps"
  dense
  solo
  item-text="name"
  placeholder="선택해주세요"
  v-model="searchCorp"
  @change="selectCorp"
  >
  </v-select>
</template>

<script>
export default {
  name: 'SearchCorp',
  data: function () {
    return {
      searchCorps: this.searchItems,
      searchCorp: '',
    }
  },
  props: {
    searchItems: Array,
    tmpAsset: Object,
  },
  methods: {
    selectCorp: function () {
      this.tmpAsset.name = this.searchCorp
      console.log(this.tmpAsset)
      for (let i = 0; i < this.searchCorps.length; i++) {
          if (this.searchCorps[i].name === this.searchCorp) {
            this.tmpAsset.stock_code = this.searchCorps[i].code
          }
      }
      this.$emit('checked')
    },
  },
}
</script>

<style>

</style>