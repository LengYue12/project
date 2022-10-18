package user;

import com.lagou.entity.User;
import com.lagou.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/5 20:10
 * @Description 测试User
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-*.xml"})
public class TestUser {

    @Autowired
    private UserService userService;

    @Test
    public void login(){
        User user = userService.login("110", "123");
        System.out.println("user = " + user);
    }


    @Test
    public void checkPhone(){
        Integer i = userService.checkPhone("110");
        System.out.println("i = " + i); // 0：未注册，1：已注册

    }
    
    
    
    @Test
    public void updatePassword(){
        Integer integer = userService.updatePassword(100030018, "111");
        System.out.println("integer = " + integer);
    }
    
    
    @Test
    public void updateUserInfo(){
        Integer integer = userService.updateUserInfo("111", "吕布", 100030018);
        System.out.println("integer = " + integer);
    }
}
