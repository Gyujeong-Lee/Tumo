<template>
  <v-dialog
    persistent
    width="555"
    v-model="isDrawUpdateArticle" 
  >
    <v-card id="updateArticle">
      <h1 class="text-center mb-5"><v-icon large color="#00BFFE" class="me-2">mdi-pencil</v-icon> 게시물 수정</h1>
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
  name: 'UpdateArticle',
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
      this.$store.state.drawUpdateArticle = false
    },
    submitForm: function () {
      const data = {
        userIdx: this.data.userIdx,
        title: this.data.title,
        content: this.data.content,
        stock: this.data.stock,
        tags: this.data.tags,
      }
      axios({
        method: 'PUT',
        url: `/api/article/${this.data.boardIdx}`,
        data: data
      })
      .then(() => {
        this.getArticleDetail()
        .then(() => {
          this.$emit('update')
          this.closeModal()
          this.$alert("성공적으로 게시물이 수정되었습니다.", "수정 성공", "success")
        })
      })
    },
    getArticleDetail: function () {
      return axios({
        method: 'GET',
        url: `/api/article/${this.data.boardIdx}/${this.data.userIdx}`
      })
      .then(res => {
        this.$store.state.selectedArticle = res.data.feed
      })
    },
  },
  computed: {
    isDrawUpdateArticle: function () {
      return this.$store.state.drawUpdateArticle
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
  created: function () {
    this.data = {
      ...this.data,
      ...this.$store.state.selectedArticle
    }
  }
}
</script>

<style>
#updateArticle {
  padding: 5% 7%;
}

#updateArticle h1 {
  font-family: 'Gothic A1', sans-serif;
  font-weight: 800;
}

#updateArticle label, 
#updateArticle span {
  font-family: 'Nanum Gothic', sans-serif;
  font-weight: 700;
}

#updateArticle form {
  width: 100%;
  margin-top: 5%;
}

#updateArticle form > div > div:first-child {
  margin-right: 5%;
}
  
</style>