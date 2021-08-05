<template>
  <tr>
    <td>{{ name }}</td>
    <td>
      <input type="text" v-model="tempInfo.price">
    </td>
    <td>
      <input type="text" v-model="tempInfo.goal">
    </td>
    <td>
      <input type="text" v-model="tempInfo.quantity">
    </td>
    <td>
      <span type="button" @click="confirmInfo">확정</span>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'PortfolioListItem',
  data: function () {
    return {
      tempInfo: {
        price: null,
        goal: null,
        quantity: null,
      },
      ...this.stockInfo,
    }
  },
  props: {
    stockInfo: Object,
  },
  methods: {
    confirmInfo: function () {
      console.log(this.stockInfo.name)
      console.log(this.tempInfo.price)
      this.$store.state.tmpAsset.name = this.stockInfo.name
      this.$store.dispatch("confirmStockInfo", this.tempInfo)
      console.log(this.$store.state.tmpAsset)
      this.$emit('confirm')
    }
  }
}
</script>

<style>
 td {
   width: 20%;
 }
 td input {
   width: 100%;
   border: 1px solid gray;
 }

 td input:focus {
   outline: none;
 }

</style>