package lht.wangtong.gowin120.patient.ui.login;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.Msg;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.LoginUtil;

/**
 * @author Luoyc
 * @date 2018/3/12
 */

public class RegisterPresenter extends BasePresenter<RegisterContact.View> implements RegisterContact.Presenter {
    private Msg msg; //发送验证码

    @Inject
    public RegisterPresenter() {

    }


    @Override
    public void netRegister(final String userName, final String password, String verifycode) {
        if (checkData(userName, password, verifycode)) {
            HttpUtil.getObject(Api.REGISTER.mapClear()
                            .addMap("mobilePhone", userName)
                            .addMap("valCode", verifycode)
                            .addMap("mobileKey", msg.getMobileKey())
                            .addMap("password", password)
                            .addBody(), Msg.class, new HttpUtil.objectCallback() {
                        @Override
                        public void result(boolean b, @Nullable Object obj) {
                            if (b) {
                                //注册成功
                                netLogin(userName, password);
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void getVerifycode(String userName) {
        if (StringUtils.isEmpty(userName)) {
            ToastUtils.showShort("手机号不能为空");
            return;
        }
        if (!RegexUtils.isMobileExact(userName)) {
            ToastUtils.showShort("请输入正确的手机号");
        } else {
            HttpUtil.getObject(Api.GETVALCODE.mapClear()
                    .addMap("mobilePhone", userName)
                    .addBody(), Msg.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        mView.showTime(60);
                        msg = (Msg) obj;
                    }
                }
            });
        }
    }

    @Override
    public void netLogin(final String userName, String password) {
        if (StringUtils.isEmpty(userName)) {
            ToastUtils.showShort("手机号不能为空");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }

        if (LoginUtil.token == null) {
            return; //token没获取不能进行登录
        }
        byte[] b = null;
        try {
            b = password.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtil.getObject(Api.MEMBERLOGIN.mapClear()
                        .addMap("password", EncodeUtils.base64Encode2String(b))
                        .addMap("loginName", userName)
                        .addMap("expiresIn", LoginUtil.token.getExpiresIn())
                        .addMap("accessToken", LoginUtil.token.getAccessToken())
                        .addBody(), User.class,
                new HttpUtil.objectCallback() {
                    @Override
                    public void result(boolean b, @Nullable Object obj) {
                        if (b) {
                            User mUser = (User) obj;
                            LoginUtil.user = (User) obj;
                            App.getInstance().loginTencent();
                            if (TextUtils.isEmpty(mUser.getAgeGroup())) {
                                ARouter.getInstance().build("/mine/info/MineInfoActivity")
                                        .withParcelable("mInfo", mUser)
                                        .withInt("mType", 2).navigation();
                            } else if (TextUtils.equals(mUser.getIsLogined(), "N")) {
                                ARouter.getInstance().build("/mine/coupons/ReceiveCouponActivity")
                                        .withInt("mType", 1)
                                        .navigation();
                                mView.close();
                            } else {
                                JPushInterface.setAlias(mView.getThisActivity(), 40, userName);
                                User oldUser = SQLite.select().from(User.class).querySingle();
                                if (oldUser != null) {
                                    oldUser.delete();
                                }
                                mUser.save();
                                EventBus.getDefault().postSticky(new UserLoginEvent(true, false));
                                ActivityUtils.finishActivity(LoginActivity.class);
                                ARouter.getInstance().build("/ui/MainActivity").navigation();
                                mView.close();
                            }
                        }
                    }
                });
    }

    private boolean checkData(String userName, String password, String verifycode) {
        if (StringUtils.isEmpty(userName)) {
            ToastUtils.showShort("用户名不能为空");
            return false;
        }
        if (!RegexUtils.isMobileExact(userName)) {
            ToastUtils.showShort("请输入正确的手机号");
            return false;
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("密码不能为空");
            return false;
        }
        if (StringUtils.isEmpty(verifycode)) {
            ToastUtils.showShort("验证码不能为空");
            return false;
        }
        if (msg == null) {
            msg = new Msg();
            msg.setMobileKey("");
        }
        return true;
    }
}
