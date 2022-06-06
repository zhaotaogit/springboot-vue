<template>
  <el-card style="width: 500px;padding: 20px;border: 1px solid #ccc">
    <el-form :model="form" label-width="80px" size="small">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "PersonInfo",

  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    };
  },

  mounted() {
  },
  created() {
    this.request.get('/user/username/' + this.user.username).then(res => {
      if (res.code === '200') {
          console.log(res.data);
        this.form = res.data
      }
    })
  },
  methods: {
    save() {
      this.request.post("/user", this.form).then((res) => {
        if (res) {
          this.$message.success("保存成功!");
          this.load();
        } else {
          this.$message.error("保存失败！");
        }
      });
      this.dialogFormVisible = false;
    },
  },
};
</script>

<style lang="scss" scoped></style>
