package lht.wangtong.gowin120.patient.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.CommentInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author luoyc
 */
public class CategoryCommentAdapter extends BaseQuickAdapter<CommentInfo, BaseViewHolder> {

    @Inject
    CategoryCommentAdapter() {
        super(R.layout.item_category_comment_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CommentInfo item) {
        helper.setText(R.id.comment_user_name, item.getUserName());
        helper.setText(R.id.comment_time, item.getCreatedDate());
        helper.setText(R.id.comment, item.getContent());
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getPicUrl())
                .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ((ImageView) helper.getView(R.id.comment_user_pic)).setImageDrawable(resource);
                    }
                });
        helper.setText(R.id.like_nums, item.getSupportCount() + "");
        if (TextUtils.equals(item.getIsSupport(), "Y")) {
            helper.getView(R.id.like_nums).setClickable(false);
            ((TextView) helper.getView(R.id.like_nums)).setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.comment_like_img), null, null, null);
        } else if (TextUtils.equals(item.getIsSupport(), "N")) {
            helper.addOnClickListener(R.id.like_nums);
            ((TextView) helper.getView(R.id.like_nums)).setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.comment_unlike_img), null, null, null);
        }
    }
}
