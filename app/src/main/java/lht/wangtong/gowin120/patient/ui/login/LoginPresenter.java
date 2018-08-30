package lht.wangtong.gowin120.patient.ui.login;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.LoginUtil;

/**
 * 登录
 * Created by luoyc on 2018/3/12.
 */

public class LoginPresenter extends BasePresenter<LoginContact.View> implements LoginContact.Presenter {

    @Inject
    public LoginPresenter() {
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
                            LoginUtil.user = (User) obj;
                            App.getInstance().loginTencent();
                            User mUser = (User) obj;
                            if (TextUtils.isEmpty(mUser.getAgeGroup())) {
                                ARouter.getInstance().build("/mine/info/MineInfoActivity")
                                        .withParcelable("mInfo", mUser)
                                        .withInt("mType", 2).navigation();
                            } else if (TextUtils.equals(mUser.getIsLogined(), "N")) {
                                JPushInterface.setAlias(mView.getThisActivity(), 40, userName);
                                User oldUser = SQLite.select().from(User.class).querySingle();
                                if (oldUser != null) {
                                    oldUser.delete();
                                }
                                mUser.save();
                                EventBus.getDefault().postSticky(new UserLoginEvent(true, false));
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
                                ARouter.getInstance().build("/ui/MainActivity").navigation();
                                mView.close();
                            }
                        }
                    }
                });
    }
}
