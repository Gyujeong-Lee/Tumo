<template>
  <div v-if="!isLoading" class="d-flex justify-content-center">
    <v-sheet elevation="8" rounded id="articleDetail">
      <div class="d-flex justify-content-between mb-3">
        <div class="d-flex align-items-center">
          <div class="ms-3">
            <h5 class="mb-0 fw-bold">{{ $data.corp_name }}</h5>
            <div style="font-size: 0.9rem;">
              <span class="my-0 text-primary" @click="moveToHomepage">
                {{ $data.corp_name_eng }}
              </span>
              <span class="my-0 text-secondary"> &nbsp;&nbsp;{{ $data.corp_code }} </span>
            </div>
          </div>
        </div>
        <div style="font-size: 0.75rem; min-width: 113px;">
          <p class="my-0">주식코드 &nbsp;{{ $data.stock_code }}</p>
          <p class="my-0">업종코드 &nbsp;{{ $data.induty_code }}</p>
        </div>
      </div>
      <div v-html="$data.content" class="my-3"></div>
      <div class="corp_detail_box">
        <table class="tbl_com1">
          <colgroup>
            <col style="width: 33%;" />
            <col style="width: 67%;" />
            <col />
          </colgroup>
          <tbody>
            <tr>
              <th scope="row">대표자</th>
              <td>{{ $data.ceo_nm }}</td>
            </tr>
            <tr>
              <th scope="row">설립일</th>
              <td>
                {{ $data.est_dt.substring(0, 4) }}년 {{ $data.est_dt.substring(4, 6) }}월
                {{ $data.est_dt.substring(6, 8) }}일
              </td>
            </tr>
            <tr>
              <th scope="row">주소</th>
              <td>{{ $data.adres }}</td>
            </tr>
            <tr>
              <th scope="row">기업형태</th>
              <td>
                <span v-if="$data.corp_cls == 'Y'">유가</span>
                <span v-if="$data.corp_cls == 'K'">코스닥</span>
                <span v-if="$data.corp_cls == 'N'">코넥스</span>
                <span v-if="$data.corp_cls == 'E'">기타</span>
              </td>
            </tr>
            <tr>
              <th scope="row">전화번호</th>
              <td>{{ $data.phn_no }}</td>
            </tr>
            <tr>
              <th scope="row">팩스번호</th>
              <td>{{ $data.fax_no }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </v-sheet>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CorporationDetail",
  components: {},
  data: function() {
    return {
      isLoading: true,
    };
  },
  methods: {
    moveToHomepage: function() {
      window.open(`https://${this.$data.hm_url}`);
    },
  },
  created: function() {
    axios({
      method: "GET",
      url: `/api/company/detail/${this.$route.params.companyName}`,
    }).then((res) => {
      console.log(this.$route.params.companyName);
      Object.assign(this.$data, res.data);
    });

    setTimeout(() => {
      this.isLoading = false;
    }, 1000);
  },
};
</script>

<style>
#articleDetail {
  width: 526px;
  padding: 2rem 2rem 1rem;
  margin: 3rem 0rem auto;
}

.el-popconfirm__action {
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
}

@media screen and (min-width: 940px) {
  #articleDetail {
    margin-right: 3rem;
  }
}
</style>
