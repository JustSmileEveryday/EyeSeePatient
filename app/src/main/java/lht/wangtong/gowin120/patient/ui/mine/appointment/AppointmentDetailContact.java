package lht.wangtong.gowin120.patient.ui.mine.appointment;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.AppointmentDetailInfo;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public interface AppointmentDetailContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setAppointmentDetail(AppointmentDetailInfo detailInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getAppointmentDetail(String reservationServiceId);

    }

}
