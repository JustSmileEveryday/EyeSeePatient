package lht.wangtong.gowin120.patient.ui.home.store;

import android.view.View;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseFragment;

/**
 *
 * @author luoyc
 */
public class StoreListFragment extends BaseFragment<StoreListPresenter> implements StoreListContact.View{



    public static StoreListFragment newInstance() {
        return new StoreListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store_list;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView(View view) {

    }
}
