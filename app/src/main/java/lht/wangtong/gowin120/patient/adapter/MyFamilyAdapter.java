package lht.wangtong.gowin120.patient.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public class MyFamilyAdapter extends BaseQuickAdapter<HomeFamilyInfo, BaseViewHolder> {

    @Inject
    public MyFamilyAdapter() {
        super(R.layout.item_my_family_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, HomeFamilyInfo item) {
        helper.setText(R.id.family_name, item.getName());
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getPicUrl())
//                .placeholder(R.drawable.mine_head_default_img)
                .apply(new RequestOptions().error(R.drawable.mine_head_default_img)
                .placeholder(R.drawable.mine_head_default_img))
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ((ImageView) helper.getView(R.id.family_pic)).setImageDrawable(resource);
                    }
                });

    }
}
