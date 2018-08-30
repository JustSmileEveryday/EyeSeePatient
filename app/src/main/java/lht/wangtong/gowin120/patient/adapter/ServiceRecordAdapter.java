package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ServiceRecordInfo;

/**
 * 服务使用记录adapter
 *
 * @author luoyc
 */
public class ServiceRecordAdapter extends BaseQuickAdapter<ServiceRecordInfo.ServiceRecordUseRecordListBean, BaseViewHolder> {

    @Inject
    public ServiceRecordAdapter() {
        super(R.layout.item_service_record_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceRecordInfo.ServiceRecordUseRecordListBean item) {
        helper.setText(R.id.date, item.getCreatedDate());
        helper.setText(R.id.agent, item.getAgentName());
    }
}
