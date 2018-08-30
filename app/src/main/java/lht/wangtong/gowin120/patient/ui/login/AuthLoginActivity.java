package lht.wangtong.gowin120.patient.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;

/**
 * 验证码登录
 *
 * @author luoyc
 */
@Route(path = "/ui/login/AuthLoginActivity")
public class AuthLoginActivity extends BaseActivity<AuthLoginPresenter> implements AuthLoginContact.View {

    //开始发送验证码
    private final int StartSendCode = 1;
    //结束发送
    private final int EndSendCode = 2;
    @BindView(R.id.user_phone)
    EditText userPhone;
    @BindView(R.id.phone_verify_code)
    EditText phoneVerifyCode;
    @BindView(R.id.send_verify_code)
    TextView sendVerifyCode;
    private Runnable mRunnable;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case StartSendCode:
                    sendVerifyCode.setTextColor(getResources().getColor(R.color.gray_text));
                    sendVerifyCode.setClickable(false);
                    sendVerifyCode.setText("还剩 " + bundle.getInt("time") + " s");
                    break;
                case EndSendCode:
                    sendVerifyCode.setClickable(true);
                    sendVerifyCode.setText(getResources().getText(R.string.send_verify_code));
                    sendVerifyCode.setTextColor(getResources().getColor(R.color.green));
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_auth_login;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.send_verify_code, R.id.password_login, R.id.login_btn, R.id.register, R.id.register_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_verify_code:
                sendVerifycode();
                break;
            case R.id.password_login:
                ARouter.getInstance().build("/ui/login/LoginActivity").navigation();
                finish();
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.register_agreement:
                ARouter.getInstance().build("/setting/agreement/AgreementActivity").withInt("type", 1).navigation();
                break;
            case R.id.register:
                ARouter.getInstance().build("/ui/login/RegisterActivity").navigation();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void sendVerifycode() {
        mPresenter.getVerifycode(userPhone.getText().toString().trim());
    }

    @Override
    public void showTime(final int time) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (time > 0) {
                    int tmp = time - 1;
                    Message msg = new Message();
                    msg.what = StartSendCode;
                    Bundle bundle = new Bundle();
                    bundle.putInt("time", tmp);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                    showTime(tmp);
                } else {
                    mHandler.sendEmptyMessage(EndSendCode);
                }
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void login() {
        KeyboardUtils.hideSoftInput(this);
        mPresenter.netAuthLogin(userPhone.getText().toString().trim(), phoneVerifyCode.getText().toString().trim());
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public Context getThisActivity() {
        return this;
    }

    @Override
    public void finish() {
        super.finish();
        mHandler.removeCallbacks(mRunnable);
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
