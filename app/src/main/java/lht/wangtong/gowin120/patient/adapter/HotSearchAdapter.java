package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.CommentLabelInfo;


/**
 * 热门搜索
 *
 * @author luoyc
 */
public class HotSearchAdapter extends BaseQuickAdapter<CommentLabelInfo, BaseViewHolder> {

    @Inject
    public HotSearchAdapter() {
        super(R.layout.item_hot_search_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentLabelInfo item) {
        helper.setText(R.id.content, item.getKeyword());
    }
}
