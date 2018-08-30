package lht.wangtong.gowin120.patient.adapter;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 服务列表adapter
 *
 * @author luoyc
 * @date 2018/4/3
 */

public class ServiceCategoryAdapter extends BaseQuickAdapter<ServiceCategoryInfo, BaseViewHolder> {

    @Inject
    public ServiceCategoryAdapter() {
        super(R.layout.item_service_category_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceCategoryInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getLogoUrl())
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.service_img));
        helper.setText(R.id.service_title, item.getTitle());
        helper.setText(R.id.service_content, item.getRemark());
        helper.setText(R.id.service_price, "￥" + item.getPrice());
        helper.setText(R.id.service_original_price, "原价￥" + item.getOriginalPrice());
        helper.setText(R.id.service_buy_count, item.getBuyCount() + "已购买");
        ((TextView) helper.getView(R.id.service_original_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
