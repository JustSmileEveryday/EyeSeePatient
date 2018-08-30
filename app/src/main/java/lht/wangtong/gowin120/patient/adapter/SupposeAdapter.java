package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.IllnessQuestionInfoId;

public class SupposeAdapter extends BaseQuickAdapter<IllnessQuestionInfoId, BaseViewHolder> {

    @Inject
    public SupposeAdapter() {
        super(R.layout.item_suppose_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, IllnessQuestionInfoId item) {
        helper.setText(R.id.question, item.getQuestion());
    }
}
