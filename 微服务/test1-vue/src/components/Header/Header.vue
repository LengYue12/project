<template>
  <div class="header-pc-wrap">
    

    <!-- 登录框 开始-->
    <el-dialog
      style="width:800px;margin:0px auto;"
      title=""
      :visible.sync="dialogFormVisible">
      <h1 style="font-size:34px;color:#00B38A">拉勾</h1>

      <el-tabs v-model="activeName"  style="margin:0px auto;">
        <el-tab-pane label="手机登录" name="first">
          <div id="loginForm">
            <el-form>
              <el-form-item>
                <el-input v-model="phoneNumber" placeholder="请输入手机号"></el-input>
              </el-form-item>
              <el-form-item >
                <el-input v-model="smsCode" placeholder="请输入验证码"></el-input> 
                <div class="get-verify-code" @click="sendSms">
                  {{ smsCodeTimeSecond>0 ? (smsCodeTimeSecond + 's 后重试') : '获取验证码' }}
                </div>
              </el-form-item>
            </el-form>
            <el-button
              style="width:100%;margin:0px auto;background-color: #00B38A;font-size:20px"
              type="primary"
              @click="loginPhone">登 录</el-button>
            <p></p>
          </div>
        </el-tab-pane>
        <el-tab-pane label="帐号密码登录" name="second">
          <!-- 登录表单-->
          <div id="loginForm">
            <el-form>
              
              <el-form-item>
                <el-input v-model="phone" placeholder="请输入帐号..."></el-input>
              </el-form-item>
              <el-form-item>
                <el-input v-model="password" type="password" placeholder="请输入密码..."></el-input>
              </el-form-item>
            </el-form>

            <el-button
              style="width:100%;margin:0px auto;background-color: #00B38A;font-size:20px"
              type="primary"
              @click="login">登 录</el-button>
            <p></p>
          </div>
        </el-tab-pane>
      </el-tabs>
      

        <!-- 微信登录图标 -->
        <img
          @click="goToLoginWX"
          src="http://www.lgstatic.com/lg-passport-fed/static/pc/modules/common/img/icon-wechat@2x_68c86d1.png"
          alt=""
        />

      <!-- 二维码 -->
      <div id="wxLoginForm"></div>

    </el-dialog>
    <!-- 登录框 结束-->

    <!-- 顶部登录条 -->
    <div class="wrap-box">
      <div @click="toToIndex" class="edu-icon"></div>
      <div @click="toToIndex" class="text">拉勾教育</div>
      <div class="right-var-wrap" v-if="!isLogin">
        <div class="login-handler" @click="goToLogin">登录 | 注册</div>
      </div>
      <div class="right-var-wrap" v-if="isLogin">
        <div
          :class="{ 'tip-icon': true, 'has-new-message': isHasNewMessage }"
          @click="toToNotic"
        >
          <i class="el-icon-bell"></i>
        </div>
        <img :src="user.portrait" class="avatar-wrap" />
        <div class="bar-wrap">
          <ul class="account-bar" >
            <li class="user_dropdown" data-lg-tj-track-code="index_user" >
              <span class="unick">{{ user.nickname }}</span>
              <i />
              <ul style="">
                <li @click="goToSetting">
                  账号设置
                </li>
                <li @click="logout">
                  退出
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import wxlogin from 'vue-wxlogin'; // 引入
import { Col, Collapse } from 'element-ui';
import jwtDecode from 'jwt-decode'

export default {
  name: "Header",
  components:{
    // wxlogin  // 声明引用的组件
  },
  props: {},
  data() {
    return {
      isLogin: false, // 登录状态，true：已登录，false：未登录
      userDTO:null, // 用来保存登录的用户信息
      user:null,    // 从token中解析获得user信息
      isHasNewMessage: false, // 是否有新的推送消息
      dialogFormVisible: false, // 是否显示登录框，true：显示，false：隐藏
      phone: "", // 双向绑定表单 手机号
      password: "", // 双向绑定表单 密码
      // appid:"wxd99431bbff8305a0", // 应用唯一标识，在微信开放平台提交应用审核通过后获得
      // scope:"snsapi_login", // 应用授权作用域，网页应用目前仅填写snsapi_login即可
      // redirect_uri:"http://www.pinzhi365.com/user/wxlogin",  //重定向地址，(回调地址)
      x:null,
      token:null,
      smsCodeTimeSecond:0, // 验证码倒计时的秒数
      phoneNumber:null, // 发送验证码的手机号
      smsCode:null, // 输入的验证码
      resultPhoneNumber:null, // 返回的手机号
      resultSmsCode:null,// 返回的验证码
    };
  },
  computed: {
  },
  watch: {
  },
  mounted() {
  },
  created(){
    // 从url中获取token参数，判断是否是微信扫码登录的token
    let token = this.getValueByUrlParams('token');
    
    // 判断token是空引用或者token是空字符串
    // 如果在url路径中获取不到token，那么去cookie里获取，就是普通登录
    if(token == null || token == ""){
      // 从cookie中获取user的token
    token = this.getCookie("user");
    }

    // 赋值token为全局
    this.token = token;

    console.log("刷新页面token=>" + token);
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
          // 返回值里没有token，既然状态=4，说明校验通过，所以还是用原来的token，并重置cookie过期时间
          this.setCookie("user",token,600);
          this.user = jwtDecode(token);
        }
      })
      .catch( (error)=>{
        //this.$message.error("登录失败！");
      });
    }


!(function(a, b, c) {
      function d(a) {
        var c = "default";
        a.self_redirect === !0
          ? (c = "true")
          : a.self_redirect === !1 && (c = "false");
        var d = b.createElement("iframe"),
          e =
            "https://open.weixin.qq.com/connect/qrconnect?appid=" +
            a.appid +
            "&scope=" +
            a.scope +
            "&redirect_uri=" +
            a.redirect_uri +
            "&state=" +
            a.state +
            "&login_type=jssdk&self_redirect=" +
            c +
            "&styletype=" +
            (a.styletype || "") +
            "&sizetype=" +
            (a.sizetype || "") +
            "&bgcolor=" +
            (a.bgcolor || "") +
            "&rst=" +
            (a.rst || "");
          (e += a.style ? "&style=" + a.style : ""),
          (e += a.href ? "&href=" + a.href : ""),
          (d.src = e),
          (d.frameBorder = "0"),
          (d.allowTransparency = "true"),
          (d.sandbox = "allow-scripts allow-top-navigation allow-same-origin"), // 允许多种请求
          (d.scrolling = "no"),
          (d.width = "300px"),
          (d.height = "400px");
        var f = b.getElementById(a.id);
        (f.innerHTML = ""), f.appendChild(d);
      }
      a.WxLogin = d;
    })(window, document);
    
  },
  methods: {
    goToSetting() {
      this.$router.push("/setting"); // 跳转个人设置页面
    },
    goToLogin() {
      this.dialogFormVisible = true; // 显示登录框
    },
    login(){ // 前去登录
      return this.axios
      .get("http://localhost:80/user/login" , {
        params:{
          phone:this.phone,
          password:this.password
        }
      })
      .then( (result)=>{
        console.log( result );
        if(result.data.state == 3){ // 登录成功
          // 1.关闭登录框
          this.dialogFormVisible = false ; 
          // 2.更新登录状态
          this.isLogin = true;

          // 3.将返回的toke保存到cookie中
          this.setCookie("user",result.data.token,600);

          // 4.解析token中的数据（昵称和头像）
          const code = jwtDecode(result.data.token);
          this.user = code;
          console.log(this.user);
          // 刷新页面
          this.$router.go(0);
          
          //this.userDTO = result.data; // 保存返回数据中的用户对象信息  
          //localStorage.setItem("user", JSON.stringify(this.userDTO)); // 将登录成功的对象信息保存到本地储存中
        }
        else{
          // 登录失败，帐号密码不匹配
          this.$message.error(result.data.message);
        }
      } )
      .catch( (error)=>{
        this.$message.error("登录失败！");
      });
    },
    // 微信登录
    goToLoginWX() {
      // 普通的登录表单隐藏
      document.getElementById("loginForm").style.display = "none";
      // 显示二维码的容器
      document.getElementById("wxLoginForm").style.display = "block";

      // 生成二维码
      // 待dom更新之后再用二维码渲染其内容
      this.$nextTick(function(){
        this.createCode(); // 直接调用会报错：TypeError: Cannot read property 'appendChild' of null
      });

    },
    // 生成二维码
    createCode(){
      var obj = new WxLogin({
          id:"wxLoginForm", // 挂载点，二维码的容器
          appid:"wxd99431bbff8305a0", // 应用唯一标识，在微信开放平台提交应用审核通过后获得
          scope:"snsapi_login", // 应用授权作用域，网页应用目前仅填写snsapi_login即可
          redirect_uri:"http://www.pinzhi365.com/user/wxlogin",  //重定向地址，(回调地址)
          href: "data:text/css;base64,LmltcG93ZXJCb3ggLnFyY29kZSB7d2lkdGg6IDIwMHB4O30NCi5pbXBvd2VyQm94IC50aXRsZSB7ZGlzcGxheTogbm9uZTt9DQouaW1wb3dlckJveCAuaW5mbyB7d2lkdGg6IDIwMHB4O30NCi5zdGF0dXNfaWNvbiB7ZGlzcGxheTogbm9uZX1jcw0KLmltcG93ZXJCb3ggLnN0YXR1cyB7dGV4dC1hbGlnbjogY2VudGVyO30=" // 加载修饰二维码的css样式
      });
    },
    toToIndex() {
      this.$router.push("/"); //回到首页
    },
    toToNotic(){
    },
    //登出
    logout(){ 
      this.delCookie("user");
      this.isLogin = false; // 更新登录状态
      alert('谢谢使用，再见！');

      this.$router.push("/"); //回到首页
      
      // 去redis删除token
      this.axios
      .get("http://localhost:80/user/logout",{
        params:{
          token:this.token
        }
      })
      .then( (result)=>{
        
        // 重定向到未登录的首页
      this.$router.go(0);

      })
      .catch( (error)=>{
        //this.$message.error("登录失败！");
      });
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
    // 给手机发验证码
    sendSms(){
      return this.axios
      .get("http://localhost:80/user/sendSms" , {
        params:{
          phoneNumber:this.phoneNumber,
        }
      })
      .then( (result)=>{
        console.log( result );
        if(result){ 
          console.log(result);
          this.resultPhoneNumber = result.data.phoneNumber;
          this.resultSmsCode = result.data.smsCode;
          // 验证码发送成功
          if(result.data.Code == "OK"){
            // 调用验证码倒计时方法
          this.setTimerCode();
          }
        }
        
      } )
      .catch( (error)=>{
        this.$message.error("发送验证码失败！");
      });
    },
    // 验证码倒计时
    setTimerCode(){
      this.smsCodeTimeSecond = 60;
      let codeTimer = setInterval(() => {
       this.smsCodeTimeSecond--;
       if(this.smsCodeTimeSecond === 0){
        clearInterval(codeTimer);
       } 
      },1000);
    },
    // 手机验证码登录
    loginPhone(){
      if(this.phoneNumber == this.resultPhoneNumber && this.smsCode == this.resultSmsCode){
        return this.axios
          .get("http://localhost:80/user/loginPhoneSms" , {
            params:{
              phoneNumber:this.phoneNumber
            }
          })
          .then( (result)=>{
            console.log( result );
            if(result.data.state == 3){
            // 1.关闭登录框
            this.dialogFormVisible = false ; 
            // 2.更新登录状态
            this.isLogin = true;
            // 3.将返回的token保存在cookie中
            this.setCookie("user",result.data.token,600);
            // 4.解析token中的数据（昵称和头像）
            const code = jwtDecode(result.data.token);
            this.user = code;
            console.log( this.user );
        }
      } )
      .catch( (error)=>{
        this.$message.error("发送验证码失败！");
      }); 
      }
    }
},
};
</script>

<style lang="less" s coped>
.header-pc-wrap {
  width: 100%;
  height: 40px;
  background: rgba(35, 39, 43, 1);
}
.wrap-box {
  width: 1200px;
  height: 100%;
  margin: 0 auto;
}
.edu-icon {
  float: left;
  width: 24px;
  height: 24px;
  background: url("./static/img/Icon@2x.png") no-repeat;
  background-size: 100% 100%;
  margin-top: 8px;
}
.text {
  font-size: 16px;
  font-weight: 500;
  color: rgba(255, 255, 255, 1);
  line-height: 40px;
  float: left;
  margin-left: 6px;
}
.login-handler {
  font-size: 12px;
  font-weight: 500;
  color: rgba(255, 255, 255, 1);
  line-height: 40px;
  float: left;
  cursor: pointer;
}
.right-var-wrap {
  float: right;
  font-size: 0;
  text-align: right;
}
.tip-icon,
.avatar-wrap,
.bar-wrap {
  display: inline-block;
  vertical-align: top;
}
.tip-icon {
  font-size: 16px;
  line-height: 40px;
  margin-right: 26px;
  color: #818895;
  cursor: pointer;
  &:hover {
    color: #fff;
  }
  &.has-new-message {
    position: relative;
    &:after {
      content: " ";
      display: inline-block;
      position: absolute;
      top: 50%;
      width: 6px;
      height: 6px;
      border-radius: 3px;
      background: red;
      right: 0;
      margin-top: -7px;
    }
  }
}
.user_dropdown {

  position: relative;
  cursor: pointer;
  font-size: 14px;
  text-align: center;
  &:hover {
    .unick {
      color: #fff;
    }
    i {
      -webkit-transform: rotate(180deg);
      -moz-transform: rotate(180deg);
      -ms-transform: rotate(180deg);
      -o-transform: rotate(180deg);
      transform: rotate(180deg);
      animation-fill-mode: forwards;
      border-color: #fff transparent transparent;
    }
    > ul {
      display: block;
      position: absolute;
      top: 40px;
      width: 100%;
      min-width: 80px;
      line-height: 30px;
      background-color: #32373e;
      z-index: 1000;
      list-style: none; margin:0px;padding:0px;
      > li {
        width: 100%;
        height: 30px;
        &:hover {
          background-color: #25282d;
          color: #fff;
        }
      }
    }
  }
  .unick {
    display: block;
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    color: #afb5c0;
    max-width: 96px;
    margin: 0 9px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    &:hover {
      color: #fff;
    }
  }
  > i {
    position: absolute;
    top: 17px;
    right: 0;
    font-size: 0;
    height: 0;
    width: 0;
    border-width: 5px 4px 0;
    border-style: solid dashed;
    border-color: #afb5c0 transparent transparent;
    overflow: hidden;
    -webkit-transition: all 0.4s ease 0s;
    -moz-transition: all 0.4s ease 0s;
    -ms-transition: all 0.4s ease 0s;
    -o-transition: all 0.4s ease 0s;
    transition: all 0.4s ease 0s;
  }
  > ul {
    display: none;
    color: #afb5c0;
  }
}
.avatar-wrap {
  width: 24px;
  height: 24px;
  margin-top: 8px;
  border-radius: 50%;
}
</style>
