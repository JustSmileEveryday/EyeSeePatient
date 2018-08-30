package lht.wangtong.gowin120.patient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 搜索服务
 *
 * @author luoyc
 */
public class SearchServiceAdapter extends BaseQuickAdapter<ServiceCategoryInfo, BaseViewHolder> {

    @Inject
    public SearchServiceAdapter() {
        super(R.layout.item_search_service_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceCategoryInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getLogoUrl())
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.service_img));
        helper.setText(R.id.service_title, item.getTitle());
        helper.setText(R.id.service_introduce, item.getRemark());
        helper.setText(R.id.service_price, item.getPrice());
    }
}
