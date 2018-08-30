package lht.wangtong.gowin120.patient.tencent.business;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.huawei.android.pushagent.PushManager;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.tencent.utils.PushUtil;
import yue.wangtong.lht.tencent.event.MessageEvent;

import static com.tencent.av.sdk.AVClientInfo.getPackageName;


/**
 * 初始化
 * 包括imsdk等
 * @author Administrator
 */
public class InitBusinessHelper {
    private static String TAG = "InitBusinessHelper";

    public static InitBusinessHelper getInstance() {
        return InitBusinessHelperHolder.INSTANCE;
    }

    /**
     * 初始化App
     */
    public void initApp(Context context, int appId, int accountType) {
        ILiveSDK.getInstance().initSdk(context, appId, accountType);
    }

    /**
     * 重新登陆IM
     *
     * @param useId
     * @param userSig
     */
    public void reLoginIM(String useId, String userSig, ILiveLoginManager.TILVBStatusListener listener) {
        ILiveLoginManager.getInstance().iLiveLogin(useId, userSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                String deviceMan = android.os.Build.MANUFACTURER;
                //注册小米和华为推送
                if (deviceMan.equals("Xiaomi") && shouldMiInit()) {
                    MiPushClient.registerPush(App.getAppContext(), "2882303761517564226", "5281756443226");
                } else if (deviceMan.equals("HUAWEI")) {
                    PushManager.requestToken(App.getAppContext());
                }
                TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
                settings.setEnabled(true);
                TIMManager.getInstance().configOfflinePushSettings(settings);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Log.e("this", "reLoginIM fail ：" + errCode + "|" + errMsg);
            }
        });
        ILiveLoginManager.getInstance().setUserStatusListener(listener);
    }

    /**
     * 判断小米推送是否已经初始化
     */
    private boolean shouldMiInit() {
        ActivityManager am = ((ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    private static class InitBusinessHelperHolder {
        /**
         * 单例对象实例
         */
        static final InitBusinessHelper INSTANCE = new InitBusinessHelper();
    }
}
