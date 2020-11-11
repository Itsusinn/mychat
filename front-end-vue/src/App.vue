<template>
  <div id="app">
    <img src="./assets/logo.png">
    <NewComment subject = 1 ></NewComment>
    <Comment v-bind:comment="ocomment"></Comment>
  </div>
</template>

<script>
import NewComment from './components/NewComment.vue'
import Comment from "@/components/Comment";

export default {
  name: 'app',
  components: {
    NewComment,
    Comment
  },
  data:function () {
    return{
      ocomment:""
    }
  },
  created() {
    this.ocomment = {
      nick:"abc",
      email:"abc@abc.com",
      content:"hello world"
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
