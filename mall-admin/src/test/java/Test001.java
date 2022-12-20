import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;
/**
 * @Description: TODO hutool加密 验证密码
 */
public class Test001 {
    //hutool加密
    @Test
    public void jiami(){
        String pwd = "112";

        //将密码进行加密操作
        String encodePwd = BCrypt.hashpw(pwd);

        System.out.println("原："+pwd);
        System.out.println("加密后："+encodePwd);
    }
    //hutool解密
    @Test
    public void checkmi(){
        String pwd = "111";
        String enpwd = "$2a$10$usINqMV0TUZ8R5EIwR1AMOKkICwVHffcjsbvrjppVzpsRVKgJBaTi";

        //验证密码, Bcrypt为spring的包, 第一个参数为明文密码, 第二个参数 为密文密码
        boolean checkpw = BCrypt.checkpw(pwd, enpwd);
        System.out.println("密码是否正确："+checkpw);
    }
}
