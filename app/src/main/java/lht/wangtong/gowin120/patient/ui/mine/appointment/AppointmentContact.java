package lht.wangtong.gowin120.patient.ui.mine.appointment;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.AppointmentInfo;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;

/**
 * @author Luoyc
 * @date 2018/3/14
 */

public interface AppointmentContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setHomeFamilys(List<HomeFamilyInfo> familyInfos);

        void setAppointmentLits(List<AppointmentInfo> infos, int total);

        void setState(boolean isShow);

    }

    interface Presenter extends BasePresenter<View> {

        void initData();

        void loadHomeFamilys();

        void loadAppointmentLits(String familyId, boolean isClear);

        void loadMore(String familyId);

        void onRefresh(String familyId);

    }

}
