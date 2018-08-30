package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.EyePreventionInfo;

/**
 * @author luoyc
 * @date 2018/3/20
 */

public class EyePreventionAdapter extends BaseQuickAdapter<EyePreventionInfo, BaseViewHolder> {

    @Inject
    public EyePreventionAdapter() {
        super(R.layout.item_eye_prevention_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, EyePreventionInfo item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.content, item.getContent());
        helper.setBackgroundRes(R.id.eye_img_1, item.getDrawableId1());
        if (item.getDrawableId2() != -1) {
            helper.setVisible(R.id.eye_img_2,true);
            helper.setBackgroundRes(R.id.eye_img_2, item.getDrawableId2());
        }else {
            helper.setGone(R.id.eye_img_2,true);
        }
    }
}
