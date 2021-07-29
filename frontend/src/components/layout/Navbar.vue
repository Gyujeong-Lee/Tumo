<template>
  <div class="sticky-top" id="NavBar">
    <v-app-bar
    color="#00BFFE"
    dense
    dark
    >
      <div class="w-100 d-flex justify-space-between my-auto">
        <!-- Logo -->
        <router-link :to="{ name: 'main' }"><img src="@/assets/login/logo.png" alt="mainlogo" height="50"></router-link>

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
          <datalist 
          id="my-list-id"
          >
            <option>인기 검색어?</option>
            <option v-for="(item, idx) in search_list" :key="idx">{{ item }}</option>
          </datalist>
        </div>

        <!-- 아이콘 -->
        <div>
          <!-- 링크 수정할 것 -->
          <!-- 글쓰기 -->
          <v-btn icon>
            <router-link :to="{ name: 'Login' }" style="text-decoration: none; color: inherit;">
              <v-icon>mdi-pencil</v-icon>
            </router-link>
          </v-btn>
          <!-- 탐색 -->
          <v-btn icon>
            <router-link :to="{ name: 'Login' }" style="text-decoration: none; color: inherit;">
              <v-icon>mdi-apple-safari</v-icon>
            </router-link>
          </v-btn>
          <!-- 알림 -->
          <v-menu
          bottom
          offset-y
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                dark
                icon
                v-bind="attrs"
                v-on="on"
              >
                <v-icon>mdi-heart</v-icon>
              </v-btn>
            </template>
            <!-- 알림 센터 popover -->
            <AlertCenter/>
          </v-menu>
          <!-- 프로필 -->
          <v-btn icon>
            <router-link 
            :to="{ name: 'profile', params:{ nickname: `${user_nickname}`} }" 
            style="text-decoration: none; color: inherit;"
            >
              <v-icon>mdi-account-circle</v-icon>
            </router-link>
          </v-btn>
        </div>
      </div>
    </v-app-bar>
  </div>
</template>

<script>
import AlertCenter from '../alertcenter/AlertCenter.vue'

export default {
  name: 'Navbar',
  data: function () {
    return {
      // 검색어
      search_item: "",
    }
  },
  computed: {
    //store에서 검색 목록 가져오기
    search_list () {
      return this.$store.state.search_history
    },
    user_nickname () {
      return this.$store.state.user_info.nickname
    },
  },
  methods: {
    search: function () {
      // vuex를 통해 검색 요청 보내기
      this.$store.dispatch('search', this.search_item)
      this.search_item = ""
    },
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

</style>