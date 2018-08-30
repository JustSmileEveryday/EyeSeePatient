package lht.wangtong.gowin120.patient.ui.home.store;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;

/**
 * 小艾商城
 *
 * @author luoyc
 */
public class StorePresenter extends BasePresenter<StoreContact.View> implements StoreContact.Presenter {

    @Inject
    public StorePresenter() {
    }


    @Override
    public void initData() {
        getBanners();
        loadCategorys();
    }

    @Override
    public void getBanners() {

    }

    @Override
    public void loadCategorys() {

    }
}
