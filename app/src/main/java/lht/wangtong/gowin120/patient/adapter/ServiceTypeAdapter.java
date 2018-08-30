package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;

/**
 * @author luoyc
 * @date 2018/3/15
 */

public class ServiceTypeAdapter extends BaseQuickAdapter<CommonCdInfo, BaseViewHolder> {

    @Inject
    public ServiceTypeAdapter() {
        super(R.layout.item_service_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonCdInfo item) {
        helper.setText(R.id.service_name,item.getNameZh());
        if (item.isChoosed()){
            helper.setTextColor(R.id.service_name,mContext.getResources().getColor(R.color.service_type_choosed));
        }else {
            helper.setTextColor(R.id.service_name,mContext.getResources().getColor(R.color.service_type_unchoosed));
        }
    }
}
