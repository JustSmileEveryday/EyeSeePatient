package lht.wangtong.gowin120.patient.ui.guide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import lht.wangtong.gowin120.patient.R;

/**
 * 首次安装引导页
 *
 * @author luoyc
 */
@Route(path = "/ui/guide/GuideActivity")
public class GuideActivity extends Activity {
    @Autowired
    int type;
    private BGABanner mGuideBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initView();
        setListener();
        processLogic();
    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        mGuideBanner = findViewById(R.id.banner_guide);
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mGuideBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, 0, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                if (type == 1) {
                    //登录页
                    ARouter.getInstance().build("/ui/login/LoginActivity").navigation();
                } else if (type == 2) {
                    //首页
                    ARouter.getInstance().build("/ui/MainActivity").navigation();
                }
                finish();
            }
        });
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);
        // 设置数据源
        mGuideBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.home_guide_img_1,
                R.drawable.home_guide_img_2,
                R.drawable.home_guide_img_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
//        mGuideBanner.setBackgroundResource(android.R.color.white);
    }
}
