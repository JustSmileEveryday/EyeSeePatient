package lht.wangtong.gowin120.patient.ui.mine.report;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author luoyc
 */
public interface ApplyReportContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void showSuccess(boolean isShow);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void saveRadiographScreenReportApply(String name, String sex, String age, String mobilePhone, String agentName, String remark, String memberAddrId);

        boolean checkData(String name, String sex, String age, String mobilePhone, String agentName);

        void queryReportApplyStatus();

    }

}
