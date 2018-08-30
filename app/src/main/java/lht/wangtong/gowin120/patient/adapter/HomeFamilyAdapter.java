package lht.wangtong.gowin120.patient.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 首页家人人员列表
 * Created by luoyc on 2018/3/6.
 */

public class HomeFamilyAdapter extends BaseMultiItemQuickAdapter<HomeFamilyInfo, BaseViewHolder> {

    public HomeFamilyAdapter(List<HomeFamilyInfo> infos) {
        super(infos);
        addItemType(HomeFamilyInfo.FAMILY, R.layout.item_activity_layout);
        addItemType(HomeFamilyInfo.ADD, R.layout.item_add_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, HomeFamilyInfo item) {
        switch (item.getItemType()) {
            case 1:
                //成员
                helper.setText(R.id.name, item.getName());
                Glide.with(mContext)
                        .load(Api.HOST_IMG + item.getPicUrl())
//                        .placeholder(R.drawable.mine_head_default_img)
                        .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                        .transition(withCrossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                ((ImageView) helper.getView(R.id.head_img_choosed)).setImageDrawable(resource);
                            }
                        });
                Glide.with(mContext)
                        .load(Api.HOST_IMG + item.getPicUrl())
//                        .placeholder(R.drawable.mine_head_default_img)
                        .transition(withCrossFade())
                        .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                ((ImageView) helper.getView(R.id.head_img_unchoosed)).setImageDrawable(resource);
                            }
                        });
                if (item.isChoose()) {
                    helper.setGone(R.id.head_img_unchoosed_layout, false);
                    helper.setGone(R.id.head_img_layout, true);
                } else {
                    helper.setGone(R.id.head_img_layout, false);
                    helper.setGone(R.id.head_img_unchoosed_layout, true);
                }
                break;
            case 2:
                //添加

                break;
            default:
                break;
        }
    }
}
