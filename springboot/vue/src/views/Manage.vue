<template>
  <div>
    <el-container style="height: 100vh;">
      <el-aside :width="isCollapse?'64px':'200px'"
                style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0 21 41/35%)">
        <Aside :isCollapse="isCollapse"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
        </el-header>

        <el-main>
          <!--表示当前页面的子路由会在router-view中展示-->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from '../components/Aside'
import Header from '../components/Header'
import User from './User'

export default {
  name: "Manage",
  components: {
    Aside, Header, User
  },
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
    };
  },
  methods: {
    // 点击收缩侧边栏
    collapse() {
      this.collapseBtnClass = this.collapseBtnClass === 'el-icon-s-fold' ? 'el-icon-s-unfold' : 'el-icon-s-fold'
      this.isCollapse = !this.isCollapse
    },
  }
};
</script>


<style lang="scss">

</style>
