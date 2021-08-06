<template>
  <v-select
  :items="searchCorps"
  dense
  solo
  item-text="name"
  v-model="searchCorp"
  >
  </v-select>
</template>

<script>
export default {
  name: 'SearchCorp',
  data: function () {
    return {
      searchCorps: this.searchItems,
      searchCorp: this.searchItems[0].name
    }
  },
  props: {
    searchItems: Array,
    tmpAsset: Object,
  },
  methods: {
    
  }
}
</script>

<style>

</style>