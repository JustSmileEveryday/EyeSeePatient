package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.AppointmentInfo;

/**
 * 预约适配器
 *
 * @author luoyc
 * @date 2018/3/14
 */

public class AppointmentAdapter extends BaseQuickAdapter<AppointmentInfo, BaseViewHolder> {

    @Inject
    public AppointmentAdapter() {
        super(R.layout.appointment_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppointmentInfo item) {
        helper.setText(R.id.appointment_type, item.getServiceTypeName());
        helper.setText(R.id.appointment_date, item.getReservationServiceDate());
        switch (item.getStatus()) {
            case 1:
                //待门店确认
                helper.setText(R.id.appointment_state,"待门店确认");
                helper.setBackgroundRes(R.id.state,R.drawable.appointment_state_1);
                helper.setBackgroundRes(R.id.appointment_state,R.drawable.appointment_state_bg_1);
                break;
            case 2:
                //预约成功
                helper.setText(R.id.appointment_state,"预约成功");
                helper.setBackgroundRes(R.id.state,R.drawable.appointment_state_2);
                helper.setBackgroundRes(R.id.appointment_state,R.drawable.appointment_state_bg_2);
                break;
//            case 3:
//                //已到店
//                helper.setText(R.id.appointment_state,"已到店");
//                helper.setBackgroundRes(R.id.state,R.drawable.appointment_state_3);
//                helper.setBackgroundRes(R.id.appointment_state,R.drawable.appointment_state_bg_3);
//                break;
            case 3:
                //已到店
                helper.setText(R.id.appointment_state,"已到店");
                helper.setBackgroundRes(R.id.state,R.drawable.appointment_state_4);
                helper.setBackgroundRes(R.id.appointment_state,R.drawable.appointment_state_bg_4);
                break;
            default:
                break;
        }
    }
}
