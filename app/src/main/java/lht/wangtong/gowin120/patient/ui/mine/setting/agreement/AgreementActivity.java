package lht.wangtong.gowin120.patient.ui.mine.setting.agreement;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.FileUtils;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.HomeActivityInfo;
import lht.wangtong.gowin120.patient.util.MyWebViewClient;
import lht.wangtong.gowin120.patient.view.OrdinaryTitleBar;

/**
 * 用户协议
 *
 * @author luoyc
 * @date 2018/3/26
 */
@Route(path = "/setting/agreement/AgreementActivity")
public class AgreementActivity extends BaseActivity<AgreementPresenter> implements AgreementContact.View {
    private final String APP_CACAHE_DIRNAME = "/webcache";
    @BindView(R.id.agreement_title)
    OrdinaryTitleBar title;
    @BindView(R.id.agreement_web)
    WebView mWebView;
    @Autowired
    int type;
    @Autowired
    String activityId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        initWebView();
        mPresenter.initData(type,activityId);
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
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
        settings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());

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
        mWebView.setWebViewClient(new MyWebViewClient());
        // mweb.loadUrl("javascript:test('aa')");
    }

    @Override
    public void setData(int type, String url,HomeActivityInfo homeActivityInfo) {
        mWebView.clearHistory();
        switch (type) {
            case 1:
                //用户注册协议
                title.setTitle("用户协议");
                mWebView.loadUrl(url);
                break;
            case 2:
                //患者就诊告知书 协议
                title.setTitle("患者知情书");
                mWebView.loadUrl(url);
                break;
            case 3:
                //首页活动
                if (homeActivityInfo != null){
                    title.setTitle(homeActivityInfo.getActivityName());
                    mWebView.loadDataWithBaseURL(null, homeActivityInfo.getActivityContent(), "text/html", "utf-8", null);
                }
                break;
            default:
                break;
        }

    }
}
