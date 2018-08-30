package lht.wangtong.gowin120.patient.bean.event;

/**
 * 登陆事件
 *
 * @author luoyc
 * @date 2017/3/9
 */

public class UserLoginEvent {
    private boolean isLogin;
    private boolean isUpdate;

    public UserLoginEvent(boolean isLogin, boolean isUpdate) {
        this.isLogin = isLogin;
        this.isUpdate = isUpdate;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    @Override
    public String toString() {
        return "UserLoginEvent{" +
                "isLogin=" + isLogin +
                ", isUpdate=" + isUpdate +
                '}';
    }
}
