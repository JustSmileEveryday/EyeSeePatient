package lht.wangtong.gowin120.patient.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.DoctorInteractComment;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author luoyc
 */
public class CommentAdapter extends BaseQuickAdapter<DoctorInteractComment, BaseViewHolder> {

    @Inject
    public CommentAdapter() {
        super(R.layout.item_comment_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctorInteractComment item) {
        helper.setText(R.id.comment_user_name, item.getMemberName());
        helper.setText(R.id.comment_time, item.getDate());
        helper.setText(R.id.comment, item.getContent());
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getPicUrl())
                .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.comment_user_pic));
    }
}
