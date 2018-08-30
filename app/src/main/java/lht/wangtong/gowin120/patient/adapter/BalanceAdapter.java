package lht.wangtong.gowin120.patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.BalanceInfo;


/**
 * 余额适配器
 * Created by luoyc on 2017/7/10.
 */

public class BalanceAdapter extends BaseQuickAdapter<BalanceInfo, BaseViewHolder> {

    @Inject
    public BalanceAdapter() {
        super(R.layout.item_balance_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalanceInfo item) {
        helper.setText(R.id.balance_num, "余额：" + item.getCashUsableNew());
        helper.setText(R.id.date, item.getCreatedDate());
        if (item.getCash() < 0) {
            helper.setText(R.id.cash, "-￥" + Math.abs(item.getCash()));
            helper.setTextColor(R.id.cash, mContext.getResources().getColor(R.color.black_text));
        } else {
            helper.setText(R.id.cash, "+￥" + Math.abs(item.getCash()));
            helper.setTextColor(R.id.cash, mContext.getResources().getColor(R.color.green));
        }
    }
}
