<template>
  <div class="wrapper">
    <div
      style="
        margin: 200px auto;
        background-color: #fff;
        width: 350px;
        height: 400px;
        padding: 20px;
        border-radius: 20px;
      "
    >
      <div style="margin: 20px 0; text-align: center; font-size: 24px">
        <b>注册</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input
            placeholder="请输入用户名"
            size="medium"
            style="margin: 10px 0"
            prefix-icon="el-icon-user"
            v-model="user.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            size="medium"
            style="margin: 10px 0"
            prefix-icon="el-icon-lock"
            show-password
            v-model="user.password"
          ></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input
            placeholder="请确认密码"
            size="medium"
            style="margin: 10px 0"
            prefix-icon="el-icon-lock"
            show-password
            v-model="user.confirmPassword"
          ></el-input>
        </el-form-item>
        <div style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" @click="register('userForm')"
            >注册</el-button
          >
          <el-button type="warning" size="small" @click="$router.push('/login')"
            >返回登录</el-button
          >
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    register(formName) {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          if (this.user.password !== this.user.confirmPassword) {
            this.$message.error("两次输入的密码不一致!");
            return false;
          }
          this.request.post("/user/register", this.user).then((res) => {
            if (res.code === "200") {
              this.$message.success("注册成功!");
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
.wrapper {
  height: 100vh;
  background: #12c2e9; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #f64f59,
    #c471ed,
    #12c2e9
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(
    to right,
    #f64f59,
    #c471ed,
    #12c2e9
  ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
  overflow: hidden;
}
</style>
