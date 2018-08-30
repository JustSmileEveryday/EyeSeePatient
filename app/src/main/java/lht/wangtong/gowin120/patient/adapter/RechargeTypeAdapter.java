package lht.wangtong.gowin120.patient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.PayTypeInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 充值类型
 *
 * @author luoyc
 * @date 2017/7/6
 */

public class RechargeTypeAdapter extends BaseQuickAdapter<PayTypeInfo, BaseViewHolder> {

    @Inject
    public RechargeTypeAdapter() {
        super(R.layout.item_recharge_type_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayTypeInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG+item.getLogo())
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.type_img));
        helper.setText(R.id.type_name, item.getName());
        if (item.isChecked()) {
            helper.setBackgroundRes(R.id.recharge_item, R.drawable.recharge_type_bg_click);
        } else {
            helper.setBackgroundRes(R.id.recharge_item, R.drawable.recharge_type_bg_unclick);
        }
    }
}
