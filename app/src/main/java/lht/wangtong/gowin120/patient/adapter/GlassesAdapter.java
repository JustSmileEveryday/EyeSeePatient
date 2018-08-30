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
import lht.wangtong.gowin120.patient.bean.GlassesInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 眼镜适配器
 *
 * @author luoyc
 */
public class GlassesAdapter extends BaseQuickAdapter<GlassesInfo, BaseViewHolder> {

    @Inject
    public GlassesAdapter() {
        super(R.layout.item_glasses_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GlassesInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getPicUrl())
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ((ImageView) helper.getView(R.id.glasses_img)).setImageDrawable(resource);
                        item.setDrawable(resource);
                    }
                });
    }
}
