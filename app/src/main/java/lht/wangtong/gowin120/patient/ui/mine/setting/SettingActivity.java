package lht.wangtong.gowin120.patient.ui.mine.setting;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.util.DataCleanManager;
import lht.wangtong.gowin120.patient.view.PromptDialog;

/**
 * 设置界面
 *
 * @author luoyc
 * @date 2018/3/26
 */
@Route(path = "/mine/setting/SettingActivity")
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContact.View {

    @BindView(R.id.cookie)
    TextView mCookies;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        setCookies();
    }

    @Override
    public void setCookies() {
        try {
            mCookies.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public Context getThisContext() {
        return this;
    }


    @OnClick({R.id.about_app_btn, R.id.user_agreement, R.id.feedback, R.id.modify_password, R.id.clear_cookies, R.id.login_out})
    public void onClilk(View view) {
        switch (view.getId()) {
            case R.id.about_app_btn:
                ARouter.getInstance().build("/setting/about/AboutActivity").navigation();
                break;
            case R.id.user_agreement:
                ARouter.getInstance().build("/setting/agreement/AgreementActivity").withInt("type", 1).navigation();
                break;
            case R.id.feedback:
                ARouter.getInstance().build("/setting/feedback/FeedbackActivity").navigation();
                break;
            case R.id.modify_password:
                ARouter.getInstance().build("/setting/reset/ResetPasswordActivity").navigation();
                break;
            case R.id.clear_cookies:
                new PromptDialog(this, new PromptDialog.ICallback() {
                    @Override
                    public void callback() {
                        mPresenter.wipeCache();
                    }
                })
                        .setContent(getString(R.string.wipe_cache))
                        .showCancleBtn(true)
                        .show();
                break;
            case R.id.login_out:
                new PromptDialog(this, new PromptDialog.ICallback() {
                    @Override
                    public void callback() {
                        mPresenter.loginOut();
                    }
                })
                        .setContent(getString(R.string.exit_login))
                        .showCancleBtn(true)
                        .show();
                break;
            default:
                break;
        }
    }
}
