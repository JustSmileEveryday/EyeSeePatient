package lht.wangtong.gowin120.patient.ui.consult;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.event.ConsultEvent;

/**
 * 咨询fragment
 *
 * @author Luoyc
 * @date 2018/3/5
 */

public class ConsultFragment extends BaseFragment<ConsultPresenter> implements ConsultContract.View {
    @BindView(R.id.category_tab)
    SlidingTabLayout categoryTab;
    @BindView(R.id.consult_pager)
    ViewPager consultPager;

    public static ConsultFragment newInstance() {
        return new ConsultFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_consult;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);
        mPresenter.loadTitles();
    }


    @Override
    public void setTitles(List<String> titleList) {
        String[] titles = new String[titleList.size()];
        for (int i = 0; i < titleList.size(); i++) {
            titles[i] = titleList.get(i);
        }
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(NewConsultFragment.newInstance());
        fragments.add(HistoryConsultFragment.newInstance());
        categoryTab.setViewPager(consultPager, titles, getActivity(), fragments);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConsultEvent(ConsultEvent event) {
        if (event.isSkip()) {
            consultPager.setCurrentItem(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
