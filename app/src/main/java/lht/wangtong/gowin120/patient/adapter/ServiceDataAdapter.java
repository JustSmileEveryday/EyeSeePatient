package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ServiceDataInfo;

/**
 * 预约服务数据 adapter
 * @author luoyc
 * @date 2018/3/23
 */

public class ServiceDataAdapter extends BaseQuickAdapter<ServiceDataInfo,BaseViewHolder>{

    @Inject
    public ServiceDataAdapter() {
        super(R.layout.item_service_data_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceDataInfo item) {
        helper.setText(R.id.service_date,item.getReservationServiceDate());
        helper.setText(R.id.service_agent,item.getAgentName());
        helper.setText(R.id.service_day,item.getDay());
    }
}
