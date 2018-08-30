package lht.wangtong.gowin120.patient.ui.mine.report;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public interface ReportDetailContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setReportDetailInfo(ReportInfo reportDetailInfo);

        void setReportImgs(List<String> reportImgs);

        void setRecommendService(List<ServiceCategoryInfo> categoryInfos);

        void setLeftAndRightCheck(ReportInfo.LeftAndRightCheckBean leftAndRightCheckBean);

        void setNoLeftAndRightCheck(ReportInfo.NoLeftAndRightCheckBean noLeftAndRightCheckBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getReportDetailInfo(String radiographScreenId);

        void getRadiographScreenMarketActivityList(String familyId);

    }
}
