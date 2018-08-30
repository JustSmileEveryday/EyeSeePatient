package lht.wangtong.gowin120.patient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 搜索科普
 *
 * @author luoyc
 */
public class SearchScienceAdapter extends BaseQuickAdapter<ScienceCategoryInfo, BaseViewHolder> {

    @Inject
    public SearchScienceAdapter() {
        super(R.layout.item_search_science_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScienceCategoryInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getBigImage())
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.science_img));
        helper.setText(R.id.science_title, item.getTitle());
        helper.setText(R.id.create_date, item.getCreatedDate());
    }
}
