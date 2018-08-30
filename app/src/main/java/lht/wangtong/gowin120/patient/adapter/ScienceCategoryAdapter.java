package lht.wangtong.gowin120.patient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 科普 - 文章适配器
 *
 * @author luoyc
 * @date 2018/3/7
 */

public class ScienceCategoryAdapter extends BaseQuickAdapter<ScienceCategoryInfo, BaseViewHolder> {

    @Inject
    public ScienceCategoryAdapter() {
        super(R.layout.item_science_category_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScienceCategoryInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getBigImage())
                .apply(new RequestOptions().placeholder(R.drawable.home_banner_error_img))
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.category_banner));
        helper.setText(R.id.category_title, item.getTitle());
        helper.setText(R.id.category_time, item.getCreatedDate());
        helper.setText(R.id.category_tag, item.getKeyWord());
        helper.setText(R.id.category_click_times, item.getClickCount());
        helper.setText(R.id.share_times, item.getShareCount());
        helper.setText(R.id.comments, item.getCommentCount());
    }
}
