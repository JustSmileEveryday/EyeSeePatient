package lht.wangtong.gowin120.patient.ui.mine.setting.reset;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.Msg;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.StringUtil;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public class ResetPasswordPresenter extends BasePresenter<ResetPasswordContact.View> implements ResetPasswordContact.Presenter {
    private Msg msg;

    @Inject
    public ResetPasswordPresenter() {

    }

    @Override
    public void netResetPassword(String userName, String password, String verifycode) {
        if (checkData(userName, password, verifycode)) {
            HttpUtil.getObject(Api.MGREDITPASSWORD.mapClear()
                            .addMap("mobilePhone", userName)
                            .addMap("valCode", verifycode)
                            .addMap("mobileKey", msg.getMobileKey())
                            .addMap("password", password)
                            .addBody(), Msg.class,
                    new HttpUtil.objectCallback() {
                        @Override
                        public void result(boolean b, @Nullable Object obj) {
                            if (b) {
                                ToastUtils.showShort("重置成功");
                                mView.getThisContext().finish();
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void getVerifycode(String userName) {
        if (!RegexUtils.isMobileExact(userName)) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        HttpUtil.getObject(Api.MGRGETVALCODE.mapClear()
                .addMap("mobilePhone", userName)
                .addMap("loginName", userName)
                .addBody(), Msg.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    msg = (Msg) obj;
                    mView.showTime(60);
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
        if (msg == null) {
            msg = new Msg();
            msg.setMobileKey("");
        }
        return true;
    }

    //检查密码格式
    private boolean checkPassword(String password) {
        if (password != null) {
            if (!password.matches("^[0-9a-z_A-Z]{6,20}$")) {
                ToastUtils.showShort("密码格式:" + "6-20位\"字母\"、\"数字\"或\"_\"");
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //检查输入的验证码格式
    private boolean checkCode(String code) {
        if (code == null) {
            ToastUtils.showShort("验证码为6为数字");
            return false;
        } else {
            if (StringUtil.isName(code)) {
                if (code.length() != 6) {
                    ToastUtils.showShort("验证码为6为数字");
                } else {
                    return true;
                }
            } else {
                ToastUtils.showShort("验证码为6为数字");
                return false;
            }
        }
        return false;
    }
}
