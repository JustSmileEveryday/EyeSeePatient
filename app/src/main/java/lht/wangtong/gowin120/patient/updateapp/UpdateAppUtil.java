package lht.wangtong.gowin120.patient.updateapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.AppUtils;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.VersionInfo;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;


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
                    if (Integer.valueOf(info.getVersionCode()) > AppUtils.getAppVersionCode()) {
                        new MaterialDialog.Builder(context)
                                .title("发现新版本:")
                                .content("小艾眼管家 V" + info.getVersionNumber() + "\n是否下载更新?")
                                .positiveText(R.string.sure)
                                .negativeText(R.string.cancel)
                                .positiveColor(Color.parseColor("#01C1B4"))
                                .negativeColor(Color.parseColor("#3C4045"))
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        DownloadAppUtils.downloadForAutoInstall(context, Api.HOST_IMG + info.getLoadPath(), "wesee_patient.apk", "更新小艾");
                                        dialog.dismiss();
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }
            }
        });
    }

    public interface VersionCallBack {
        void callBack(VersionInfo info);
    }


}
