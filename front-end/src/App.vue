<template>
  <div id="app">
    <img src="./assets/logo.png">
    <NewComment title="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
import NewComment from './components/NewComment.vue'

export default {
  name: 'app',
  components: {
    NewComment
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
