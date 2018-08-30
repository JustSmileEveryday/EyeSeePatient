package lht.wangtong.gowin120.patient.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.huantansheng.easyphotos.EasyPhotos;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.event.ConsultEvent;
import lht.wangtong.gowin120.patient.bean.event.MessageEvent;
import lht.wangtong.gowin120.patient.bean.event.RecommendServiceEvent;
import lht.wangtong.gowin120.patient.ui.consult.ConsultFragment;
import lht.wangtong.gowin120.patient.ui.home.HomeFragment;
import lht.wangtong.gowin120.patient.ui.mine.MineFragment;
import lht.wangtong.gowin120.patient.ui.science.ScienceFragment;
import lht.wangtong.gowin120.patient.ui.service.ServiceFragment;
import lht.wangtong.gowin120.patient.updateapp.UpdateAppUtil;
import lht.wangtong.gowin120.patient.util.ActivityCollector;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * 主页
 *
 * @author luoyc
 * @date 2018/3/5
 */
@Route(path = "/ui/MainActivity")
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.navigation)
    BottomNavigationViewEx mNavigation;
    private List<BaseFragment> mFragments;
    private int mLastFgIndex;
    private Context mContext;
    private long mBackPressed;
    private QBadgeView mQBadgeView;

    public int getmLastFgIndex() {
        return mLastFgIndex;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

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

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mNavigation.setTextVisibility(true);
        mNavigation.setTextSize(13);
//        mNavigation.setIconSize(39,28);
        mNavigation.enableAnimation(false);
        mNavigation.enableShiftingMode(false);
        mNavigation.enableItemShiftingMode(false);
        mNavigation.setOnNavigationItemSelectedListener(this);
        initFragment();
        switchFragment(2);
        switchFragment(1);
        switchFragment(0);
        UpdateAppUtil.updateApp(mContext);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNewMessage(MessageEvent event) {
        if (event.isNewMessage()) {
            addBadgeAt(event.getNum());
        } else {
            addBadgeAt(event.getNum());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onServiceEvent(RecommendServiceEvent event) {
        if (event.isService()) {
            mNavigation.setCurrentItem(2);
            switchFragment(2);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConsultEvent(ConsultEvent event) {
        if (event.isSkip()) {
            switchFragment(1);
            mNavigation.setCurrentItem(1);
        }
    }

    private void addBadgeAt(int num) {
        if (mQBadgeView == null) {
            mQBadgeView = new QBadgeView(this);
            mQBadgeView.setGravityOffset(12, 2, true);
            mQBadgeView.bindTarget(mNavigation.getBottomNavigationItemView(1));
            mQBadgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {

                    }
                }
            });
        }
        mQBadgeView.setBadgeNumber(num);
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(ConsultFragment.newInstance());
        mFragments.add(ServiceFragment.newInstance());
        mFragments.add(ScienceFragment.newInstance());
        mFragments.add(MineFragment.newInstance());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                switchFragment(0);
                break;
            case R.id.consulting:
                addBadgeAt(0);
                switchFragment(1);
                break;
            case R.id.service:
                switchFragment(2);
                break;
            case R.id.science:
                switchFragment(3);
                break;
            case R.id.mine:
                switchFragment(4);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    private void switchFragment(int position) {
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded()) {
            ft.add(R.id.layout_fragment, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == HomeFragment.GET_PHOTO) {
            if (data != null) {
                //返回图片地址集合：如果你只需要获取图片的地址，可以用这个
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                if (resultPaths.size() > 0) {
                    Log.e("luoyc", " path + " + resultPaths.get(0));
                    ARouter.getInstance()
                            .build("/home/tryglasses/TryGlassesActivity")
                            .withString("photoUrl", resultPaths.get(0))
                            .navigation();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mContext = null;
    }
}
