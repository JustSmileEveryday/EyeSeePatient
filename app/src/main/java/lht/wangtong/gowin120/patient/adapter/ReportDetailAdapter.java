package lht.wangtong.gowin120.patient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public class ReportDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    @Inject
    public ReportDetailAdapter() {
        super(R.layout.item_report_detail_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item)
                .apply(new RequestOptions().error(R.drawable.report_detail_default_img)
                        .placeholder(R.drawable.report_detail_default_img))
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.report_img));
//                .into(new SimpleTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                        ((ImageView) helper.getView(R.id.report_img)).setImageDrawable(resource);
//                    }
//                });
    }
}
