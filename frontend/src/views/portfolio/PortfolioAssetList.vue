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
      <span type="button" v-if="!isConfirm" @click="confirmInfo">확정</span>
      <span v-else>완료</span>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'PortfolioAssetList',
  data: function () {
    return {
      isConfirm: false,
      tempInfo: {
        price: null,
        goal: null,
        quantity: null,
      },
      ...this.asset,
    }
  },
  props: {
    asset: Object,
  },
  methods: {
    confirmInfo: function () {
      this.asset.price = this.tempInfo.price
      this.asset.goal = this.tempInfo.goal
      this.asset.quantity = this.tempInfo.quantity
      this.isConfirm= true
      console.log(this.asset)
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
   text-align: center;
 }

 td input:focus {
   outline: none;
 }

</style>