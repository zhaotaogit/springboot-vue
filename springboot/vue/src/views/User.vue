<template>
  <div>

    <div style="margin: 10px 0">
      <el-input placeholder="请输入名称" style="width: 200px" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input placeholder="请输入邮箱" style="width: 200px" suffix-icon="el-icon-message" v-model="email"></el-input>
      <el-input placeholder="请输入地址" style="width: 200px" suffix-icon="el-icon-position"
                v-model="address"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0 ">
      <el-button type="primary" @click="handleAddUser">新增<i class="el-icon-circle-plus-outline"/></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我在想想'
          icon="el-icon-info"
          icon-color="red"
          title="确定要批量删除这些数据吗？"
          @confirm="handleDelBatch"
      >
        <el-button slot="reference" type="danger">批量删除<i class="el-icon-remove-outline"/></el-button>
      </el-popconfirm>
      <el-upload
          class="upload-demo" style="display: inline-block"
          action="http://localhost:9090/user/import"
          :show-file-list="false"
          :on-success="handleImportSuccess"
          accept="xlsx"
          >
      <el-button class="ml-5" type="primary">导入<i class="el-icon-bottom"/></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"/></el-button>
    </div>
    <el-table :header-cell-class-name="headerBg" :data="tableData" border stripe
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="140"></el-table-column>
      <el-table-column prop="username" label="用户名" width="140"></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"/></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我在想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定要删除吗？"
              @confirm="handleDel(scope.row.id)"
          >
            <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"/>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 15,20, 25]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageTotal">
      </el-pagination>
    </div>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      headerBg: 'headerBg',
      pageNum: 1,
      pageSize: 10,
      pageTotal: 0,
      username: '',
      email: '',
      address: '',
      dialogFormVisible: false,
      form: {},
      // 选择的数据数据
      multipleSelection: []


    };
  },
  methods: {
    load() {
      this.request.get("/user/page",
          {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              username: this.username,
              email: this.email,
              address: this.address,
            }
          })
          .then(res => {
            this.tableData = res.data.records
            this.pageTotal = res.data.total
          })
    },
    reset() {
      this.username = ""
      this.email = ""
      this.address = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
      // console.log(`每页 ${pageSize} 条`);
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
      // console.log(`当前页: ${pageNum}`);
    },
    //新增用户
    handleAddUser() {
      this.dialogFormVisible = !this.dialogFormVisible
      this.form = {}
    },
    save() {
      this.request.post("/user", this.form)
          .then(res => {
            if (res.code === '200') {
              this.$message.success("新增成功！")
              this.load()
            } else {
              this.$message.error("新增失败！")
            }
          })
      this.dialogFormVisible = false
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    handleDel(id) {
      this.request.delete(`/user/${id}`).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败！")
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleDelBatch() {
      let ids = this.multipleSelection.map(e => e.id)
      this.request.post("/user/del/batch", ids).then(res => {
        if (res) {
          this.$message.success("删除成功！")
          this.load()
        } else {
          this.$message.error("删除失败！")
        }
      })
    },
    //导出excel数据
    exp(){
      // this.request.get("/user/export").then(res=>{
      //   if(res) {
      //     this.$message.success("导出成功")
      //   }else {
      //     this.$message.error("导出失败!")
      //   }
      // })
      window.open("http://localhost:9090/user/export")
    },
    handleImportSuccess(rsponse, file, fileList){
      this.$message.success("导入成功！")
      this.load()
      console.log(esponse, file, fileList)
    }

  },
  created() {
    //  请求分页查询数据
    this.load()
  }
}
</script>

<style scoped lang="scss">
.headerBg {
  background: #eee !important;
}
</style>