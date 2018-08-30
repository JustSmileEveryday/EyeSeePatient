package lht.wangtong.gowin120.patient.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.blankj.utilcode.util.Utils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.vondear.rxtool.RxTool;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.config.Constant;
import lht.wangtong.gowin120.patient.db.Initdb;
import lht.wangtong.gowin120.patient.di.component.ApplicationComponent;
import lht.wangtong.gowin120.patient.di.component.DaggerApplicationComponent;
import lht.wangtong.gowin120.patient.di.module.ApplicationModule;
import lht.wangtong.gowin120.patient.tencent.Init;
import lht.wangtong.gowin120.patient.tencent.utils.Foreground;
import lht.wangtong.gowin120.patient.util.APIutils;
import lht.wangtong.gowin120.patient.util.LoginUtil;


/**
 * @author luoyc
 * @date 2018/1/18
 */

public class App extends Application {

    //微信 第三方app和微信通信的openapi接口
    public static IWXAPI mWXAPI = null;
    private static App mInstance;
    private ApplicationComponent mApplicationComponent;
    private RefWatcher refWatcher;

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static App getInstance() {
        return mInstance;
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
        Foreground.init(this);
        mInstance = this;
        initApplicationComponent();
        if (MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                    }
                }
            });
            TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
            settings.setEnabled(true);
            TIMManager.getInstance().configOfflinePushSettings(settings);
        }
        initAll();
    }

    private void initAll() {
        initXutils();
        initAlibaba();
        RxTool.init(this);
        // 设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        Utils.init(this);
        FlowManager.init(this);
        //将应用注册到微信
        mWXAPI = WXAPIFactory.createWXAPI(this, null);
        mWXAPI.registerApp(Constant.APP_ID);
        intARouter();
        initUmeng();
        updateFile();
    }

    private void initAlibaba() {
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                // ...
//                Log.e("luoyc", "AlibcTradeSDK onSuccess: " );
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
//                Log.e("luoyc", "onFailure: " +code+"   "+msg);
            }
        });
    }

    private void initUmeng() {
        UMConfigure.init(this, "58328c111061d25e1500127d"
                , null, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(false);
        PlatformConfig.setWeixin("wx5d7f760ac6136336", "dfcade257e5e141272448660824d9e9d");
        PlatformConfig.setQQZone("1105712131", "PFdh8MKGL8SHjCL3");
        PlatformConfig.setSinaWeibo("2494170141","273c2a7c352601248915fe510f300ad8","http://sns.whalecloud.com/sina2/callback");
    }

    /**
     * 初始化网络框架
     */
    private void initXutils() {
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        new Initdb("EyeSeePatient");
    }

    /**
     * 初始化路由
     */
    private void intARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }

    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * 检查版本
     */
    public void checkVersion() {

    }

    /**
     * 登录腾讯IM
     */
    public void loginTencent() {
        if (LoginUtil.user != null) {
            Init.getInstance().initTencentIM();
//            Init.getInstance().loginIMSDK();
        }
    }


    /**
     * 更新配置文件
     */
    private void updateFile() {
        APIutils.getCommonCd();
//        APIutils.getSystemParam();
//        APIutils.getSalesOrgaById();
//        APIutils.getSystemArea();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
