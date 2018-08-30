package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ServiceTimeInfo;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public class ServiceTimeAdapter extends BaseQuickAdapter<ServiceTimeInfo, BaseViewHolder> {

    @Inject
    public ServiceTimeAdapter() {
        super(R.layout.item_service_time_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceTimeInfo item) {
        helper.setText(R.id.service_time_name, item.getTime());
        if (item.isChoosed()) {
            helper.setVisible(R.id.oval_img, true);
            helper.setBackgroundRes(R.id.service_time_layout, R.drawable.service_choose_text_bg);
            helper.setTextColor(R.id.service_time_name, mContext.getResources().getColor(R.color.service_detail_type_choosed));
        } else {
            helper.setVisible(R.id.oval_img, false);
            helper.setBackgroundRes(R.id.service_time_layout, R.drawable.service_unchoose_text_bg);
            helper.setTextColor(R.id.service_time_name, mContext.getResources().getColor(R.color.service_detail_type_unchoosed));
        }
    }
}
