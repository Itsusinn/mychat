<template><div>

  <el-row>
    <el-col :span="4"><h5>{{comment.nick}}</h5></el-col>
    <el-col :span="6"><h5>{{comment.email}}</h5></el-col>
  </el-row>

  <el-row>
    <el-col :span="12"><p>{{comment.content}}</p></el-col>
  </el-row>

</div></template>

<script>
export default {
  name: "Comment",
  props:{
    comment:Object
  }
}
</script>

<style scoped>

</style>