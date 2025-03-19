import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UserService {


    public void banUser(Integer userId, LocalDateTime banTime) {
        User user = findUserById(userId); // 假设有一个方法可以根据ID查找用户
        if (user != null) {
            user.setIsBanned(1);
            user.setBanUntil(banTime.plus(10, ChronoUnit.DAYS));
            saveUser(user); // 假设有一个方法可以保存用户信息
        }
    }

}