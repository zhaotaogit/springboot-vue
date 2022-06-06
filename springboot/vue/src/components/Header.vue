<template>
  <div>
    <div style="line-height: 60px;display: flex">
      <div style="flex: 1;">
        <span style="cursor: pointer;font-size: 18px;" :class="collapseBtnClass" @click="collapse"></span>
        <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
          <el-breadcrumb-item :to="{path:item.path?item.path:'/'}" v-for="(item,index) in $route.matched"
                              :key="item.path">
            {{ item.meta.name }}
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <el-dropdown style="width: 100px;cursor: pointer">
        <div style="display: inline-block">
          <img style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px" :src="user.avatarUrl"
               alt="">
          <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px;cursor: pointer"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <router-link to="/person">个人信息</router-link>
            </el-dropdown-item>
          <el-dropdown-item>
            <span style="text-decoration: none" @click="logout">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: [
    'collapseBtnClass', 'collapse'
  ],
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }

  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功！")

    }
  }
}
</script>

<style scoped>

</style>