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
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.config.Api;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author 课堂列表适配器
 */
public class CourseListAdapter extends BaseQuickAdapter<CourseInfo, BaseViewHolder> {

    @Inject
    public CourseListAdapter() {
        super(R.layout.item_course_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CourseInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getBigImage())
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ((ImageView) helper.getView(R.id.face_img)).setImageDrawable(resource);
                    }
                });
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.play_times, item.getClickCount());
        helper.setText(R.id.comments, item.getCommentCount());
        helper.setText(R.id.play_time, item.getPlayTime());
        helper.setText(R.id.time, item.getCreatedDate());
    }
}
