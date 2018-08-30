package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.db.HistoryInfo;

/**
 * 搜索历史adapter
 *
 * @author luoyc
 */
public class SearchHistoryAdapter extends BaseQuickAdapter<HistoryInfo, BaseViewHolder> {

    @Inject
    public SearchHistoryAdapter() {
        super(R.layout.item_search_history_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryInfo item) {
        helper.setText(R.id.history_name, item.getHistory());
        helper.addOnClickListener(R.id.history_clear_btn);
    }
}
