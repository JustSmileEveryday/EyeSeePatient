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
import lht.wangtong.gowin120.patient.bean.ConsultInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.tencent.utils.TimeUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 历史咨询适配器
 *
 * @author luoyc
 */
public class HistoryConsultAdapter extends BaseQuickAdapter<ConsultInfo, BaseViewHolder> {

    @Inject
    public HistoryConsultAdapter() {
        super(R.layout.item_history_consult_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, ConsultInfo item) {
        Glide.with(mContext)
                .load(Api.HOST_IMG + item.getPicUrl())
                .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ((ImageView) helper.getView(R.id.head_img)).setImageDrawable(resource);
                    }
                });
        helper.setText(R.id.name, item.getUserName());
        helper.setText(R.id.latest_message, item.getLastMessageSummary());
        helper.setText(R.id.time, TimeUtil.getTimeStr(item.getLastMessageTime()));
        helper.addOnClickListener(R.id.head_img);
        if (item.getUnreadNum() <= 0) {
            helper.setVisible(R.id.un_read_num, false);
        } else {
            helper.setVisible(R.id.un_read_num, true);
            String unReadStr = String.valueOf(item.getUnreadNum());
            if (item.getUnreadNum() < 10) {
                helper.setBackgroundRes(R.id.un_read_num, R.drawable.point1);
            } else {
                helper.setBackgroundRes(R.id.un_read_num, R.drawable.point2);
                if (item.getUnreadNum() > 99) {
                    unReadStr = mContext.getResources().getString(R.string.time_more);
                }
            }
            helper.setText(R.id.un_read_num, unReadStr);
        }
    }
}
