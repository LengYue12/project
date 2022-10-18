package com.lagou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lagou.entity.FileSystem;
import com.lagou.entity.User;
import com.lagou.entity.UserDTO;
import com.lagou.entity.UserVo;
import com.lagou.user.service.UserService;
import commons.HttpClientUtil;
import entity.Token;
import entity.WxUser;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/5 21:04
 * @Description 服务消费方
 */
@RestController
@RequestMapping("user")
public class UserController {
    private FileSystem fileSystem = null;
    @Reference  // 远程消费
    private UserService userService;

    private UserDTO dto = null; // 是否用微信登录成功，dto为null,则尚未登录

    @GetMapping("login")
    public UserDTO login(String phone, String password,String nickName,String headImg) {
        dto = new UserDTO();
        User user = null;

        if (nickName == null || nickName.isEmpty()) {
            nickName = phone;
        }
        System.out.println("phone = " + phone);
        System.out.println("password = " + password);
        System.out.println("nickName = " + nickName);

        // 首先检测手机号是否注册
        Integer i = userService.checkPhone(phone);
        if (i == 0) {
            // 未注册，自动注册并登录
            userService.register(phone, password,nickName,headImg);
            dto.setMessage("手机号尚未注册，系统已帮您自动注册，请牢记密码！");
            user = userService.login(phone, password);
        } else {
            user = userService.login(phone, password);
            if (user == null) {
                dto.setState(300);
                dto.setMessage("账号密码错误，登录失败！");
            } else {
                dto.setState(200); // 200 表示成功
                dto.setMessage("登录成功！");
            }
        }
        dto.setContent(user);
        return dto;
    }


    /**
     * 修改密码
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("updatePassword")
    public UserDTO updatePassword(@RequestBody UserVo userVo) throws Exception {
        System.out.println("用户id" + userVo.getUserId());
        System.out.println("新密码" + userVo.getPassword());

        // 修改密码，返回受影响的行数
        Integer integer = userService.updatePassword(userVo.getUserId(),userVo.getPassword());

        dto = new UserDTO();
        if (integer == 1) { // 为1，表示更新成功
            // 响应
            dto.setState(200);
            dto.setSuccess(true);
            dto.setMessage("修改成功");
        } else {    // 为0，表示失败
            dto.setState(400);
            dto.setSuccess(false);
            dto.setMessage("修改失败");
        }
        return dto;
    }


    /**
     * 更新用户头像与昵称
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("updateUserInfo")
    public UserDTO updateUserInfo(@RequestBody UserVo userVo) throws Exception {
        System.out.println("用户id" + userVo.getUserId());
        System.out.println("新昵称" + userVo.getName());
        System.out.println("头像地址" + userVo.getPortrait());

        // 更新用户头像与昵称，返回受影响的行数
        Integer integer = userService.updateUserInfo(userVo.getPortrait(), userVo.getName(), userVo.getUserId());

        dto = new UserDTO();
        if (integer == 1) { // 为1，表示更新成功
            // 响应
            dto.setState(200);
            dto.setSuccess(true);
            dto.setMessage("修改成功");
        } else {    // 为0，表示失败
            dto.setState(400);
            dto.setSuccess(false);
            dto.setMessage("修改失败");
        }
        return dto;
    }



    /**
     *
     * @param request 多部件表单的请求对象
     * @return  上传文件对象的json对象
     * @throws Exception
     *
     *
     * 上传文件的流程：
     *  1.先把文件保存到web服务器上
     *  2.再从web服务器上将文件 上传到 FastDFS上
     */
    @PostMapping("upload")
    private UserDTO upload(MultipartHttpServletRequest request) throws Exception {

        // 1.把文件保存到web服务器
        // 从页面请求中，获取上传的文件对象
        MultipartFile file = request.getFile("fname");


            // 从文件对象中获取文件的原始名
            String oldFileName = file.getOriginalFilename();
            // 通过字符串截取的方式，从文件原始名中获取文件的后缀名
            String hou = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
            // 为了避免文件因为同名而覆盖，生成全新的文件名
            String newFileName = UUID.randomUUID().toString() + "." + hou;
            // 创建web服务器保存文件的目录
            File toSaveFile = new File("D:/upload/" + newFileName);
            // 将路径转换成文件
            file.transferTo(toSaveFile);
            // 获取服务器的绝对路径
            String newFilePath = toSaveFile.getAbsolutePath();


            // 2.把文件从web服务器上传到FastDFS

            // 加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");

            // 创建tracker客户端
            TrackerClient trackerClient = new TrackerClient();
            // 通过tracker客户端获取tracker的连接服务并返回
            TrackerServer trackerServer = trackerClient.getConnection();
            // 声明storage服务
            StorageServer storageServer = null;

            // 定义storage客户端
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] list = new NameValuePair[1];
            list[0] = new NameValuePair("fileName",oldFileName);
            String fileId = client.upload_file1(newFilePath, hou, list);
            System.out.println("fileId = " + fileId);
            trackerServer.close();


            
            String portrait = "http://www.fdfsstorage.com/" + fileId;
            // fileSystem.setFilePath(portrait);   // 已经上传到FastDFS上，通过fileId来访问图片，所以fileId即为文件路径
            dto = new UserDTO();

            dto.setContent(portrait);
            dto.setMessage("图片上传成功");
            dto.setState(200);
            dto.setSuccess(true);

            return dto;
    }



    @GetMapping("wxlogin")
    public String wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 1.微信官方发给我们一个临时凭证
        String code = request.getParameter("code");
        System.out.println("【临时凭证】code = " + code);

        // 2.通过code，去微信官方申请一个正式的token（令牌）
        // 发出一个get请求    httpclient -封装>
        String getTokenByCode_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxd99431bbff8305a0&secret=60f78681d063590a469f1b297feff3c4&code="+code+"&grant_type=authorization_code";
        String tokenString = HttpClientUtil.doGet(getTokenByCode_url);
        System.out.println("tokenString = " + tokenString);

        // 将json格式的token字符串转换成实体对象，方便存取
        Token token = JSON.parseObject(tokenString, Token.class);

        // 3.通过token，去微信官方获取用户的信息
        String getUserByToken_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token.getAccess_token()+"&openid="+token.getOpenid();

        String userInfoString = HttpClientUtil.doGet(getUserByToken_url);
        System.out.println("userInfoString = " + userInfoString);
        // 将json格式的user字符串转换成实体对象，方便存取
        WxUser wxUser = JSON.parseObject(userInfoString, WxUser.class);
        System.out.println("微信的昵称 = " + wxUser.getNickname());
        System.out.println("微信的头像 = " + wxUser.getHeadimgurl());


        // 拉勾的业务流程！ 需要 手机号(wxUser.getUnionid())和密码(wxUser.getUnionid()),头像和昵称

        login(wxUser.getUnionid(),wxUser.getUnionid(),wxUser.getNickname(),wxUser.getHeadimgurl());

        response.sendRedirect("http://localhost:8080/#/");
        return  null;
    }


    @GetMapping("checkWxStatus")
    public UserDTO checkWxStatus(){

        return dto;
    }


    @GetMapping("logout")
    public Object logout(){
        dto = null;
        return null;
    }
}