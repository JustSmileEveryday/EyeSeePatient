package lht.wangtong.gowin120.patient.ui.home.store;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.CatalogInfo;

/**
 * 小艾商城
 *
 * @author luoyc
 */
public interface StoreContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initBanner();

        void setBanners(List<BannerInfo> banners);

        void setCategorys(List<CatalogInfo> categorys);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void getBanners();

        void loadCategorys();
    }

}
