<template>
  <v-sheet
   elevation="2"
   rounded
   id="Tiptap"
  >
    <div v-if="editor" class="w-100 d-flex justify-content-center align-items-center py-2 bg-white border">
      <button type="button" @click="editor.chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }">
        <i class="ri-bold"></i>
      </button>
      <button type="button" @click="editor.chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }">
        <i class="ri-italic"></i>
      </button>
      <button type="button" @click="editor.chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }">
        <i class="ri-strikethrough-2"></i>
      </button>
      <button type="button" @click="editor.chain().focus().toggleCode().run()" :class="{ 'is-active': editor.isActive('code') }">
        <i class="ri-code-line"></i>
      </button>
      <button type="button" @click="editor.chain().focus().unsetAllMarks().run()">
        <i class="ri-format-clear"></i>
      </button>
      <div class="divider"></div>
      <button type="button" @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
        <i class="ri-h-1"></i>
      </button>
      <button type="button" @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
        <i class="ri-h-2"></i>
      </button>
      <button type="button" @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
        <i class="ri-h-3"></i>
      </button>
      <div class="divider"></div>
      <button type="button" @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }">
        <i class="ri-list-unordered"></i>
      </button>
      <button type="button" @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'is-active': editor.isActive('orderedList') }">
        <i class="ri-list-ordered"></i>
      </button>
      <div class="divider"></div>
      <button type="button" @click="editor.chain().focus().undo().run()">
        <i class="ri-arrow-go-back-line"></i>
      </button>
      <button type="button" @click="editor.chain().focus().redo().run()">
        <i class="ri-arrow-go-forward-line"></i>
      </button>
    </div>
    <editor-content :editor="editor" class="ms-4"/>
  </v-sheet>
</template>

<script>
import { Editor, EditorContent } from '@tiptap/vue-2'
import StarterKit from '@tiptap/starter-kit'

export default {
  components: {
    EditorContent,
  },

  props: {
    value: {
      type: String,
      default: '',
    },
  },

  data() {
    return {
      editor: null,
    }
  },

  watch: {
    value(value) {
      const isSame = this.editor.getHTML() === value

      if (isSame) {
        return
      }

      this.editor.commands.setContent(this.value, false)
    },
  },

  mounted() {
    this.editor = new Editor({
      extensions: [
        StarterKit,
      ],
      content: this.value,
      
      onUpdate: () => {
        this.$emit('input', this.editor.getHTML())
      },
    })
  },

  beforeDestroy() {
    this.editor.destroy()
  },
}
</script>

<style lang="scss">
@import "./tiptap.scss";

</style>