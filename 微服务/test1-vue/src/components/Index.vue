<template>
  <div>
    <!--头部登录导航条-->
    <Header></Header>

    <!--首页顶部轮播广告-->
    <div style=" width:1200px; margin:0px auto; margin-top:20px;">
      <el-carousel indicator-position="outside">
        <el-carousel-item v-for="(item, index) in adList" :key="index">
          <a :href="item.link">
            <img :src="item.img" style="width:100%;height:100%;object-fit:cover"/>
          </a>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!--容器-->
    <div style="width: 1200PX; min-height:500px; margin:0px auto;">

      <!--课程列表-->
      <div style="width: 850PX;display: inline-block;  ">
        <el-tabs v-model="activeName">
          <el-tab-pane label="选课" name="allLesson">
            <ul class="course-ul-pc">
              <!-- 课程信息展示开始 -->
              <li class="course-li" v-for="(item , index) in courseList" :key="index">
                <!-- 课程封面图 -->
                <img
                  :src="item.courseImgUrl"
                  class="teacher-portrait hover-pointer"
                />
                <!-- 课程文字信息 -->
                <div class="content-main">
                  <!-- 课程标题 -->
                  <div class="content-title hover-pointer">
                    <div
                      class="p-title"
                      style="text-align:left;"
                      @click="gotoDetail(item)"
                    >
                      <span>
                        {{item.courseName}}
                      </span>
                    </div>
                    <!-- 作者和职称 -->
                    <p class="p-title-buy text-overflow">
                      <span class="p-author-span">
                        {{item.teacher.teacherName}}
                      </span>
                      <span class="p-author-line" />
                      <span class="p-author-span">
                        {{item.teacher.position}}
                      </span>
                    </p>
                    <p></p>
                    <!-- 课程简单描述 -->
                    <p class="p-describe" style="text-align:left;">
                      {{item.brief}}
                    </p>
                  </div>
                  <!-- 课程前两个章节信息 -->
                  <ul class="content-course" style="text-align:left;">
                    <!-- 章节1 : 免费试看，通常是第一章的前两节课 -->
                    <li
                      class="content-course-lesson text-overflow"
                      style="width:300px" 
                      v-for="(lesson , index) in item.lessonsDTO2" :key="index">
                      <!-- 免费试看图标 -->
                      <img
                        src="@/assets/course-list/free-course.png"
                        class="free-label hover-pointer"
                      />
                      <span class="theme-span hover-pointer">
                        {{lesson.theme}}
                        </span>
                    </li>
                    
                  </ul>
                  <!-- 价格信息 -->
                  <div class="content-price" style="text-align:left;">
                    <p class="content-price-p">
                      <span class="content-price-orange-sm">￥</span>
                      <span class="content-price-orange">{{item.discounts}}</span>
                      <span class="current-price">
                        <span class="current-price-unite">￥</span>
                        {{item.price}}
                      </span>
                      <span class="activity-name">成就自己</span>
                      <span class="content-price-buy">{{item.sales}}人购买</span>
                    </p>
                    <div class="btn btn-green btn-offset" @click="buy(item)">立即购买</div>
                  </div>
                </div>
              </li>
              <!-- 课程信息结束 -->
              
            </ul>

          <!-- 微信支付二维码
          <el-dialog :visible.sync="dialogFormVisible" :before-close="cancelOrder" :modal="true" :close-on-click-modal="false" style="width:800px;margin:0px auto;" >
            <h1 style="font-size:30px;color:#00B38A" >微信扫一扫支付</h1>
            <div id="qrcode" style="width:210px;margin:20px auto;"></div>
            <h2 id="statusText"></h2>
            <p id="closeText"></p>
          </el-dialog>
          -->

          </el-tab-pane>
          <el-tab-pane label="已购" name="hasPay">
            <div v-if="!isLogin">
              <img src="@/assets/course-list/no-login@2x.png" class="no-data-icon"/>
              <div class="no-data-title">您还没有登录</div>
              <div class="no-data-title">登录后即可查看已购课程</div>
              <div class="btn btn-yellow btn-center">立即登录</div>
            </div>
            <div v-if="isLogin">
              <ul class="course-ul-pc">
              <!-- 课程信息展示开始 -->
              <li class="course-li" v-for="(item , index) in myCourseList" :key="index">
                <!-- 课程封面图 -->
                <img
                  :src="item.courseImgUrl"
                  class="teacher-portrait hover-pointer"
                />
                <!-- 课程文字信息 -->
                <div class="content-main">
                  <!-- 课程标题 -->
                  <div class="content-title hover-pointer">
                    <div
                      class="p-title"
                      style="text-align:left;"
                      @click="gotoDetail(item)"
                    >
                      <span>
                        {{item.courseName}}
                      </span>
                    </div>
                    <!-- 作者和职称 -->
                    <p class="p-title-buy text-overflow">
                      <span class="p-author-span">
                        {{item.teacher.teacherName}}
                      </span>
                      <span class="p-author-line" />
                      <span class="p-author-span">
                        {{item.teacher.position}}
                      </span>
                    </p>
                    <p></p>
                    <!-- 课程简单描述 -->
                    <p class="p-describe" style="text-align:left;">
                      {{item.brief}}
                    </p>
                  </div>
                  <!-- 课程前两个章节信息 -->
                  <ul class="content-course" style="text-align:left;">
                    <!-- 章节1 : 免费试看，通常是第一章的前两节课 -->
                    <li
                      class="content-course-lesson text-overflow"
                      style="width:300px" 
                      v-for="(lesson , index) in item.lessonsDTO2" :key="index">
                      <!-- 免费试看图标 -->
                      <img
                        src="@/assets/course-list/free-course.png"
                        class="free-label hover-pointer"
                      />
                      <span class="theme-span hover-pointer">
                        {{lesson.theme}}
                        </span>
                    </li>
                    
                  </ul>
                  <!-- 价格信息 -->
                  <div class="content-price" style="text-align:left;">
                    <p class="content-price-p">
                      <span class="content-price-orange-sm">￥</span>
                      <span class="content-price-orange">{{item.discounts}}</span>
                      <span class="current-price">
                        <span class="current-price-unite">￥</span>
                        {{item.price}}
                      </span>
                      <span class="activity-name">成就自己</span>
                      <span class="content-price-buy">{{item.sales}}人购买</span>
                    </p>
                    <div class="btn btn-yellow btn-offset">好好学习</div>
                  </div>
                </div>
              </li>
              <!-- 课程信息结束 -->
              
            </ul>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>

      <!--微信连接-->
      <div style="width:330px; height:350px; display: inline-block; float:right;" >
        <p  class="title">精选文章</p> 
        <ul class="article-content">
          <li>即将淘汰的java技术，曾经都是王者</li>
        </ul>
        <p  class="split-line"  style="margin-bottom:20px;"   >
          <span  class="split-word">还可以在这里找到我们</span>
        </p> 
        <ul  class="social-list" >
          <li  class="social-item"  >
            <div id="qr-wrapper"  class="qr-wrapper" style="display: none;" @mouseout="hideqr()">
              <img  src="../assets/wechat-qr.47ac3f5.png" alt="二维码" class="qr-wrapper-img">
            </div> 
            <img @mouseover="showqr()"  src="data:image/png;base64,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" alt="微信" class="social-item-img"> 
            <p  class="social-name">微信</p>
          </li>
          <li  class="social-item hover-pointer" @click="gozhihu()" >
              <img  src="data:image/png;base64,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" alt="知乎" class="social-item-img"> 
              <p  class="social-name">知乎</p>
          </li>
          <li  class="social-item hover-pointer" @click="gowb()">
            <img  src="data:image/png;base64,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" alt="微博" class="social-item-img"> 
            <p  class="social-name">微博</p>
          </li>
        </ul>
      </div>

    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header/Header"; //顶部登录条
import Footer from "./Footer/index"; //顶部登录条
import jwtDecode from 'jwt-decode'
export default {
  name: "Index",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      activeName: "allLesson",
      courseList:[],  // 课程集合
      myCourseList:[], // 我购买过的课程列表
      isLogin:false, //登录状态
      user:null, // 已登录的用户对象信息
      adList:null,  // 广告集合
    };
  },
  created() {

    this.getCourseList(); //当组件创建完毕，就调用获取所有课程的方法

    // 加载顶部轮播广告
    this.getAdList();

    // 从url中获取token参数
    let token  = this.getValueByUrlParams('token');

    if(token == null || token == ""){
      // 从cookie中获取user的token
      token = this.getCookie("user");
    }

    this.token = token;
   
    console.log(token);
    if(token != null || token != ""){
      // 将token发送到sso进行校验
      this.axios
      .get("http://localhost:80/user/checkToken",{
        params:{
          token:token
        }
      })
      .then( (result)=>{
        if(result.data.state == 4){
          this.isLogin = true;
          this.setCookie("user",token,600);
          this.user = jwtDecode(token);
          // 获取已购买的课程列表
          this.getMyCourseList();
        }
      })
      .catch( (error)=>{
       });
    }

    
    
  },
  methods: {
    changeCourseTab(tabName) {
      this.classSelect = tabName;
      sessionStorage && sessionStorage.setItem("courseTab", tabName);
    },
    gotoDetail(item) {
      this.$router.push({ name: "Course", params: { course: item } });
    },
    // 去课程微服务获取全部课程
    getCourseList(){ 
      return this.axios
      .get("http://localhost:8004/course/getAllCourse")
      .then((result) => {
        console.log(result);
        this.courseList = result.data;
      }).catch( (error)=>{
        this.$message.error("获取课程信息失败！");
      } );
    },
    // 获取已购的课程列表
    getMyCourseList(){
      return this.axios
  .get("http://localhost:8004/course/getCoursesByUserId/"+this.user.userId).then((result) => {
      console.log(result);
      this.myCourseList = result.data;
  }).catch( (error)=>{
      this.$message.error("获取已购买的课程信息失败！");
  });
    },
    // 加载顶部轮播广告
    getAdList(){
      return this.axios
      .get("http://localhost:9001/ad/ad/getAdsBySpaceId/"+1)
      .then((result) => {
        console.log(result);
        this.adList = result.data;
      }).catch( (error)=>{
        this.$message.error("获取轮播广告失败！");
      } );
    },
    // 显示微信公众号二维码
  showqr(){
      document.getElementById("qr-wrapper").style.display = "block";
  },
  // 隐藏微信公众号二维码
  hideqr(){
    document.getElementById("qr-wrapper").style.display = "none";
  },
  // 跳转到知乎
  gozhihu(){
    window.open('https://www.zhihu.com/org/la-gou-kai-wu-xun-lian-ying');
  },
  // 跳转到微博
  gowb(){
    window.open('https://weibo.com/p/1006066707072494/home?from=page_100606&mod=TAB&is_hot=1#place');
  },
  //设置cookie
setCookie(key,value,expires){
  var exp = new Date();
  exp.setTime(exp.getTime() + expires*1000);
  document.cookie = key + "=" + escape (value) + ";expires=" + exp.toGMTString();
},
    //从cookie中获取token
    getCookie(key){
      var name = key + "=";
      return document.cookie.split(name)[1];
    },
    // 删除cookie
    delCookie(name){
      // 删除cookie只需要将值清空
      // -1 让指定名为name的cookie过期实现自动清除，如果不赋值-1的话，只是将对应的cookie值删除，而这条cookiei记录并没有删除
      this.setCookie(name,'',-1);
    },
    // 获取url中参数
    getValueByUrlParams(paramKey) {
    var url = location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {}
    var i, j
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paramKey.toLowerCase()];
    if (typeof(returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
},
  },
  
};
</script>

<style lang="less" scoped>
@import "../assets/less/common.less";

.el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

p {
  margin-top: 0;
}
li {
  list-style: none;
}
flex {
  display: flex;
}
.btn(@a, @b) {
  background-color: @a;
  color: @b;
  border: 1px solid @a;
}
.course-ads {
  margin-bottom: 20px;
  cursor: pointer;
  width: 100%;
  max-height: 280px;
  overflow: hidden;
  img {
    width: 100%;
    border-radius: 2px;
  }
}
.tab-container {
  width: 100%;
  height: 60px;
  background: #ffffff;
  border-bottom: 1px solid #ededed;
}
.vux-tab.course-tab {
  box-sizing: border-box;
  height: 60px;
  width: 200px;
  overflow: hidden;
  font-size: 20px;
  .vux-tab-container {
    width: 260px;
  }
  .vux-tab-item {
    cursor: pointer;
    background: none;
    line-height: 60px;
    font-size: 20px;
  }
  .vux-tab-ink-bar {
    bottom: -1px;
    .vux-tab-bar-inner {
      height: 2px;
    }
  }
}
.tab-container {
  width: 100%;
  height: 60px;
  background: #ffffff;
  border-bottom: 1px solid #ededed;
}
.course-ul {
  padding: 0 20px;
}
.hover-pointer {
  cursor: pointer;
}
.course-ul-pc .course-li {
  padding: 30px 0 40px 0;
  border-bottom: 1px solid #ededed;
  position: relative;
  &:last-child {
    border-bottom: 0;
  }
}
.current-price {
  font-size: 12px;
  color: #999;
  font-weight: 600;
  text-decoration: line-through;
}
.current-price-unite {
  font-size: 12px;
  margin-right: -4px;
}
.p-author-line {
  content: "";
  display: inline-block;
  width: 1px;
  height: 10px;
  position: absolute;
  background: #999;
  margin-left: 5px;
  top: 3px;
}
.teacher-portrait {
  width: 121px;
  height: 159px;
  border-radius: 4px;
  float: left;
}
.content-main {
  line-height: 1.4;
  margin-left: 141px;
  position: relative;
  height: 150px;
}
.content-main .p-title {
  font-size: 20px;
  color: #333;
  font-family: PingFangSC-Medium, PingFang SC;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 350px;
}
.course-ul-pc .content-main .p-title-buy {
  font-size: 12px;
  color: #999;
  display: inline-block;
  margin-top: 6px;
  position: relative;
  float: right;
  top: -28px;
  font-weight: 400;
}
p {
  margin-top: 0;
}
.course-ul-pc .p-author-line {
  content: "";
  display: inline-block;
  width: 1px;
  height: 10px;
  position: absolute;
  background: #999;
  margin-left: 5px;
  top: 3px;
}
.text-overflow {
  word-wrap: normal;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.course-ul-pc .content-main .p-describe {
  font-size: 14px;
  margin-top: 5px;
  color: #333;
  word-wrap: normal;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.course-ul-pc .content-main .p-author-span:not(:first-child) {
  margin-left: 18px;
}
.course-ul-pc .content-main .content-course {
  font-size: 14px;
  padding-top: 25px;
  color: #666;
}

ol,
ul {
  margin: 0;
  padding: 0;
  list-style: none;
}
* {
  -webkit-tap-highlight-color: transparent;
}
.course-ul-pc .content-main .content-course-lesson {
  display: inline-block;
  width: 304px;
  margin: 2px 40px 6px 0;
}
.course-ul-pc .content-main .content-course .free-label {
  width: 50px;
  height: 16px;
  margin: 1px 5px 0 0;
}
.course-ul-pc .content-main .content-course-lesson:nth-child(2n) {
  margin-right: 0;
}

.course-ul-pc .content-main .content-course-lesson {
  display: inline-block;
  width: 304px;
  margin: 2px 40px 6px 0;
}
.course-ul-pc .content-main .content-price {
  height: 28px;
  line-height: 28px;
  position: absolute;
  width: 100%;
  bottom: -4px;
}
.course-ul-pc .content-main .content-price-p {
  font-size: 0;
}
.course-ul-pc .content-main .content-price-orange-sm {
  color: #ff7452;
  font-size: 14px;
}

.content-course {
  font-size: 14px;
  padding-top: 25px;
  color: #666;
}
.content-course.free-label {
  width: 50px;
  height: 16px;
  margin: 1px 5px 0 0;
}

.content-course-lesson {
  display: inline-block;
  width: 304px;
  margin: 2px 40px 6px 0;
}
.content-course-lesson:nth-child(2n) {
  margin-right: 0;
}
.disabled-word {
  color: #999;
  cursor: default;
}
.theme-span :hover {
  color: #0abf89;
}

.content-price-p {
  font-size: 0;
}
.content-price-orange {
  color: #ff7452;
  font-size: 20px;
}
.content-price-buy {
  font-size: 14px;
  color: #999;
  margin-left: 20px;
  transform: translate(8px, -2px);
  display: inline-block;
}
.content-price-grey {
  color: #999;
  font-size: 12px;
  margin-left: 5px;
}
.content-price-buyed {
  color: #999;
  font-size: 14px;
}
.content-price {
  height: 28px;
  line-height: 28px;
  position: absolute;
  width: 100%;
  bottom: -4px;
}
.activity-name {
  font-size: 12px;
  line-height: 14px;
  color: rgba(255, 255, 255, 1);
  padding: 2px 4px;
  background: linear-gradient(
    180deg,
    rgba(255, 137, 110, 1) 0%,
    rgba(255, 123, 92, 1) 100%
  );
  border-radius: 2px;
  display: inline-block;
  transform: translate(6px, -3px);
}
.p-title {
  font-size: 20px;
  color: #333;
  font-family: PingFangSC-Medium, PingFang SC;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 350px;
}
.p-title-dec {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
}
.p-title-buy {
  // .font(12px);
  font-size: 12px;
  color: #999;
  display: inline-block;
  margin-top: 6px;
  position: relative;
  // .fr;
  top: -28px;
  font-weight: normal;
}

.companion-reading {
  background: rgba(0, 179, 138, 0.06);
  border-radius: 2px;
  font-family: PingFangSC-Regular;
  font-size: 12px;
  color: #00b38a;
  display: inline-block;
  padding: 0 8px;
  height: 22px;
  line-height: 22px;
  margin-left: 10px;
  vertical-align: text-bottom;
  position: relative;

  .triangle {
    position: absolute;
    top: 12px;
    left: -3px;
    width: 0;
    height: 0;
    content: "";
    border: 5px solid;
    border-color: transparent transparent white white;
    transform: rotate(45deg);
    box-shadow: -2px 2px 2px rgba(0, 0, 0, 0.08);
  }
}
.companion-tip {
  display: none;
  position: absolute;
  font-family: HiraginoSansGB-W3;
  font-size: 14px;
  color: #777777;
  background: #ffffff;
  border: 1px solid #e3ebe9;
  box-shadow: 0 2px 3px 0 rgba(0, 0, 0, 0.08);
  border-radius: 3px;
  line-height: 29px;
  top: -7px;
  word-break: keep-all;
  left: 80px;
  padding: 0 10px;
  width: 200px;
  z-index: 2;
}
.companion-reading:hover .companion-tip {
  display: inline-block;
}
.companion-reading:hover .companion-tip {
  display: block;
}
.p-describe {
  // .font(14px);
  font-size: 14px;
  margin-top: 5px;
  color: #333;
  // .text-overflow;
}
.p-author + p {
  margin-top: 0;
}
.p-author {
  margin: 10px 0 5px 0;
  // .font(12px);
  font-size: 12px;
  color: #999;
  position: relative;

  .p-author-line {
    content: "";
    display: inline-block;
    width: 1px;
    height: 10px;
    position: absolute;
    background: #999;
    margin-left: 5px;
    top: 3px;
  }
}
.p-author-span:not(:first-child) {
  margin-left: 18px;
}

.course-ul-container {
  width: 850px;
  display: inline-block;
  float: left;
}
.course-ul {
  padding: 0 20px;
}
.course-ul-pc {
  width: 790px;
  background-color: #fff;
  padding: 0 30px;
  // .fl;
}
.tag-icon {
  width: 57px;
  height: 18px;
  position: absolute;
  top: 30px;
  left: 0;
}

.more-icon {
  width: 10px;
  height: 10px;
  vertical-align: middle;
}
.btn {
  width: 94px;
  height: 34px;
  text-align: center;
  line-height: 34px;
  cursor: pointer;
  border-radius: 3px;
  font-size: 14px;
  box-sizing: content-box;
  &-green {
    .btn(#00b38a, #fff);
    &:hover {
      background: #00a57f;
      border-color: #00a57f;
    }
  }
  &-yellow {
    .btn(#fcd766, #fff);
    &:hover {
      background: #f0d071;
      border-color: #f0d071;
    }
  }
  &-white {
    .btn(#fff, #00b38a);
    border: 1px solid #00b38a;
    &:hover {
      color: #fff;
      border-color: #00b38a;
      background-color: #00b38a;
    }
  }
  &-offset {
    position: absolute;
    right: 0;
    top: -4px;
  }
}

.btn-center {
  margin: 10px auto;
}

.no-data-title {
  font-size: 20px;
  color: #333;
  font-weight: 500;
}
</style>
