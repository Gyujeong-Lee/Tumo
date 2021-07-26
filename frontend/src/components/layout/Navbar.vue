<template>
  <div>
    <v-app-bar
    color="#00BFFE"
    dense
    dark
    >
      <div class="w-100 d-flex justify-space-between">
        <!-- Logo -->
        <router-link :to="{ name: 'Login' }"><img src="@/assets/login/logo.png" alt="mainlogo" height="50"></router-link>
        <!-- Search -->
        <b-form-input class="mt-3" placeholder="Search">
        </b-form-input> 
        <!-- 아이콘 -->
        <div class="mt-2">
          <!-- 링크 수정할 것 -->
          <v-btn icon>
            <router-link :to="{ name: 'Login' }" style="text-decoration: none; color: inherit;"><v-icon>mdi-pencil</v-icon></router-link>
          </v-btn>
          <v-btn icon>
            <router-link :to="{ name: 'Login' }" style="text-decoration: none; color: inherit;"><v-icon>mdi-apple-safari</v-icon></router-link>
          </v-btn>
          <v-btn icon>
            <router-link :to="{ name: 'Login' }" style="text-decoration: none; color: inherit;"><v-icon>mdi-heart</v-icon></router-link>
          </v-btn>
          <v-btn icon>
            <router-link :to="{ name: 'Login' }" style="text-decoration: none; color: inherit;"><v-icon>mdi-account-circle</v-icon></router-link>
          </v-btn>
        </div>

      </div>
    </v-app-bar>
  </div>
</template>

<script>
export default {
  name: 'Navbar',
  data: function () {
    return {

    }
  },
  methods: {

  }
}
</script>

<style scoped>
  #nav-bar {
    /* nav bar 색상 추가 */
    background-color: #00BFFE;
  }
  input {
    width: 20%;
    height: 80%;
  }
</style>