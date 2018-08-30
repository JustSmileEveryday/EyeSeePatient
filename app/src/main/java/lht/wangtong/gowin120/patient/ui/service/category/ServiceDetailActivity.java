package lht.wangtong.gowin120.patient.ui.service.category;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.ServiceDetailInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.util.MyWebViewClient;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 服务详情界面
 *
 * @author luoyc
 */
@Route(path = "/service/category/ServiceDetailActivity")
public class ServiceDetailActivity extends BaseActivity<ServiceDetailPresenter> implements ServiceDetailContract.View, BGABanner.Adapter<ImageView, String>, BGABanner.Delegate<ImageView, String> {
    @Autowired
    public String marketActivityId;
    @BindView(R.id.service_banner)
    BGABanner mBanner;
    @BindView(R.id.buy_count)
    TextView mCount;
    @BindView(R.id.service_name)
    TextView mServiceName;
    @BindView(R.id.service_content)
    TextView mServiceContent;
    @BindView(R.id.service_price)
    TextView mServicePrice;
    @BindView(R.id.service_original_price)
    TextView mServiceOriginalPrice;
    @BindView(R.id.service_price_1)
    TextView mServicePrice1;
    @BindView(R.id.service_original_price_1)
    TextView mServiceOriginalPrice1;
    @BindView(R.id.service_use_sign)
    WebView mNoticeForUse;
    @BindView(R.id.service_detail_sign)
    WebView mServiceDetail;
    @BindView(R.id.effective_count)
    TextView mEffectiveCount;
    @BindView(R.id.service_agent_name)
    TextView mServiceAgentName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        initBanner();
        initWebView();
        mPresenter.loadServiceDetail(marketActivityId);
    }

    private void initWebView() {
        WebSettings settings = mServiceDetail.getSettings();
        //支持获取手势焦点
        mServiceDetail.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
//        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        }
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(mServiceDetail.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setRenderPriority(WebSettings.RenderPriority.NORMAL);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置
        // 设置web视图客户端
        mServiceDetail.setWebViewClient(new MyWebViewClient());
        // mweb.loadUrl("javascript:test('aa')");
    }

    @Override
    public void initBanner() {
        float Rate; //宽高比
        //广告图片高度
        int imgH = 480;
        //广告图片宽度
        int imgW = 1080;
        //当前比例
        Rate = imgW * 1000 / imgH;
        WindowManager wm = (WindowManager) this.getSystemService(
                Context.WINDOW_SERVICE);
        float width = wm.getDefaultDisplay().getWidth();
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, (int) (width * 1000 / Rate));
        mBanner.setLayoutParams(params);
        mBanner.setAdapter(this);
        mBanner.setDelegate(this);
        mBanner.setIsNeedShowIndicatorOnOnlyOnePage(false);
        mBanner.setAutoPlayInterval(5000);
        mBanner.setAllowUserScrollable(true);
    }

    @Override
    public void setServiceBanner(List<String> bannerInfos) {
        mBanner.setAutoPlayAble(bannerInfos.size() > 1);
        mBanner.setData(bannerInfos, null);
    }

    @Override
    public void setServiceDetail(ServiceDetailInfo info) {
        List<String> bannerInfos = new ArrayList<>();
        if (info.getBannerUrl().contains(",")) {
            String[] banners = info.getBannerUrl().split(",");
            bannerInfos.addAll(Arrays.asList(banners));
        } else {
            bannerInfos.add(info.getBannerUrl());
        }
        setServiceBanner(bannerInfos);
        mServiceName.setText(info.getTitle());
        mServiceContent.setText(info.getRemark());
        mServicePrice.setText(info.getPrice());
        mServicePrice1.setText(info.getPrice());
        mEffectiveCount.setText(info.getEffectiveCount() + "次");
        mServiceOriginalPrice.setText("原价:￥" + info.getOriginalPrice());
        mServiceOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mServiceOriginalPrice1.setText("原价:￥" + info.getOriginalPrice());
        mServiceOriginalPrice1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mNoticeForUse.loadDataWithBaseURL(null, info.getNoticeForUse(), "text/html", "utf-8", null);
        mServiceDetail.loadDataWithBaseURL(null, info.getContent(), "text/html", "utf-8", null);
        mServiceAgentName.setText(info.getServiceAgentName());
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        Glide.with(this)
                .load(Api.HOST_IMG + model)
                .apply(new RequestOptions().error(R.drawable.home_banner_error_img))
                .transition(withCrossFade())
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {

    }

    @OnClick({R.id.buy_btn, R.id.buy_btn_1, R.id.back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buy_btn:
            case R.id.buy_btn_1:
                mPresenter.saveService(marketActivityId);
                break;
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }
    }
}
