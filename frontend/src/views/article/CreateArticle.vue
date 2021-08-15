<template>
  <v-dialog
    persistent
    width="555"
    v-model="isDrawCreateArticle" 
  >
    <v-card id="createArticle">
      <h1 class="text-center mb-5"><v-icon large color="#00BFFE" class="me-2">mdi-pencil</v-icon> 게시물 작성</h1>
      <v-form
        ref="form"
        v-model="valid"
      >
        <!-- title -->
        <div class="d-flex flex-column flex-sm-row justify-content-sm-between">
          <div class="w-100">
            <label for="formTitle">제목</label>
            <v-text-field
              solo
              dense
              counter=20
              :rules="titleRules"
              v-model="data.title" 
              id="formTitle"
            ></v-text-field>
          </div>
          <div>
            <label for="formStock">종목</label>
            <v-select
              :items="items"
              dense
              solo
              v-model="data.stock"
              id="formStock"
            ></v-select>
          </div>
        </div>
        <!-- content -->
        <div>
          <label for="content">내용</label>
          <Tiptap v-model="data.content"/>
        </div>
        <br>
        <!-- tags -->
        <label for="tags">해쉬 태그</label>
        <div class="d-flex justify-content-between align-items-end">
          <span>#</span>
          <v-text-field
            dense
            solo
            hide-details
            class="w-100 mx-2"
            v-model="inputTag"
            @keypress.enter="addTag"
          ></v-text-field>
          <v-btn color="error" @click="addTag">추가</v-btn>          
        </div>
        <div class="mt-2 mb-3">
          <v-chip v-for="(tag, idx) in data.tags" :key="idx" label close @click:close="popTag(idx)" class="me-3 mt-3">#{{ tag }}</v-chip>
        </div>
        <!-- Btn Group -->
        <div class="d-flex justify-content-end mt-5">
          <v-btn @click="closeModal" class="me-5" color="error" >작성 취소</v-btn>
          <v-btn @click="submitForm" :disabled="!valid" color="primary" >작성 완료</v-btn>
        </div>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'
import Tiptap from '@/components/Tiptap.vue'

export default {
  name: 'CreateArticle',
  components: {
    Tiptap,
  },
  data: function () {
    return {
      valid: true,
      items: ['국내주식', '해외주식', '국내채권', '해외채권'],
      inputTag: '',
      data: {
        userIdx: this.$store.state.user_info.id,
        title: null,
        content: null,
        stock: '국내주식',
        tags: [],
      }
    }
  },
  methods: {
    addTag: function () {
      if (this.inputTag.trim()) {
        this.data.tags.push(this.inputTag)
      }
      this.inputTag = ''
    },
    popTag: function (idx) {
      this.data.tags.splice(idx, 1)
    },
    closeModal: function () {
      this.$store.state.drawCreateArticle = false
    },
    submitForm: function () {
      axios({
        method: 'POST',
        url: '/api/article/',
        data: this.data
      })
      .then(() => {
        this.closeModal()
        this.$alert("성공적으로 게시물이 작성되었습니다.", "작성 완료", 'success')
        .then(() => {
          if (this.$route.name === 'main') {
            this.$router.go(this.$router.currentRoute)
          }
        })
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
  computed: {
    isDrawCreateArticle: function () {
      return this.$store.state.drawCreateArticle
    },
    titleRules: function () {
      return [
        v => !!v || '제목을 적어주세요.',
        v => (v && v.length <= 20) || '제목이 너무 길어요...',
      ]
    },
    contentRules: function () {
      return [
        v => !!v || '내용을 적어주세요.',
      ]
    }
  },
}
</script>

<style>
#createArticle {
  padding: 5% 7%;
  font-family: 'Noto Sans KR', sans-serif;
}

#createArticle h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 800;
}

#createArticle form {
  width: 100%;
  margin-top: 5%;
}

#createArticle form > div > div:first-child {
  margin-right: 5%;
}
  
</style>