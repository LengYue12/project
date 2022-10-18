<template>
    <div>
        <Header></Header>
    <!-- 面包屑导航 -->
      <div class="nav-wrap">
        <p class="nav-p-pc" style="margin-top:25px;text-align:left;">
          <a href="/">课程列表</a>
          <span class="sharp-content">&gt;</span>
          <span class="nav-sec">账号设置</span>
        </p>
      </div>
    
    <!-- 侧栏框 -->
  <el-col :span="4">
    <el-menu
      class="el-menu-vertical-demo">
        <el-menu-item>
          <i class="el-icon-setting"></i>
        <span slot="title">基本设置</span>
      </el-menu-item>
      <el-menu-item>
        <i class="el-icon-setting"></i>
        <span slot="title">账号绑定</span>
      </el-menu-item>
      <el-menu-item>
        <i class="el-icon-setting"></i>
        <span slot="title"  @click="dialogFormVisible = true">修改密码</span>
      </el-menu-item>
    </el-menu>
  </el-col>



<!-- 修改密码的对话框 -->
  <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="当前密码" :label-width="formLabelWidth">
      <el-input  v-model="form.oldePassword"></el-input>
    </el-form-item>
    <el-form-item label="新密码" :label-width="formLabelWidth">
      <el-input v-model="form.newPassword"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" :label-width="formLabelWidth">
      <el-input v-model="form.confirmPassword"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="updatePassword">确 定</el-button>
  </div>
</el-dialog>



<div style="margin-left:300px;text-align:left;">
<span>头像</span>
  <el-upload
  class="avatar-uploader"
  action="http://localhost:80/user/upload"
  name="fname"
  :show-file-list="false"
  :on-success="handleAvatarSuccess"
  :before-upload="beforeAvatarUpload">
  <img v-if="imageUrl" :src="imageUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>


    <p><span>昵称</span></p>
    <el-row :gutter="20">
  <el-col :span="6">
         <el-input placeholder="请输入昵称" v-model="username" clearable></el-input>
  </el-col>
</el-row>
   
    
    <div style="margin-top: 20px">
    <el-button  @click="updateUserInfo" type="success" plain>更新信息</el-button>
    </div>
</div>
    <Footer></Footer>
    </div>
</template>

<script>
import Header from "./Header/Header"; //顶部登录条
import Footer from "./Footer/index"; //顶部登录条

export default {
    name: "Setting",
    components: {
    Header,
    Footer
},


data() {
    return {
        username:"",  // 双向绑定的输入框   当前新昵称
        userDTO:null,    // 从router中拿到的userDTO对象
        imageUrl:"",  // 头像新地址
        dialogFormVisible: false,// 是否显示对话框，true：显示，false：隐藏
        form: {
          oldePassword:'',  //  双向绑定表单 当前密码
          newPassword:'', // 双向绑定表单 新密码
          confirmPassword:''  // 双向绑定表单 确认密码
        },
        formLabelWidth: '120px'
    };
},
created() {
    this.userDTO = this.$route.params.userDTO;  // 从路由中获得参数对象赋值给本组件的参数
    console.log(this.userDTO)
    this.imageUrl = this.userDTO.content.portrait    // 把从router路由中拿到的头像地址给imageUrl ，在头像显示
    this.username = this.userDTO.content.name       // 昵称
    this.form.oldePassword = this.userDTO.content.password    // 显示userDto里的密码 在修改密码的对话框里
},
methods:{
  handleAvatarSuccess(res, file) {  // 图片上传成功
        console.log(res);
        this.imageUrl = res.content  // 获取到后台上传图片接口 返回UserDto里的content。就是portrait 头像新地址，并赋值给imageUrl 
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },

// 更新用户头像与昵称
    updateUserInfo(){

        return this.axios.post("http://localhost:80/user/updateUserInfo",{
          userId:this.userDTO.content.id,   // 从路由里获取到的userDTO里的用户id
          portrait:this.imageUrl,           // 上传图片后，接口返回的服务器的头像地址 
          name:this.username                // 双向数据绑定的  输入框里的昵称
        }).then((res) => {
            console.log(res);
            this.$message.success("更新用户信息成功");

            // 更新到本地储存
            this.userDTO.content.portrait = this.imageUrl;    // 把新上传的头像地址赋值到userDTO
            this.userDTO.content.name = this.username;        // 用户填写的新昵称
            localStorage.setItem("user", JSON.stringify(this.userDTO)); // 将更新成功的对象信息保存到本地储存中
        }).catch((error) => {
            this.$message.error("失败");
        })
    },

    // 更新密码
    updatePassword(){
      if(this.form.newPassword == ""){    // 新密码为空
        this.$message.error("请输入新密码");
        return;
      }
      if(this.form.confirmPassword == ""){    // 二次密码为空
        this.$message.error("请再输入新密码");
        return;
      }
      if(this.form.oldePassword == this.form.newPassword){  // 获取当前用户的密码与新密码进行匹配
        this.$message.warning("新密码与当前密码一样，请更换密码！");
        return;
      }
      if(this.form.newPassword != this.form.confirmPassword){   // 新密码和确认密码进行验证相等
        this.$message.error("两次输入的密码不同!");
        return;
      }

      return this.axios.post("http://localhost:80/user/updatePassword",{
        userId:this.userDTO.content.id,   // 还是从router路由中 获取到的用户id
        password:this.form.newPassword    // 验证通过的新密码（表单里的，用户填写的新密码）
      }).then(res => {
        console.log(res);
        this.$message.success("密码已更新！");
        this.userDTO.content.password = this.form.newPassword;    // 将新密码赋值到router路由里的 userDTO，进行前台更新
        localStorage.setItem("user", JSON.stringify(this.userDTO)); // 将更新成功的对象信息保存到本地储存中
      }).catch(error => {
        this.$message.error("密码更新失败")
      })
      
    }
},
};
</script>

<style scoped>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>