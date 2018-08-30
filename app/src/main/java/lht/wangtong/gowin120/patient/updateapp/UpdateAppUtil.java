package lht.wangtong.gowin120.patient.updateapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.VersionInfo;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.view.PromptDialog;


/**
 * Created by Teprinciple on 2016/11/15.
 */
public class UpdateAppUtil {

    /**
     * 获取apk的版本号 currentVersionCode
     *
     * @param ctx
     * @return
     */
    public static int getAPPLocalVersion(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }


    public static void getAPPServerVersion(Context context, final VersionCallBack callBack) {

        HttpUtil.getObject(Api.GETVERSION.mapClear().addBody(), VersionInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
//                        BaseApplication.getIntance().checkVersion();
                    callBack.callBack((VersionInfo) obj);
                }
            }
        });
    }


    /**
     * 更新APP
     *
     * @param context
     */
    public static void updateApp(final Context context) {

        getAPPServerVersion(context, new VersionCallBack() {
            @Override
            public void callBack(final VersionInfo info) {

                if (info != null && info.getVersionCode() != null) {

                    if (Integer.valueOf(info.getVersionCode()) > getAPPLocalVersion(context)) {
                        PromptDialog dialog = new PromptDialog(context, new PromptDialog.ICallback() {
                            @Override
                            public void callback() {
                                DownloadAppUtils.downloadForAutoInstall(context, Api.HOST_IMG + info.getLoadPath(), "wesee_patient.apk", "更新小艾");
                            }
                        });
                        dialog.setContent("发现新版本:小艾" + info.getVersionNumber() + "\n是否下载更新?");
                        dialog.showCancleBtn(true);
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                }
            }
        });
    }

    public interface VersionCallBack {
        void callBack(VersionInfo info);
    }


}
