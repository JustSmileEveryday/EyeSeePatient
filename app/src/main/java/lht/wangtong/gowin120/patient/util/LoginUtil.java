package lht.wangtong.gowin120.patient.util;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.config.Constant;
import lht.wangtong.gowin120.patient.db.Token;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.ui.login.LoginActivity;


/**
 * @author luoyc
 * @date 2016/10/28 0028
 */
public class LoginUtil {
    public static Token token = null;
    public static User user = null;

    //刷新token   退出登录  过期
    public static void getTokenNew() {
        String time = String.valueOf(System.currentTimeMillis());
        String md5B = Constant.appId + time.substring(3) + Constant.appSecret;
        HttpUtil.getObject(Api.APPLYTOKEN
                        .addMap("md5", getMD5(md5B))
                        .addMap("time", time).addBody(),
                Token.class, new HttpUtil.objectCallback() {
                    @Override
                    public void result(boolean b, Object obj) {
                        if (b) {
                            token = (Token) obj;
                            Token oldToken = SQLite.select().from(Token.class).querySingle();
                            if (oldToken != null) {
                                oldToken.delete();
                            }
                            token.save();
                        }
                    }
                });
    }

    //获取token
    public static void getTokenDB() {
        try {
            token = SQLite.select().from(Token.class).querySingle();
            Log.e("this", "token == null！" + token.toString());
        } catch (Exception e) {
            Log.e("this", "token 获取失败！");
        }
    }

    public static void getToken() {
        getTokenDB();
        if (token == null) {
            getTokenNew();
        }
    }

    //自动登录
    public static void loginRe() {
        try {
            Token oldToken = SQLite.select().from(Token.class).querySingle();
            if (oldToken != null) {
                oldToken.delete();
            }
            User oldUser = SQLite.select().from(User.class).querySingle();
            if (oldUser != null) {
                oldUser.delete();
            }
            user = null;
            EventBus.getDefault().postSticky(new UserLoginEvent(false, false));
            if (ActivityUtils.getTopActivity() instanceof LoginActivity) {
                return;
            } else {
                ARouter.getInstance().build("/ui/login/LoginActivity").navigation();
            }
            getTokenNew();
        } catch (Exception e) {
            Log.i("this", "DB:" + e.toString());
        }
    }

    //转md5
    public static String getMD5(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            for (byte b : md.digest()) {
                sb.append(String.format("%02x", b)); //10进制转16进制，x表示以十六进制小写字母形式输出，02表示不足两位前面补0输出
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
