<template><div>

  <el-row>
    <el-col :span="16"><h2>评论</h2></el-col>
  </el-row>

  <el-row>
    <el-col :span="16"><el-input
        type="textarea"
        :rows="5"
        placeholder="不说点什么吗"
        v-model="content">
    </el-input></el-col>
  </el-row>

  <el-row>
    <el-col :span="4"><h5>昵称</h5></el-col>
    <el-col :span="6"><h5>邮箱</h5></el-col>
  </el-row>

  <el-row>
    <el-col :span="4"><el-input v-model="nick"></el-input></el-col>
    <el-col :span="8"><el-input v-model="email"></el-input></el-col>
    <el-col :span="4"><el-button type="primary" v-on:click="submit">提交</el-button></el-col>
  </el-row>

</div></template>

<script>
export default {
  name: 'NewComment',
  props:{
    title:String
  },
  data(){
    return{
      content:"",
      nick:"",
      email:"",
      result:""
    }
  },
  methods:{
    submit:function () {
      this.axios
          .post("http://localhost:8081/api/v1/comment/add",
              {
                nick:this.nick,
                email:this.email,
                content:this.content
              })
          .then(function (respond) {
            console.log(respond)
          })
          .catch(function (error){
            console.log(error)
          })
    }
  }
}
</script>

<style scoped>

</style>