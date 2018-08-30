package lht.wangtong.gowin120.patient.ui.science;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.CatalogInfo;

/**
 * @author luoyc
 * @date 2018/3/5
 */

public interface ScienceContract {

    interface View extends BaseContract.BaseView {

        void initBanner();

        void setScienceBanner(List<BannerInfo> bannerInfos, List<String> tips);

        void setCategorys(List<CatalogInfo> categorys);

        void searchArticle();

        void showGuide();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadScienceBanner();

        void loadCategorys();
    }

}
