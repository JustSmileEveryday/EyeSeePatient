package lht.wangtong.gowin120.patient.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 自助咨询适配器
 *
 * @author luoyc
 */
public class AutoConsultAdapter extends BaseQuickAdapter<BannerInfo, BaseViewHolder> {

    @Inject
    public AutoConsultAdapter() {
        super(R.layout.item_auto_consult);
    }

    @Override
    protected void convert(final BaseViewHolder helper, BannerInfo item) {
        helper.setText(R.id.consult_name, item.getTitle());
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getImgUrl())
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ((ImageView) helper.getView(R.id.consult_img)).setImageDrawable(resource);
                    }
                });
    }
}
