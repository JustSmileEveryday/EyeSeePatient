package lht.wangtong.gowin120.patient.ui.home.plan;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.PlanScheduleInfo;

/**
 * @author luoyc
 * @date 2018/3/21
 */

public interface ProtectEyePlanContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setPlanList(List<PlanScheduleInfo> scheduleInfos);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadPlanList(String familyId);

        void changePlanScheduleStatus(String planScheduleId);

    }

}
