<template>
  <el-card class="form-container" shadow="never">
    <el-form :model="homeAdvertise" :rules="rules" ref="form" label-width="150px" size="small">
      <el-form-item label="广告位名称：" prop="name">
        <el-input v-model="homeAdvertise.name" class="input-width"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave()">提交</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { axios } from "../../utils";

export default {
  name: "HomeAdvertiseDetail",
  //组件传参
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },

  // 数据部分
  data() {
    return {
      homeAdvertise: { name: null },  // 表单对象
      rules: {
        name: [
          { required: true, message: "请输入广告名称", trigger: "blur" },
          {
            min: 2,
            max: 140,
            message: "长度在 2 到 140 个字符",
            trigger: "blur"
          }
        ]
      }
    };
  },

  //钩子函数
  created() {

    // 判断是添加还是修改操作
    if(this.isEdit){
      // 修改
      const id = this.$route.query.id;
      this.loadPromotionSpace(id);
    } else{
      // 新增
      this.homeAdvertise = {};
    }

  },

  methods: {
    //方法1: 保存广告位信息
    handleSave() {
      this.$refs.form.validate(valid => {
        if(!valid) return false;

        // 请求后台
        axios.post("/PromotionSpace/saveOrUpdatePromotionSpace",this.homeAdvertise)
        .then(res => {
          // 返回上个页面
          this.$router.back();
        }).catch(err =>{
          this.$message("数据处理失败了！");
        });

      })
    },

    //方法2: 回显广告位信息
    loadPromotionSpace(id) {
      return axios.get("/PromotionSpace/findPromotionSpaceById?id=" + id).then(res => {
        Object.assign(this.homeAdvertise,res.data.content);
        this.homeAdvertise.id = id;
      }).catch(err => {
         this.$message("数据处理失败了！");
      })
    }
  }
};
</script>
<style scoped>
.input-width {
  width: 70%;
}
</style>
