package lht.wangtong.gowin120.patient.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.PlanScheduleInfo;

/**
 * @author Luoyc
 * @date 2018/3/21
 */

public class EyePlanAdapter extends BaseQuickAdapter<PlanScheduleInfo, BaseViewHolder> {

    @Inject
    public EyePlanAdapter() {
        super(R.layout.item_eye_plan_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanScheduleInfo item) {
        helper.setText(R.id.plan_name, item.getName());
        if (TextUtils.equals(item.getStatus(), "Y")) {
            //开启
            helper.setChecked(R.id.swich_btn, true);
        } else if (TextUtils.equals(item.getStatus(), "N")) {
            //关闭
            helper.setChecked(R.id.swich_btn, false);
        }
        helper.addOnClickListener(R.id.swich_btn);
    }
}
