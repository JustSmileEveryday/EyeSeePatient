package lht.wangtong.gowin120.patient.ui.home.detection;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;

/**
 * 检测页面
 *
 * @author luoyc
 * @date 2018/3/19
 */
@Route(path = "/home/detection/EyeDetectionActivity")
public class EyeDetectionActivity extends BaseActivity<EyeDetectionPresenter> implements EyeDetectionContact.View {
    @BindView(R.id.service_tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @Autowired
    int mCurrentItem;
    @Autowired
    String mfamilyId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eye_detection;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.initData(mfamilyId);
        mViewPager.setCurrentItem(mCurrentItem);
    }

    @Override
    public void initAdapter(String[] titles, ArrayList<Fragment> fragments) {
        mTabLayout.setViewPager(mViewPager, titles, this, fragments);
    }
}
