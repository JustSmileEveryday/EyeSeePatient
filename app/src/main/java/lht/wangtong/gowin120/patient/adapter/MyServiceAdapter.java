package lht.wangtong.gowin120.patient.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.MyServiceInfo;

/**
 * 我的服务适配器
 *
 * @author luoyc
 */
public class MyServiceAdapter extends BaseQuickAdapter<MyServiceInfo, BaseViewHolder> {

    @Inject
    public MyServiceAdapter() {
        super(R.layout.item_my_service_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyServiceInfo item) {
        helper.setText(R.id.service_name, item.getServiceTitle());
        helper.setText(R.id.service_date, item.getCreatedDate());
        helper.setText(R.id.residue_count, "剩余次数" + item.getResidueCount() + "次");
        if (TextUtils.equals(item.getPayStatus(), "1")) {
            //未支付
            helper.setText(R.id.service_state, "未支付");
            helper.setTextColor(R.id.service_state,mContext.getResources().getColor(R.color.service_state_1));
            helper.setBackgroundRes(R.id.state, R.drawable.service_state_1);
        } else {
            //订购成功
            switch (item.getUseStatus()) {
                case 1:
                    //未使用
                    helper.setText(R.id.service_state, "订购成功");
                    helper.setBackgroundRes(R.id.state, R.drawable.service_state_2);
                    helper.setTextColor(R.id.service_state,mContext.getResources().getColor(R.color.service_state_2));
                    break;
                case 2:
                    //使用中
                    helper.setText(R.id.service_state, "使用中");
                    helper.setBackgroundRes(R.id.state, R.drawable.service_state_3);
                    helper.setTextColor(R.id.service_state,mContext.getResources().getColor(R.color.service_state_3));
                    break;
                case 3:
                    //已使用
                    helper.setText(R.id.service_state, "已使用");
                    helper.setBackgroundRes(R.id.state, R.drawable.service_state_4);
                    helper.setTextColor(R.id.service_state,mContext.getResources().getColor(R.color.service_state_4));
                    break;
                default:
                    break;
            }
        }
    }
}
