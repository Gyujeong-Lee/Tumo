<template>
  <tr>
    <td>{{ name }}</td>
    <td>
      <input type="text" v-model.number="price">
    </td>
    <td>
      <input type="text" v-model.number="goal">
    </td>
    <td>
      <input type="text" v-model.number="quantity">
    </td>
    <td>
      <span type="button" v-if="!isConfirm" @click="confirmInfo">확정</span>
      <span v-else>완료</span>
      <v-btn icon>
        <v-icon @click="deleteAsset">mdi-delete</v-icon>
      </v-btn>
    </td>
  </tr>
</template>

<script>
import axios from 'axios'

export default {
  name: 'UpdateAssetList',
  data: function () {
    return {
      isConfirm: false,
      ...this.asset,
      portfolioId: this.portfolioIdx,
    }
  },
  props: {
    asset: Object,
    portfolioIdx: Number,
    myAssets: Array,
  },
  methods: {
    confirmInfo: function () {
    // 여기서 수정 및 추가 보내야 함.
      if (this.asset.datetime) {
        // 수정(기존 자산)
        const assetData = {
          assetIdx: this.asset.asset_idx,
          goal: this.asset.goal,
          price: this.asset.price,
          quantity: this.asset.quantity
        }
        axios({
          method: 'PUT',
          url: `/api/portfolio/asset`,
          data: assetData
        })
        .then(res => {
          console.log(res)
          // 알림 띄우기 수정 완료
          this.$alert("자산을 수정하였습니다.", "수정 완료", 'success')
        })
      } else {
        // 추가 (신규자산)
        this.asset.portfolioIdx = this.portfolioId
        axios({
          method: 'POST',
          url: '/api/portfolio/asset',
          data: this.asset
        })
        .then(res => {
          console.log(res)
          this.$alert("자산을 추가하였습니다.", "추가 완료", 'success')
        })
        .catch(err => {
          console.log(err)
        })
      }
      this.isConfirm= true
      // console.log(this.asset)
    },
    deleteAsset: function () {
      if (this.asset.datetime) {
        // 기존 자산 
        axios({
          method: 'DELETE',
          url: `/api/portfolio/asset/${this.asset.asset_idx}`
        })
        .then(res => {
          console.log(res)
          this.$alert("자산이 삭제되었습니다.", "삭제 완료", 'success')
        })
        .catch(err => {
          console.log(err)
        })
      } else {
        // 신규 자산
        let name = this.asset.name
        let idx = this.myAssets.findIndex(function(ob) {return ob.name === name})
        this.myAssets.splice(idx, 1)
      }
    }
  },
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