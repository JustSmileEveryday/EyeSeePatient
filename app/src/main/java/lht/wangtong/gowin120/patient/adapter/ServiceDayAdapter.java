package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ServiceDayInfo;

/**
 * @author Luoyc
 * @date 2018/3/16
 */

public class ServiceDayAdapter extends BaseQuickAdapter<ServiceDayInfo, BaseViewHolder> {

    @Inject
    public ServiceDayAdapter() {
        super(R.layout.item_service_day_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceDayInfo item) {
        helper.setText(R.id.week, item.getWeekInfo());
        helper.setText(R.id.day, item.getDayInfo());
        if (item.isChoose()) {
            helper.setTextColor(R.id.week, mContext.getResources().getColor(R.color.service_day_choosed));
            helper.setTextColor(R.id.day, mContext.getResources().getColor(R.color.service_day_choosed));
            helper.setBackgroundRes(R.id.service_time_layout, R.drawable.service_day_choosed_bg);
        } else {
            helper.setTextColor(R.id.week, mContext.getResources().getColor(R.color.service_day_unchoosed));
            helper.setTextColor(R.id.day, mContext.getResources().getColor(R.color.service_day_unchoosed));
            helper.setBackgroundRes(R.id.service_time_layout, R.drawable.service_day_unchoose_bg);
        }
    }
}
