package lht.wangtong.gowin120.patient.ui.mine.report;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.view.ShareDialog;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author luoyc
 */
public class ReportShareActivity extends Activity {

    private final int SHARE = 105;
    private String imgUrl;
    private ShareDialog mShareDialog;
    private UMImage umImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_share);
        initView();
    }

    private void initView() {
        ImageView mReportView = findViewById(R.id.report_share_img);
        imgUrl = getIntent().getStringExtra("imgUrl");
        if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
            Glide.with(this)
                    .load(Api.HOST_IMG + imgUrl)
                    .apply(new RequestOptions().error(R.drawable.report_detail_default_img)
                            .placeholder(R.drawable.report_detail_default_img))
                    .transition(withCrossFade())
                    .into(mReportView);

        }
    }

    /**
     * 分享
     *
     * @param view
     */
    public void share(View view) {
        if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
            UMWeb web = new UMWeb(Api.HOST_IMG + imgUrl);
            web.setTitle("眼力pk");
            web.setDescription("关爱您的眼睛");
            umImage = new UMImage(this, Api.HOST_IMG + imgUrl);
            UMImage thumb = new UMImage(this, R.drawable.logo_img);
            umImage.setThumb(thumb);
            if (mShareDialog == null) {
                mShareDialog = new ShareDialog(this, web, Api.HOST_IMG + imgUrl, 1);
            }
            mShareDialog.show();
        }

    }


    public void back(View view) {
        finish();
    }

}
