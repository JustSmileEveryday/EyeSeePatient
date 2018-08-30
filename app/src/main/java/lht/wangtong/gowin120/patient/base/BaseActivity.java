package lht.wangtong.gowin120.patient.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.di.component.ActivityComponent;
import lht.wangtong.gowin120.patient.di.component.DaggerActivityComponent;
import lht.wangtong.gowin120.patient.di.module.ActivityModule;
import lht.wangtong.gowin120.patient.util.ActivityCollector;
import lht.wangtong.gowin120.patient.util.netanimation.AnimationUtil;

/**
 *
 * @author lw
 * @date 2018/1/18
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {
    @Nullable
    @Inject
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;
    @Nullable
    protected Toolbar mToolbar;
    private Unbinder unbinder;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

    /**
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        ARouter.getInstance().inject(this);
        int layoutId = getLayoutId();
        if (layoutId != 0){
            setContentView(layoutId);
        }
        initInjector();
        unbinder = ButterKnife.bind(this);
//        initToolBar();
        attachView();
        ActivityCollector.addActivity(this);
        initView();
        if (!NetworkUtils.isConnected()) {
            showNoNet();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
        ActivityCollector.finishActivity(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showSuccess(String successMsg) {
        ToastUtils.showShort(successMsg);
    }

    @Override
    public void showFaild(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void showNoNet() {
        ToastUtils.showShort(R.string.no_network_connection);
    }

    @Override
    public void onRetry() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
                default:
                    break;
        }
        return true;
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    /**
     * 初始化ActivityComponent
     */
    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

//    /**
//     * 初始化toolbar
//     */
//    private void initToolBar() {
////        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (mToolbar == null) {
//            throw new NullPointerException("toolbar can not be null");
//        }
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
//        /**toolbar除掉阴影*/
//        getSupportActionBar().setElevation(0);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mToolbar.setElevation(0);
//        }
//    }

    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        if (AnimationUtil.getProgress() != null) {
            AnimationUtil.getProgress().close();
            AnimationUtil.singleProgress = null;
        }
    }
}
