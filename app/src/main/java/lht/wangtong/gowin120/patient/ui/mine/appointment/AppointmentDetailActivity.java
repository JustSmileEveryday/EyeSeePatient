package lht.wangtong.gowin120.patient.ui.mine.appointment;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AppointmentDetailInfo;

/**
 * 预约详情
 *
 * @author Administrator
 * @date 2018/3/14
 */
@Route(path = "/mine/appointment/AppointmentDetailActivity")
public class AppointmentDetailActivity extends BaseActivity<AppointmentDetailPresenter> implements AppointmentDetailContact.View {
    @BindView(R.id.appointment_state)
    TextView mState;
    @BindView(R.id.appointment_name)
    TextView mName;
    @BindView(R.id.appointment_date)
    TextView mDate;
    @BindView(R.id.appointment_agent)
    TextView mAgent;
    @BindView(R.id.appointment_people)
    TextView mPeople;
    @Autowired
    String mReservationServiceId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.getAppointmentDetail(mReservationServiceId);
    }

    @Override
    public void setAppointmentDetail(AppointmentDetailInfo detailInfo) {
        mName.setText(detailInfo.getServiceTypeName());
        mDate.setText(detailInfo.getReservationServiceDate());
        mAgent.setText(detailInfo.getAgentName());
        mPeople.setText(detailInfo.getFamilyName());
        switch (detailInfo.getStatus()) {
            case 1:
                //门店未确认
                mState.setText("门店未确认");
                mState.setTextColor(getResources().getColor(R.color.appointment_state_1));
                break;
            case 2:
                //预约成功
                mState.setText("预约成功");
                mState.setTextColor(getResources().getColor(R.color.appointment_state_2));
                break;
//            case 3:
//                //预约失败
//                mState.setText("预约失败");
//                mState.setTextColor(getResources().getColor(R.color.appointment_state_3));
//                break;
            case 3:
                //已到店
                mState.setText("已到店");
                mState.setTextColor(getResources().getColor(R.color.appointment_state_4));
                break;
            default:

                break;
        }
    }
}
