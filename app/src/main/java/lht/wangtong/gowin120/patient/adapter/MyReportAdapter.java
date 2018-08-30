package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ReportInfo;

/**
 * 报告适配器
 *
 * @author luoyc
 * @date 2018/3/13
 */

public class MyReportAdapter extends BaseQuickAdapter<ReportInfo, BaseViewHolder> {

    @Inject
    public MyReportAdapter() {
        super(R.layout.report_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportInfo item) {
        helper.setText(R.id.date, item.getMemberName() + "   " + item.getCheckInDate());
        helper.setText(R.id.agent, item.getAgentName());
        if (item.getIsRead() == 0) {
            helper.setGone(R.id.is_read, true);
        } else if (item.getIsRead() == 1) {
            helper.setGone(R.id.is_read, false);
        }
    }
}
