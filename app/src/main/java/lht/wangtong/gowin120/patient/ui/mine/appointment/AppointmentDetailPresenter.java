package lht.wangtong.gowin120.patient.ui.mine.appointment;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AppointmentDetailInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public class AppointmentDetailPresenter extends BasePresenter<AppointmentDetailContact.View> implements AppointmentDetailContact.Presenter {

    @Inject
    public AppointmentDetailPresenter() {

    }


    @Override
    public void getAppointmentDetail(String reservationServiceId) {
        HttpUtil.getObject(Api.viewReservationService.mapClear()
                .addMap("reservationServiceId",reservationServiceId )
                .addBody(), AppointmentDetailInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    AppointmentDetailInfo detailInfo = (AppointmentDetailInfo) obj;
                    mView.setAppointmentDetail(detailInfo);
                }
            }
        });
    }
}
