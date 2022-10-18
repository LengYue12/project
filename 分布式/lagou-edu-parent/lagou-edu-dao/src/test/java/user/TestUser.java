package user;

import com.lagou.entity.User;
import mapper.UserDao;
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
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class TestUser {

    @Autowired
    private UserDao userDao;

    @Test
    public void login(){
        User user = userDao.login("110", "123");
        System.out.println("user = " + user);
    }


    @Test
    public void checkPhone(){
        Integer i = userDao.checkPhone("1101");
        System.out.println("i = " + i); // 0：未注册，1：已注册

    }


    @Test
    public void register(){
        //Integer i = userDao.register("114", "123");
        //System.out.println("i = " + i); // 0：注册失败，1：注册成功
    }
}
