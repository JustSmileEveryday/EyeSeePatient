package lht.wangtong.gowin120.patient.updateapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;

import java.io.File;


/**
 * author: wqlin
 * time: 2016/5/21 9:56
 */
public class DownloadAppUtils {
    private static final String TAG = DownloadAppUtils.class.getSimpleName();
    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    /**
     * 通过浏览器下载APK包
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "tmp.apk")),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    /*
     *
     addRequestHeader(String header,String value):添加网络下载请求的http头信息
    allowScanningByMediaScanner():用于设置是否允许本MediaScanner扫描。
    setAllowedNetworkTypes(int flags):设置用于下载时的网络类型，默认任何网络都可以下载，提供的网络常量有：NETWORK_BLUETOOTH、NETWORK_MOBILE、NETWORK_WIFI。
    setAllowedOverRoaming(Boolean allowed):用于设置漫游状态下是否可以下载
    setNotificationVisibility(int visibility):用于设置下载时时候在状态栏显示通知信息
    setTitle(CharSequence):设置Notification的title信息
    setDescription(CharSequence):设置Notification的message信息
    setDestinationInExternalFilesDir、setDestinationInExternalPublicDir、 setDestinationUri等方法用于设置下载文件的存放路径，注意如果将下载文件存放在默认路径，那么在空间不足的情况下系统会将文件删除，所 以使用上述方法设置文件存放目录是十分必要的。
     */

    /**
     * 下载更新apk包
     * 权限:1,<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
     *
     * @param context
     * @param url
     */
    public static void downloadForAutoInstall(Context context, String url, String fileName, String title) {
        //LogUtil.e("App 下载 url="+url+",fileName="+fileName+",title="+title);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager = (DownloadManager) context
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //在通知栏中显示
            request.setVisibleInDownloadsUi(true);
            request.setTitle(title);
            String filePath = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//外部存储卡
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath();

            } else {
                ToastUtils.showShort("没有SD卡");
                return;
            }
            downloadUpdateApkFilePath = filePath + File.separator + fileName;
            // 若存在，则删除
            deleteFile(downloadUpdateApkFilePath);
            Uri fileUri = Uri.parse("file://" + downloadUpdateApkFilePath);
            request.setDestinationUri(fileUri);
            downloadUpdateApkId = downloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
            downloadForWebView(context, url);
        }finally {
//            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }


    private static boolean deleteFile(String fileStr) {
        File file = new File(fileStr);
        return file.delete();
    }
}
