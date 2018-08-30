package lht.wangtong.gowin120.patient.ui.home.plan;

import java.util.Date;
import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.PlanScheduleDetailInfo;
import lht.wangtong.gowin120.patient.bean.WeekInfo;

/**
 * @author luoyc
 * @date 2018/3/22
 */

public interface AddPlanContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setWeekList(List<WeekInfo> weekInfos);

        void showTimePicker();

        void getTime(Date date);

        void finishActivity();

        void setPlanInfo(List<WeekInfo> weekInfos, PlanScheduleDetailInfo detailInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData(String mPlanScheduleId);

        void loadWeekList(String mPlanScheduleId);

        void updatePlanSchedule(String planScheduleId, String familyId, String name, String remindDate, String[] remindValue);

        boolean checkData(String name, String remindDate, String[] remindValue);

        void loadPlanSchedule(String planScheduleId,List<WeekInfo> infos);

    }

}
