package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.MineOrderInfo;

/**
 * 我的订单适配器
 *
 * @author luoyc
 */
public class MineOrderAdapter extends BaseQuickAdapter<MineOrderInfo, BaseViewHolder> {

    @Inject
    public MineOrderAdapter() {
        super(R.layout.item_mine_order_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineOrderInfo item) {

    }
}
