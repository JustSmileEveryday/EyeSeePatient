package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.GoodsInfo;

/**
 * @author luoyc
 */
public class GoodsAdapter extends BaseQuickAdapter<GoodsInfo, BaseViewHolder> {

    @Inject
    public GoodsAdapter() {
        super(R.layout.item_goods_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfo item) {

    }
}
