package lht.wangtong.gowin120.patient.ui.login;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;

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
 * @author luoyc
 */
public class AuthLoginPresenter extends BasePresenter<AuthLoginContact.View> implements AuthLoginContact.Presenter {

    private Msg msg; //发送验证码

    @Inject
    public AuthLoginPresenter() {
    }

    @Override
    public void getVerifycode(String phone) {
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.showShort("手机号不能为空");
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort("请输入正确的手机号");
        } else {
            HttpUtil.getObject(Api.verifiValCode.mapClear()
                    .addMap("mobilePhone", phone)
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
    public void netAuthLogin(final String phone, String code) {
        if (checkData(phone, code)) {
            HttpUtil.getObject(Api.checkVlCode.mapClear()
                            .addMap("mobilePhone", phone)
                            .addMap("valCode", code)
                            .addMap("expiresIn", LoginUtil.token.getExpiresIn())
                            .addMap("accessToken", LoginUtil.token.getAccessToken())
                            .addMap("mobileKey", msg.getMobileKey())
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
                                    JPushInterface.setAlias(mView.getThisActivity(), 40, phone);
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
    }

    private boolean checkData(final String phone, String code) {
        if (StringUtils.isEmpty(phone)) {
            RxToast.error("用户名不能为空");
            return false;
        }
        if (StringUtils.isEmpty(code)) {
            RxToast.error("请输入验证码");
            return false;
        }
        if (LoginUtil.token == null) {
            //token没获取不能进行登录
            return false;
        }
        if (msg == null) {
            RxToast.error("请输入正确验证码");
            return false;
        }
        return true;
    }
}
