package lht.wangtong.gowin120.patient.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.EyeSignInInfo;

/**
 *保健操打卡列表
 * @author luoyc
 * @date 2018/3/21
 */

public class EyeSignInAdapter extends BaseQuickAdapter<EyeSignInInfo,BaseViewHolder>{

    @Inject
    public EyeSignInAdapter() {
        super(R.layout.item_eye_sign_in_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, EyeSignInInfo item) {
        helper.setText(R.id.date,item.getDate());
        if (TextUtils.isEmpty(item.getAmTime())){
            helper.setVisible(R.id.amTime,false);
        }else {
            helper.setVisible(R.id.amTime,true);
            helper.setText(R.id.amTime,item.getAmTime());
        }
        if (TextUtils.isEmpty(item.getPmTime())){
            helper.setVisible(R.id.pmTime,false);
        }else {
            helper.setVisible(R.id.pmTime,true);
            helper.setText(R.id.pmTime,item.getPmTime());
        }
    }
}
