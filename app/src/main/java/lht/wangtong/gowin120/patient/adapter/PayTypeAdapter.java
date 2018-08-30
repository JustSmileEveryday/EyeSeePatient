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
 * 支付适配器
 * Created by luoyc on 2017/5/3.
 */

public class PayTypeAdapter extends BaseQuickAdapter<PayTypeInfo, BaseViewHolder> {

    @Inject
    public PayTypeAdapter() {
        super(R.layout.pay_type_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayTypeInfo item) {
        if (item.getTypeFlag() == 0) {
            helper.setImageResource(R.id.pay_icon, R.drawable.balance_pay_img);
        }else {
            Glide.with(mContext)
                    .load(Api.HOST_IMG + item.getLogo())
                    .transition(withCrossFade())
                    .into((ImageView) helper.getView(R.id.pay_icon));
        }
        helper.setText(R.id.pay_type_name, item.getName());
        if (item.isChecked()) {
            helper.setBackgroundRes(R.id.choose_img, R.drawable.agent_choosed_img);
        } else {
            helper.setBackgroundRes(R.id.choose_img, R.drawable.agent_un_choosed_img);
        }
    }
}
