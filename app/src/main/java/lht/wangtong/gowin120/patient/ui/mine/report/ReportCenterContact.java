package lht.wangtong.gowin120.patient.ui.mine.report;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;

/**
 * @author luoyc
 * @date 2018/3/13
 */

public interface ReportCenterContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setHomeFamilys(List<HomeFamilyInfo> familyInfos);

        void setReports(List<ReportInfo> reportInfos, int total);

        void setReportLayout(boolean isShow);

        void setRecommendService(List<ServiceCategoryInfo> categoryInfos);

        void addItemOnClick();

        void setGroupQrcodeImgUrl(String imgUrl);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void initData();

        void loadHomeFamilys();

        void loadReports(String familyId);

        void getRadiographScreenMarketActivityList(String familyId);

        void onRefresh(String familyId);

        void getSystemParamBykey(String systemParamKey);
    }

}
