package lht.wangtong.gowin120.patient.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.IntegrationInfo;

/**
 * 积分列表适配器
 *
 * @author luoyc
 */
public class MyIntegralAdapter extends BaseQuickAdapter<IntegrationInfo, BaseViewHolder> {

    @Inject
    public MyIntegralAdapter() {
        super(R.layout.item_my_integral_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegrationInfo item) {
        helper.setText(R.id.create_date, item.getCreatedDate());
        if (item.getBonus() > 0) {
            helper.setText(R.id.type, item.getRemark());
            helper.setText(R.id.num, "+" + item.getBonus());
            helper.setTextColor(R.id.num, Color.parseColor("#00CABB"));
        } else {
            helper.setText(R.id.type,  item.getRemark());
            helper.setText(R.id.num, "-" + item.getBonus());
            helper.setTextColor(R.id.num, Color.parseColor("#333333"));
        }
    }
}
