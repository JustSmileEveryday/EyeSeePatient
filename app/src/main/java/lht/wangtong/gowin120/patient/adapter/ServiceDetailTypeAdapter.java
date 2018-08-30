package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ServiceDetailType;

/**
 * @author luoyc
 * @date 2018/3/16
 */

public class ServiceDetailTypeAdapter extends BaseQuickAdapter<ServiceDetailType, BaseViewHolder> {

    @Inject
    public ServiceDetailTypeAdapter() {
        super(R.layout.item_service_detail_type_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceDetailType item) {
        helper.setText(R.id.service_detail_name, item.getName());
        if (item.isChoose()){
            helper.setVisible(R.id.oval_img,true);
            helper.setBackgroundRes(R.id.service_detail_layout,R.drawable.service_choose_text_bg);
            helper.setTextColor(R.id.service_detail_name, mContext.getResources().getColor(R.color.service_detail_type_choosed));
        }else {
            helper.setVisible(R.id.oval_img,false);
            helper.setBackgroundRes(R.id.service_detail_layout,R.drawable.service_unchoose_text_bg);
            helper.setTextColor(R.id.service_detail_name, mContext.getResources().getColor(R.color.service_detail_type_unchoosed));
        }
    }
}
