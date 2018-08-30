package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ReportInfo;

/**
 * 搜索报告
 *
 * @author luoyc
 */
public class SearchReportAdapter extends BaseQuickAdapter<ReportInfo, BaseViewHolder> {

    @Inject
    public SearchReportAdapter() {
        super(R.layout.item_search_report_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportInfo item) {
        helper.setText(R.id.report_name, item.getMemberName() + "眼健康综合报告");
        helper.setText(R.id.create_date, item.getCheckInDate());
        helper.setText(R.id.report_address, item.getAgentName());
    }
}
