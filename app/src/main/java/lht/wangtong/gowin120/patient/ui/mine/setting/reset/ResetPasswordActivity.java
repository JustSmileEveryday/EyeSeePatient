package lht.wangtong.gowin120.patient.ui.mine.setting.reset;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;

/**
 * 重置密码
 *
 * @author luoyc
 * @date 2018/3/26
 */
@Route(path = "/setting/reset/ResetPasswordActivity")
public class ResetPasswordActivity extends BaseActivity<ResetPasswordPresenter> implements ResetPasswordContact.View {
    private final int StartSendCode = 1; //开始发送验证码
    private final int EndSendCode = 2; //结束发送
    @BindView(R.id.user_phone)
    EditText mUserName;
    @BindView(R.id.new_password)
    EditText mPassword;
    @BindView(R.id.verify_code)
    EditText mCode;
    @BindView(R.id.sightless_img)
    ImageView mSightless;
    @BindView(R.id.send_verify_code)
    TextView mSendCode;
    private boolean isVisiable = false; //当前密码是否为可见的
    private Runnable mRunnable;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case StartSendCode:
                    mSendCode.setTextColor(getResources().getColor(R.color.gray_text));
                    mSendCode.setClickable(false);
                    mSendCode.setText("还剩 " + bundle.getInt("time") + " s");
                    break;
                case EndSendCode:
                    mSendCode.setClickable(true);
                    mSendCode.setText(getResources().getText(R.string.send_verify_code));
                    mSendCode.setTextColor(getResources().getColor(R.color.green));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.reset_password)
    @Override
    public void reset() {
        mPresenter.netResetPassword(mUserName.getText().toString(), mPassword.getText().toString(), mCode.getText().toString());
    }

    @OnClick(R.id.sightless_img)
    @Override
    public void sightless() {
        //不可见图标
        if (isVisiable) {
            isVisiable = false;
            mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mSightless.setImageDrawable(getResources().getDrawable(R.drawable.visible_img));
            setEidtSelection();
        } else {
            isVisiable = true;
            mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mSightless.setImageDrawable(getResources().getDrawable(R.drawable.sightless));
            setEidtSelection();
        }
    }

    @Override
    public void setEidtSelection() {
        CharSequence text = mPassword.getText();
        if (text != null) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    @OnClick(R.id.send_verify_code)
    @Override
    public void sendVerifycode() {
        mSendCode.setClickable(false);
        mPresenter.getVerifycode(mUserName.getText().toString());
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
    public Activity getThisContext() {
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
