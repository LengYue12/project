package mapper;


import com.lagou.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-05 18:24:56
 */
@Repository
public interface UserDao {

    /**
     * 用户登录
     * @param phone 手机号
     * @param password  密码
     * @return 用户对象
     */
    User login(@Param("phone")String phone,@Param("password")String password);


    /**
     *  检查手机号是否注册过
     * @param phone 手机号
     * @return 0：未注册，1：已注册
     */
    Integer checkPhone(String phone);



    /**
     * 用户注册
     * @param phone 手机号
     * @param password  密码
     * @param nickName 昵称
     * @param headImg   头像
     * @return 受影响的行数
     */
    Integer register(@Param("phone")String phone,@Param("password")String password,@Param("nickName")String nickName,@Param("headImg")String headImg);


    /**
     * 更新用户头像与昵称
     * @param portrait  头像新地址
     * @param name      新昵称
     * @param userId    用户id
     * @return          受影响的行数
     */
    Integer updateUserInfo(@Param("portrait")String portrait,@Param("name")String name,@Param("userId")Integer userId);


    /**
     * 修改密码
     * @param userId    用户Id
     * @param password  验证通过的新密码
     * @return
     */
    Integer updatePassword(@Param("userId")Integer userId,@Param("password")String password);
}

