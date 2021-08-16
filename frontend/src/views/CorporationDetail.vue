<template>
  <div v-if="!isLoading" id="highest_block" class="container d-flex flex-column">
    <div class="d-flex justify-center mb-5">
      <div id="corp_info_block">
        <h2>
          {{ $data.corp_name }}
          <span id="hash_tags" class="mt-5">
            <v-chip v-if="$data.corp_cls == 'Y'" class="sm" color="#00BFFE" text-color="white">
              유가
            </v-chip>
            <v-chip v-if="$data.corp_cls == 'K'" class="me-2" color="#00BFFE" text-color="white">
              코스닥
            </v-chip>
            <v-chip v-if="$data.corp_cls == 'N'" class="me-2" color="#00BFFE" text-color="white">
              코넥스
            </v-chip>
            <v-chip v-if="$data.corp_cls == 'E'" class="me-2" color="#00BFFE" text-color="white">
              기타
            </v-chip>
          </span>
          <span class="my-0 text-secondary" style="font-size: 0.9rem;">
            &nbsp;&nbsp;&nbsp;&nbsp;{{ $data.corp_code }}
          </span>
          <span class="my-0 text-primary" @click="moveToHomepage" style="font-size: 0.9rem;">
            &nbsp;&nbsp;&nbsp;&nbsp;<v-icon small color="primary">mdi-home</v-icon>
            {{ $data.corp_name_eng }}
          </span>
        </h2>
        <p style="font-size: 0.9rem;">
          <span> <b>주식코드</b> {{ $data.stock_code }}</span>
          <span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>업종코드</b> {{ $data.induty_code }}</span>
        </p>
        <p>
          <span><v-icon small color="primary">mdi-phone</v-icon> {{ $data.phn_no }} </span>
          <span
            >&nbsp;&nbsp;<v-icon small color="primary">mdi-fax</v-icon> {{ $data.fax_no }}
          </span>
        </p>
        <p><v-icon small color="primary">mdi-map-marker</v-icon> {{ $data.adres }}</p>
        <p>
          <v-icon small color="primary">mdi-account-supervisor-circle</v-icon> {{ $data.ceo_nm }}
        </p>
        <p>
          <v-icon small color="primary">mdi-calendar-start</v-icon>
          {{ $data.est_dt ? $data.est_dt.substring(0, 4) : "" }}년
          {{ $data.est_dt ? $data.est_dt.substring(4, 6) : "" }}월
          {{ $data.est_dt ? $data.est_dt.substring(6, 8) : "" }}일
        </p>
      </div>
    </div>
    <div class="d-grid justify-center my-auto">
      <div class="row">
        <div class="col-12 col-sm-5 offset-sm-1">
          <h3>News</h3>
          <div v-if="isNews" class="w-100">
            <v-sheet
              elevation="8"
              rounded
              class="articleFeed mx-2 my-5"
              @mouseover="elevation = 10"
              @mouseleave="elevation = 4"
              height="auto"
              width="auto"
              v-for="(news, idx) in corpNews"
              :key="idx"
            >
              <h5 type="button" @click="moveToNews(news.href)">
                {{ news.title }}
              </h5>
              <div class="d-flex align-left mt-3">
                <div class="d-flex flex-row" id="NewsInfo">
                  <span>{{ news.date }}</span> &nbsp;&nbsp;&nbsp;
                  <span class="text-primary">{{ news.author }}</span>
                </div>
              </div>
            </v-sheet>
          </div>
          <div v-else>뉴스가 없습니다.</div>
        </div>
        <div class="col-12 col-sm-5 offset-sm-1">
          <h3>Report</h3>
          <v-simple-table class="w-100 border">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">
                    분류
                  </th>
                  <th class="text-center">
                    2019
                  </th>
                  <th class="text-centers">
                    2020
                  </th>
                  <th class="text-center">
                    2021
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(report, idx) in corpReport" :key="idx">
                  <td>{{ report.value }}</td>
                  <td>
                    {{ report.twoyear }}
                  </td>
                  <td>
                    {{ report.lastyear }}
                  </td>
                  <td>
                    {{ report.thisyear }}
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </div>

      </div>
    </div>
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
      corpNews: [],
      corpReport: [],
      isNews: false,
      isReport: false,
    };
  },
  methods: {
    moveToHomepage: function() {
      window.open(`https://${this.$data.hm_url}`);
    },
    moveToNews: function(href) {
      window.open(`http://finance.naver.com${href}`);
    },
  },
  created: function() {
    axios({
      method: "GET",
      url: `/api/company/detail/${this.$route.params.companyName}`,
    })
      .then((res) => {
        Object.assign(this.$data, res.data);
      })
      .then(() => {
        axios({
          method: "GET",
          url: `/api/company/news/${this.$route.params.companyName}`,
        }).then((res) => {
          this.corpNews = res.data.news;
          this.isNews = true
        })
        axios({
          method: "GET",
          url: `/api/company/report/${this.$route.params.companyName}`,
        }).then((res) => {
          console.log(res)
          let tmpList = res.data.list;
          for (let idx in tmpList) {
            let flag = false;
            if (tmpList[idx].thisyear == Infinity || tmpList[idx].thisyear == -Infinity) {
              tmpList[idx].thisyear = "자료없음";
              flag = true;
            }

            if (tmpList[idx].lastyear == Infinity || tmpList[idx].lastyear == -Infinity) {
              tmpList[idx].lastyear = "자료없음";
              flag = true;
            }

            if (tmpList[idx].twoyear == Infinity || tmpList[idx].twoyear == -Infinity) {
              tmpList[idx].twoyear = "자료없음";
              flag = true;
            }

            if (flag) continue;

            if (idx == 0 || idx == 1 || (tmpList[0].value == "매출액" && idx == 2)) {
              let sign1 = tmpList[idx].thisyear.substring(0, 1) == "-" ? true : false;
              let sign2 = tmpList[idx].lastyear.substring(0, 1) == "-" ? true : false;
              let sign3 = tmpList[idx].twoyear.substring(0, 1) == "-" ? true : false;

              let len1 = tmpList[idx].thisyear.length;
              let len2 = tmpList[idx].lastyear.length;
              let len3 = tmpList[idx].twoyear.length;
              if (sign1) {
                if (len1 > 16) {
                  tmpList[idx].thisyear =
                    tmpList[idx].thisyear.substring(0, len1 - 15).replace(/,/gi, "") + "조";
                } else if (len1 > 11) {
                  tmpList[idx].thisyear =
                    tmpList[idx].thisyear.substring(0, len1 - 10).replace(/,/gi, "") + "억";
                } else if (len1 > 6) {
                  tmpList[idx].thisyear =
                    tmpList[idx].thisyear.substring(0, len1 - 5).replace(/,/gi, "") + "만";
                }
              } else {
                if (len1 >= 16) {
                  tmpList[idx].thisyear =
                    tmpList[idx].thisyear.substring(0, len1 - 15).replace(/,/gi, "") + "조";
                } else if (len1 >= 11) {
                  tmpList[idx].thisyear =
                    tmpList[idx].thisyear.substring(0, len1 - 10).replace(/,/gi, "") + "억";
                } else if (len1 >= 6) {
                  tmpList[idx].thisyear =
                    tmpList[idx].thisyear.substring(0, len1 - 5).replace(/,/gi, "") + "만";
                }
              }

              if (sign2) {
                if (len2 > 16) {
                  tmpList[idx].lastyear =
                    tmpList[idx].lastyear.substring(0, len2 - 15).replace(/,/gi, "") + "조";
                } else if (len2 > 11) {
                  tmpList[idx].lastyear =
                    tmpList[idx].lastyear.substring(0, len2 - 10).replace(/,/gi, "") + "억";
                } else if (len2 > 6) {
                  tmpList[idx].lastyear =
                    tmpList[idx].lastyear.substring(0, len2 - 5).replace(/,/gi, "") + "만";
                }
              } else {
                if (len2 >= 16) {
                  tmpList[idx].lastyear =
                    tmpList[idx].lastyear.substring(0, len2 - 15).replace(/,/gi, "") + "조";
                } else if (len2 >= 11) {
                  tmpList[idx].lastyear =
                    tmpList[idx].lastyear.substring(0, len2 - 10).replace(/,/gi, "") + "억";
                } else if (len2 >= 6) {
                  tmpList[idx].lastyear =
                    tmpList[idx].lastyear.substring(0, len2 - 5).replace(/,/gi, "") + "만";
                }
              }
              if (sign3) {
                if (len3 > 16) {
                  tmpList[idx].twoyear =
                    tmpList[idx].twoyear.substring(0, len3 - 15).replace(/,/gi, "") + "조";
                } else if (len3 > 11) {
                  tmpList[idx].twoyear =
                    tmpList[idx].twoyear.substring(0, len3 - 10).replace(/,/gi, "") + "억";
                } else if (len3 > 6) {
                  tmpList[idx].twoyear =
                    tmpList[idx].twoyear.substring(0, len3 - 5).replace(/,/gi, "") + "만";
                }
              } else {
                if (len3 >= 16) {
                  tmpList[idx].twoyear =
                    tmpList[idx].twoyear.substring(0, len3 - 15).replace(/,/gi, "") + "조";
                } else if (len3 >= 11) {
                  tmpList[idx].twoyear =
                    tmpList[idx].twoyear.substring(0, len3 - 10).replace(/,/gi, "") + "억";
                } else if (len3 >= 6) {
                  tmpList[idx].twoyear =
                    tmpList[idx].twoyear.substring(0, len3 - 5).replace(/,/gi, "") + "만";
                }
              }
            }

            if (idx == 0 || idx == 1 || (tmpList[0].value == "매출액" && idx == 2)) {
              tmpList[idx].twoyear += "원";
              tmpList[idx].lastyear += "원";
              tmpList[idx].thisyear += "원";
            } else {
              tmpList[idx].twoyear += "%";
              tmpList[idx].lastyear += "%";
              tmpList[idx].thisyear += "%";
            }
          }
          this.corpReport = tmpList;
          this.isReport = true
          this.isLoading = false;
        })
        .catch((error) => {
          // Error 😨
          if (error.response) {
            if (error.response.status === 500) {
              this.$alert("존재하지 않는 기업입니다.", "실패", 'error')
              this.$router.go(-1)
            }
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
          console.log(error.config);
        });
      })
    .catch((error) => {
      // Error 😨
      if (error.response) {
        if (error.response.status === 500) {
          this.$alert("존재하지 않는 기업입니다.", "실패", 'error')
          this.$router.go(-1)
        }
      } else if (error.request) {
        console.log(error.request);
      } else {
        console.log('Error', error.message);
      }
      console.log(error.config);
    });
  },
};
</script>

<style scoped>

.el-popconfirm__action {
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
}

#highest_block {
  padding-top: 5rem;
}
#user_image {
  width: 5rem;
  height: 5rem;
  margin-bottom: 1rem;
}
h2,
h3 {
  text-align: center;
  font-family: "Otomanopee One", sans-serif;
}

p {
  text-align: left;
}

#activityTabs span {
  font-family: "Otomanopee One", sans-serif;
}

#activityTabs {
  padding-top: 6rem;
  position: sticky;
  top: 0;
  background-color: white;
  z-index: 1;
}

.v-chip.v-size--default {
  border-radius: 16px;
  font-size: 10px;
  height: 22px;
}
</style>
