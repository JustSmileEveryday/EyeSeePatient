package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.RechargeNumInfo;

/**
 * @author luoyc
 * @date 2017/7/6
 */

public class RechargeNumAdapter extends BaseQuickAdapter<RechargeNumInfo, BaseViewHolder> {

    @Inject
    public RechargeNumAdapter() {
        super(R.layout.item_recharge_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeNumInfo item) {
        helper.setText(R.id.money, item.getMoney() + "元");
        helper.setText(R.id.integral, item.getIntegral() + "积分");
        if (item.isChoose())
            helper.setBackgroundRes(R.id.recharge_item, R.drawable.recharge_num_bg_clicked);
        else
            helper.setBackgroundRes(R.id.recharge_item, R.drawable.recharge_num_bg_unclicked);
    }

}
