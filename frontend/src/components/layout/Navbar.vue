<template>
  <div class="sticky-top" id="NavBar">
    <v-app-bar
    color="#00BFFE"
    dense
    dark
    >
      <div class="w-100 d-flex justify-space-between my-auto">
        <!-- Logo -->
        <div id="enLogo">
          <router-link :to="{ name: 'main' }"><img src="@/assets/login/logo.png" alt="mainlogo" height="50"></router-link>
        </div>
        <div id="koLogo">
          <router-link :to="{ name: 'main' }"><img src="@/assets/koLogo.png" alt="mainlogo" height="50"></router-link>
        </div>
        <!-- Search -->
        <div class="mt-1 ms-1">
          <b-form-input
          placeholder="Search"
          v-model="search_item"
          list="my-list-id"
          autocomplete="on"
          @keypress.enter="search"
          >
          </b-form-input>

          <!-- 검색 목록 -->
          <datalist id="my-list-id">
            <!-- 인기 키워드 배열로 담아서 출력할 것. -->
            <option class="text-danger">📢인기 검색어📢</option>
            <option v-for="(hotItem, idx) in hotSearchItems" :key="idx+'a'">{{ hotItem.keyword.substring(0, 9)}}</option>
            <!-- <option v-for="(item, idx) in search_list" :key="idx">{{ item }}</option> -->
          </datalist>
        </div>

        <!-- toggle menu -->
        <v-app-bar-nav-icon @click="drawer = !drawer" id="toggleBtn"/>
        <v-navigation-drawer id="navDrawer" light right absolute app v-model="drawer">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="text-h6">
              Menu
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>
        
        <!-- 토글된 메뉴 리스트 -->
        <!-- 글작성 -->
        <v-list dense nav>
          <v-list-item link @click="createDrawer = !createDrawer">
            <v-list-item-icon>
              <v-icon>mdi-pencil</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Create</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item type='button' class="text-center" style="font-size:smaller" v-if="createDrawer" @click="drawArticleModal">- Article</v-list-item>
          <v-list-item type='button' class="text-center" style="font-size:smaller" v-if="createDrawer" @click="drawPortfolioModal">- Portfolio</v-list-item>
          <!-- <v-list-item-subtitle v-else>Article</v-list-item-subtitle> -->

          <!-- <v-navigation-drawer id="createDrawer" bottom absolute v-model="createDrawer">
            <v-list>
              <v-list-item @click="drawArticleModal">
                Article
              </v-list-item>
              <v-list-item @click="drawPortfolioModal">
                Portfolio
              </v-list-item>
            </v-list>
          </v-navigation-drawer> -->

          <!-- 탐색 -->
          <router-link :to="{ name: 'explore' }" style="text-decoration: none; color: inherit;">
            <v-list-item link>
              <v-list-item-icon>
                <v-icon>mdi-apple-safari</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>Explore</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </router-link>
          
          
          <v-list-item link @click="alarmDrawer = !alarmDrawer">
            <v-list-item-icon>
              <v-icon>mdi-heart</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Alarm</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-navigation-drawer id="alarmDrawer" bottom absolute v-model="alarmDrawer">
            <AlertCenter/>
          </v-navigation-drawer>

          <v-list-item link @click="myPageDrawer = !myPageDrawer">
            <v-list-item-icon>
              <v-icon>mdi-account-circle</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>My Page</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item type='button' class="text-center" style="font-size:smaller" v-if="myPageDrawer" @click="moveToProfile">- Profile</v-list-item>
          <v-list-item type='button' class="text-center" style="font-size:smaller" v-if="myPageDrawer" @click="logout">- Logout</v-list-item>
          <!-- <v-navigation-drawer id="myPageDrawer" bottom absolute v-model="myPageDrawer">
            <v-list>
              <v-list-item @click="moveToProfile">
                Profile
              </v-list-item>
              <v-list-item @click="logout">
                Logout
              </v-list-item>
            </v-list>
          </v-navigation-drawer> -->
          


        </v-list>

        </v-navigation-drawer>

        <!-- 아이콘 -->
        <div id="btnGroup">
          <!-- 글쓰기 -->
          <v-menu bottom left offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn dark icon v-bind="attrs" v-on="on">
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
            </template>
            <v-list style="width:7rem">
              <v-list-item class="d-flex justify-center" @click="drawArticleModal">
                Article
              </v-list-item>
              <v-list-item class="d-flex justify-center" @click="drawPortfolioModal">
                Portfolio
              </v-list-item>
            </v-list>
          </v-menu>
          <!-- 탐색 -->
          <v-btn icon>
            <router-link :to="{ name: 'explore' }" style="text-decoration: none; color: inherit;">
              <v-icon>mdi-apple-safari</v-icon>
            </router-link>
          </v-btn>
          <!-- 알림 -->
          <v-menu bottom offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn dark icon v-bind="attrs" v-on="on">
                <v-icon v-if="unreadAlert" color="#CE1D28">mdi-heart</v-icon>
                <v-icon v-else>mdi-heart</v-icon>
              </v-btn>
            </template>
            <!-- 알림 센터 popover -->
            <AlertCenter/>
          </v-menu>

          <!-- 프로필 -->
          <v-menu bottom offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn dark icon v-bind="attrs" v-on="on">
                <v-icon>mdi-account-circle</v-icon>
              </v-btn>
            </template>
            <v-list style="width:8rem">
              <v-list-item class="d-flex justify-center" @click="moveToProfile">
                  my profile
              </v-list-item>
              <!-- 로그아웃 -->
              <v-list-item class="d-flex justify-center" @click="logout">
                Log out
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </div>
    </v-app-bar>
  </div>
</template>

<script>
import axios from 'axios'
import AlertCenter from '../alertcenter/AlertCenter.vue'

export default {
  name: 'Navbar',
  data: function () {
    return {
      // 검색어
      hotSearchItems: [],
      search_item: "",
      drawer: false,
      createDraw: false,
      createDrawer: false,
      alarmDrawer: false,
      myPageDrawer: false,
      items: [
        { title: 'Create', icon: 'mdi-pencil' },
        { title: 'Explore', icon: 'mdi-apple-safari' },
        { title: 'Alarm', icon: 'mdi-heart' },
        { title: 'MyPage', icon: 'mdi-account-circle' },
      ],
    }
  },
  computed: {
    //store에서 검색 목록 가져오기
    search_list () {
      return this.$store.state.search_history
    },
    // 스토어에서 현재 로그인한 사용자 닉네임 가져오기
    user_nickname () {
      return this.$store.state.user_info.nickname
    },
    unreadAlert () {
      if (this.$store.state.unreadAlert > 0) {
        return true
      } else {
        return false
      }
      
    },
  },
  created: function () {
    axios({
      method: 'GET',
      url: '/api/feed/hotkeyword'
    })
    .then(res => {
      // console.log(res)
      res.data.hotkeyList.splice(5)
      this.hotSearchItems = res.data.hotkeyList
      
    })
    .catch(err => {
      console.log(err)
    })
  },
  methods: {
    search: function () {
      // 검색 결과 페이지 이동 
      // console.log(this.search_item)
      this.$router.push({name: 'search', params: {keyword: `${this.search_item}`}})
      // vuex에 최신 검색 목록 저장
      this.$store.dispatch('search', this.search_item)
      this.search_item = ""
    },
    drawArticleModal: function () {
      // 글 작성 모달
    this.$store.state.drawCreateArticle = true
    },
    drawPortfolioModal: function () {
      // 포트폴리오 모달
      this.$store.state.drawCreatePortfolio = true
    },
    logout: function () {
      if (this.$store.state.user_info.oauth === 'google') {
        // 구글 계정 소셜 로그인 회원
        const authInst = window.gapi.auth2.getAuthInstance();
        authInst.signOut().then(() => {
          localStorage.removeItem('userData')
          this.$store.commit('LOGOUT')
          this.$router.push({ name: 'Login' })
        });
        return;
      }

      localStorage.removeItem('userData')
      this.$store.commit('LOGOUT')
      this.$router.push({ name: 'Login' })
    },
    moveToProfile: function () {
      this.$router.push({ name: 'profile', params:{ nickname: `${this.user_nickname}`}})
    },
    showList: function () {
      this.createDraw = true
    }
  },
  components: {
    AlertCenter,
  }
}
</script>

<style scoped>

#NavBar {
  z-index: 2;
}
@media (min-width: 600px) { 
  #toggleBtn {
    display: none;
  }
  #navDrawer {
    display: none;
  }
  #koLogo {
    display: none;
  }
}
@media (max-width: 600px) { 
  #btnGroup {
    display: none;
  }
  #enLogo {
    display: none;
  }
}

 .toggleMenu {
   display: none;
 }

 .createList {
   display: block;
 }
</style>