<template>
  <section class="users">
    <!-- 查询条件表单 -->
    <el-form class="actions" :model="filter" label-position="top" inline>
      <el-form-item label="手机号">
        <el-input v-model="filter.username" placeholder="请输入手机号" />
      </el-form-item>

      <el-form-item label="注册时间">
        <el-date-picker
          v-model="filter.resTime"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          range-separator="至"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleFilter">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table :data="users" v-loading="loading" element-loading-text="数据加载中...">
      <el-table-column prop="id" label="用户ID" width="100"></el-table-column>
      <el-table-column label="头像" min-width="50">
        <template slot-scope="scope">
          <img
            class="avatar"
            :src="
              scope.row.portrait ||
                '//www.lgstatic.com/thumbnail_100x100/i/image2/M01/5E/65/CgotOVszSAOANi0LAAAse2IVWAE693.jpg'
            "
            :alt="scope.row.name"
          />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="用户名" min-width="150"></el-table-column>
      <el-table-column prop="phone" label="手机号" min-width="150"></el-table-column>

      <el-table-column prop="createTime" label="注册时间" min-width="180" :formatter="dateFormatter"></el-table-column>

      <!-- 状态显示 -->
      <el-table-column prop="status" label="状态" align="center" min-width="120">
        <template slot-scope="scope">
          <i class="status status-success" title="正常" v-if="scope.row.status === 'ENABLE'"></i>
          <i class="status status-danger" title="禁用" v-else-if="scope.row.status === 'DISABLE'"></i>
        </template>
      </el-table-column>

      <!-- 禁用按钮  v-if="scope.row.status == 'ENABLE'"-->
      <el-table-column label="操作" align="center" min-width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleToggleStatus(scope.row)"
          >{{ scope.row.status == "ENABLE" ? "禁用" : "启用" }}</el-button>
          <el-button size="mini" type="text" @click="handleSelectRole(scope.row)">分配角色</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      v-if="users.length"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handlePageSizeChange"
      @current-change="handleCurrentPageChange"
      :current-page="page"
      :page-sizes="[10, 20, 30]"
      :page-size="size"
      :total="total"
    />

    <!-- 分配角色对话框 -->
    <el-dialog title="分配角色" :visible.sync="allocDialogVisible" width="30%">
      <el-select v-model="allocRoleIds" multiple placeholder="请选择" size="small" style="width: 80%">
        <el-option v-for="item in allRoleList" :key="item.id" :label="item.name" :value="item.id"></el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="allocDialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleAllocRole()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </section>
</template>

<script>
import { axios } from "../utils";
import { getRoleByAdmin, getRolesById, allocRole } from "@/services/login";
import moment from "moment";

export default {
  name: "Users",
  title: "用户管理",
  data() {
    //日期选择器 选项
    const pickerOptions = {
      shortcuts: [
        {
          text: "最近一周",
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit("pick", [start, end]);
          }
        },
        {
          text: "最近一个月",
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit("pick", [start, end]);
          }
        },
        {
          text: "最近三个月",
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit("pick", [start, end]);
          }
        }
      ]
    };

    //过滤信息
    const filter = {
      username: "",
      resTime: null
    };

    //数据部分
    return {
      pickerOptions, //日期选择器选项设置
      total: 0, //总条数
      size: 10, //每页显示条数
      page: 1, //当前页
      filter,
      users: [], //用户数据
      loading: false,
      allocAdminId: "",
      allocDialogVisible: false,
      allocRoleIds: [], // 保存用户拥有的角色id
      allRoleList: [],  // 下拉列表需要的角色数据
      listQuery: {}
    };
  },
  created() {
    //初始化用户数据
    this.loadUsers();
  },
  methods: {
    //方法1: 加载用户数据
    loadUsers() {
      this.loading = true;

      //设置参数
      const params = { currentPage: this.page, pageSize: this.size };

      //过滤条件
      if (this.filter.username) params.username = this.filter.username;

      //设置日期参数
      if (this.filter.resTime) {
        params.startCreateTime = this.filter.resTime[0];
        params.startCreateTime.setHours(0);
        params.startCreateTime.setMinutes(0);
        params.startCreateTime.setSeconds(0);

        params.endCreateTime = this.filter.resTime[1];
        params.endCreateTime.setHours(23);
        params.endCreateTime.setMinutes(59);
        params.endCreateTime.setSeconds(59);
      }
      //请求后台接口
      return axios
        .post("/user/findAllUserByPage", params)
        .then(res => {
          this.users = res.data.content.list; //用户数据
          this.total = res.data.content.total;
          this.loading = false;
        })
        .catch(err => {
          this.$message("获取数据失败! ! !");
        });
    },

    //方法2: 修改用户状态
    //修改用户状态
    handleToggleStatus(item) {
      return axios
        .get("/user/updateUserStatus", {
          params: {
            id: item.id,
            status: item.status
          }
        })
        .then(res => {
          item.status = res.data.content;
        })
        .catch(err => {
          this.$message("获取数据失败! ! !");
        });
    },

    //条件查询
    handleFilter() {
      this.loadUsers();
    },

    //页码变化触发的函数
    handleCurrentPageChange(page) {
      this.page = page;
      this.loadUsers();
    },

    //每页显示条数变化触发的函数
    handlePageSizeChange(size) {
      this.size = size;
      this.loadUsers();
    },

    //日期格式化
    dateFormatter(row, column, value, index) {
      return moment(value).format("YYYY-MM-DD HH:mm:ss");
    },

    //分配角色
    handleSelectRole(row) {
      //保存用户ID
      this.allocAdminId = row.id;

      //获取角色列表
      this.getRoleList();

      //获取当前用户拥有的角色
      this.getUserRoleById(row.id);

      //打开对话框
      this.allocDialogVisible = true;
    },

    //获取角色列表
    getRoleList(id) {
      return axios.post("/role/findAllRole",this.listQuery).then(res => {
          console.log(res.data.content);
          // 填充下拉列表需要的角色数据
          // 遍历 item就是每个元素
          this.allRoleList = res.data.content.map(item => {
            // 只取每个元素中的 id 和 name
            // 放到allRoleList  里，在select 下拉组件中做遍历展示
            return {id:item.id,name:item.name};
          })
      }).catch(err => {})
    },

    //获取当前用户拥有的角色,默认选中
    getUserRoleById(id) {
      // 传递当前用户id请求后台，获取当前用户所拥有的角色信息
      return axios.get("/user/findUserRoleById?id=" + id).then(res => {
        // 取出数据
        const allocRoleList = res.data.content;

        // 取出数据中的角色id
        this.allocRoleIds = [];

       // 判断后台返回的list为不为空
        if(allocRoleList != null && allocRoleList.length > 0){
          // 遍历list获取id 放到allocRoleIds里
          for(let i = 0; i < allocRoleList.length; i++){
            // 保存id
            this.allocRoleIds.push(allocRoleList[i].id);
          }
        }
      }).catch(err => {})
    },

    // 给用户分配角色
    handleAllocRole() {

      // 准备参数
      const params = {
        // 用户id
        userId:this.allocAdminId,
        // 为当前用户选择的角色id   select框中的ids
        roleIdList:this.allocRoleIds
      }

      return axios.post("/user/userContextRole",params).then(res => {
        // 关闭对话框
        this.allocDialogVisible = false;
      }).catch(err => {})
    }
  }
};
</script>

<style lang="scss">
.users {
  .actions {
    display: flex;
    align-items: flex-end;

    label {
      line-height: 1;
    }
  }

  .btn-add {
    margin-left: auto;
    margin-right: 0;
  }

  .avatar {
    margin-right: 10px;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    box-sizing: border-box;
    vertical-align: middle;
  }

  .name {
    display: inline-block;
    line-height: 30px;
    vertical-align: middle;
  }

  .el-pagination {
    margin-top: 30px;
    text-align: right;
  }
}
</style>
