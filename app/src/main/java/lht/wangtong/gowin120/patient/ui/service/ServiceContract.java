package lht.wangtong.gowin120.patient.ui.service;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;

/**
 * Created by luoyc on 2018/3/5.
 */

public interface ServiceContract {

    interface View extends BaseContract.BaseView {

        void initBanner();

        void setServiceBanner(List<BannerInfo> bannerInfos);

        void setCategorys(List<CommonCdInfo> categorys);

        void searchService();

        void showGuide();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadServiceBanner();

        void loadCategorys();
    }
}
