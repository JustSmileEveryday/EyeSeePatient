package lht.wangtong.gowin120.patient.tencent.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.tencent.model.Message;
import lht.wangtong.gowin120.patient.util.LoginUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 聊天界面adapter
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private final String TAG = "ChatAdapter";

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private Context mContext;
    private String doctorId;
    private String picUrl;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ChatAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.mContext = context;
        resourceId = resource;
    }

    public ChatAdapter(Context context, int resource, List<Message> objects, String doctorId, String picUrl) {
        super(context, resource, objects);
        this.mContext = context;
        resourceId = resource;
        this.doctorId = doctorId;
        this.picUrl = picUrl;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        return view != null ? view.getId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftMessage = view.findViewById(R.id.leftMessage);
            viewHolder.rightMessage = view.findViewById(R.id.rightMessage);
            viewHolder.leftPanel = view.findViewById(R.id.leftPanel);
            viewHolder.rightPanel = view.findViewById(R.id.rightPanel);
            viewHolder.sending = view.findViewById(R.id.sending);
            viewHolder.error = view.findViewById(R.id.sendError);
            viewHolder.sender = view.findViewById(R.id.sender);
            viewHolder.rightDesc = view.findViewById(R.id.rightDesc);
            viewHolder.systemMessage = view.findViewById(R.id.systemMessage);
            viewHolder.leftAvatar = view.findViewById(R.id.leftAvatar);
            viewHolder.rightAvatar = view.findViewById(R.id.rightAvatar);
            Glide.with(mContext)
                    .load(Api.HOST_IMG + LoginUtil.user.getPicUrl())
                    .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                    .transition(withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            viewHolder.rightAvatar.setImageDrawable(resource);
                        }
                    });
            Glide.with(mContext)
                    .load(Api.HOST_IMG + picUrl)
                    .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                    .transition(withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            viewHolder.leftAvatar.setImageDrawable(resource);
                        }
                    });
            view.setTag(viewHolder);
        }
        if (position < getCount()) {
            final Message data = getItem(position);
            data.showMessage(viewHolder, getContext());
        }
        return view;
    }


    public class ViewHolder {
        public RelativeLayout leftMessage;
        public RelativeLayout rightMessage;
        public RelativeLayout leftPanel;
        public RelativeLayout rightPanel;
        public ProgressBar sending;
        public ImageView error;
        public TextView sender;
        public TextView systemMessage;
        public TextView rightDesc;
        public CircleImageView leftAvatar;
        public CircleImageView rightAvatar;
    }
}
