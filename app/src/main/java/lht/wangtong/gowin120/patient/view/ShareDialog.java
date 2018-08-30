package lht.wangtong.gowin120.patient.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vondear.rxtool.view.RxToast;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.util.ShareUtils;

/**
 * 分享弹窗
 *
 * @author luoyc
 */
public class ShareDialog extends BaseDialog implements View.OnClickListener {
    private UMWeb mWeb;
    private Activity mContext;
    private String mCopyContent;
    private int mType;
    private UMImage umImage;

    public ShareDialog(Activity context, UMWeb web) {
        super(context);
        this.mContext = context;
        this.mWeb = web;
        setCustomDialog();
    }

    public ShareDialog(Activity context, UMWeb web, String content, int mType) {
        super(context);
        this.mContext = context;
        this.mWeb = web;
        mCopyContent = content;
        this.mType = mType;
        setCustomDialog();
    }

    public ShareDialog(Activity context, UMImage image, String content, int mType) {
        super(context);
        this.mContext = context;
        this.mType = mType;
        this.umImage = image;
        mCopyContent = content;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_share_layout, null);
        TextView mWeixinFriend = mView.findViewById(R.id.wx_friend);
        TextView mWeixinscene = mView.findViewById(R.id.wx_friend_scene);
        TextView mQq = mView.findViewById(R.id.qq_friend);
        TextView mQqZone = mView.findViewById(R.id.qq_zone);
        TextView mCopyBtn = mView.findViewById(R.id.copy_btn);
        TextView mSinaBtn = mView.findViewById(R.id.sina_share_btn);
        TextView mCancelBtn = mView.findViewById(R.id.cancel_btn);
        mWeixinFriend.setOnClickListener(this);
        mWeixinscene.setOnClickListener(this);
        mQq.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mQqZone.setOnClickListener(this);
        mSinaBtn.setOnClickListener(this);
        mCopyBtn.setOnClickListener(this);
        setContentView(mView);
    }

    @Override
    public void show() {
        super.show();
        setGravity(Gravity.BOTTOM);
        setSize(ScreenUtils.getScreenWidth(), 0);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wx_friend:
                ShareUtils.getInstance().share(mContext, mWeb, umImage, SHARE_MEDIA.WEIXIN, mType);
                break;
            case R.id.wx_friend_scene:
                ShareUtils.getInstance().share(mContext, mWeb, umImage, SHARE_MEDIA.WEIXIN_CIRCLE, mType);
                break;
            case R.id.qq_friend:
                ShareUtils.getInstance().share(mContext, mWeb, umImage, SHARE_MEDIA.QQ, mType);
                break;
            case R.id.qq_zone:
                ShareUtils.getInstance().share(mContext, mWeb, umImage, SHARE_MEDIA.QZONE, mType);
                break;
            case R.id.sina_share_btn:
                ShareUtils.getInstance().share(mContext, mWeb, umImage, SHARE_MEDIA.SINA, mType);
                break;
            case R.id.copy_btn:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", mCopyContent);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                RxToast.success("已复制到剪贴板");
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
    }
}
