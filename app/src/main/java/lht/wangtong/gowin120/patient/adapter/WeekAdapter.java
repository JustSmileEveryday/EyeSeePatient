package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.WeekInfo;

/**
 * 星期适配器
 *
 * @author luoyc
 * @date 2018/3/22
 */

public class WeekAdapter extends BaseQuickAdapter<WeekInfo, BaseViewHolder> {

    @Inject
    public WeekAdapter() {
        super(R.layout.item_week_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeekInfo item) {
        helper.setText(R.id.week, item.getWeekName());
        if (item.isChoosed()) {
            helper.setVisible(R.id.choose_img, true);
        } else {
            helper.setGone(R.id.choose_img, false);
        }
    }
}
