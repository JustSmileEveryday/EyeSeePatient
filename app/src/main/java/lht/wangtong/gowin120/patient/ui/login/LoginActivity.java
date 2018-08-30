package lht.wangtong.gowin120.patient.ui.login;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.util.ActivityCollector;
import lht.wangtong.gowin120.patient.util.AnimationTool;
import lht.wangtong.gowin120.patient.util.LoginUtil;

/**
 * 登录界面
 *
 * @author luoyc
 * @date 2018/3/26
 */
@Route(path = "/ui/login/LoginActivity")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContact.View {
    @BindView(R.id.logo)
    ImageView mLogo;
    @BindView(R.id.user_phone)
    EditText mUserName;
    @BindView(R.id.user_password)
    EditText mPassword;
    @BindView(R.id.login_btn)
    TextView mLoginBtn;
    @BindView(R.id.sightless_img)
    ImageView mSightless;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.register_agreement)
    LinearLayout mAgreement;
    private boolean isVisiable = false; //当前密码是否为可见的
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private long mBackPressed;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
//        initEvent();
    }

    @Override
    public void onBackPressed() {
        int mTIMEINTERVAL = 2000;
        if (mBackPressed + mTIMEINTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            ActivityCollector.AppExit(this);
            return;
        } else {
            Toast.makeText(getBaseContext(), "再次点击返回键退出", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    private void initEvent() {
        /**
         * 禁止键盘弹起的时候可以滚动
         */
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    int dist = mContent.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        AnimationTool.zoomIn(mLogo, 0.6f, dist);
                    }
                    mAgreement.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    if ((mContent.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        AnimationTool.zoomOut(mLogo, 0.6f);
                    }
                    mAgreement.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    @OnClick(R.id.login_btn)
    @Override
    public void login() {
        KeyboardUtils.hideSoftInput(this);
        mPresenter.netLogin(mUserName.getText().toString(), mPassword.getText().toString());
    }

    @OnClick(R.id.forget_password)
    @Override
    public void forgetPassword() {
        ARouter.getInstance().build("/setting/reset/ResetPasswordActivity").navigation();
    }

    @OnClick(R.id.auth_login)
    @Override
    public void authLogin() {
        ARouter.getInstance().build("/ui/login/AuthLoginActivity").navigation();
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

    @OnClick(R.id.register)
    @Override
    public void register() {
        ARouter.getInstance().build("/ui/login/RegisterActivity").navigation();
    }



    @Override
    public void close() {
        finish();
    }

    @OnClick(R.id.register_agreement)
    @Override
    public void agreement() {
        ARouter.getInstance().build("/setting/agreement/AgreementActivity").withInt("type", 1).navigation();
    }

    @Override
    public Activity getThisActivity() {
        return this;
    }
}
