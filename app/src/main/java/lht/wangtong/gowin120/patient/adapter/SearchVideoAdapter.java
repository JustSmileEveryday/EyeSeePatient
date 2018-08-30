package lht.wangtong.gowin120.patient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 搜索视频
 *
 * @author luoyc
 */
public class SearchVideoAdapter extends BaseQuickAdapter<CourseInfo, BaseViewHolder> {

    @Inject
    public SearchVideoAdapter() {
        super(R.layout.item_search_video_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getBigImage())
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.video_img));
        helper.setText(R.id.video_title, item.getTitle());
        helper.setText(R.id.time, item.getCreatedDate());
    }
}
