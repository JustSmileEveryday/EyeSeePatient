package lht.wangtong.gowin120.patient.util;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * @author luoyc
 * @date 2018/3/29
 */

public class ShareUtils {

    private ShareUtils() {
    }

    public static final ShareUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 分享
     *
     * @param context
     * @param web
     * @param image
     * @param shareMedia
     * @param type
     */
    public void share(Activity context, UMWeb web, UMImage image, SHARE_MEDIA shareMedia, int type) {
        if (shareMedia == SHARE_MEDIA.WEIXIN && !UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN)) {
            ToastUtils.showShort("您还未安装微信");
            return;
        }
        switch (type) {
            case 1:
                //链接
                if (web != null) {
                    new ShareAction(context)
                            .setPlatform(shareMedia)
//                .withText("新浪微博")
                            .withMedia(web)
                            .share();
                }
                break;
            case 2:
                //图片
                if (image != null) {
                    new ShareAction(context)
                            .setPlatform(shareMedia)
                            .withMedia(image)
                            .share();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 分享
     *
     * @param context
     * @param web
     * @param shareMedia
     */
    public void share(Activity context, UMWeb web, SHARE_MEDIA shareMedia) {
        if (shareMedia == SHARE_MEDIA.WEIXIN && !UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN)) {
            ToastUtils.showShort("您还未安装微信");
            return;
        }
        //链接
        new ShareAction(context)
                .setPlatform(shareMedia)
//                .withText("新浪微博")
                .withMedia(web)
                .share();

    }

    /**
     * 分享
     *
     * @param context
     * @param web
     * @param shareMedia
     */
    public void share(Activity context, UMImage web, SHARE_MEDIA shareMedia) {
        if (shareMedia == SHARE_MEDIA.WEIXIN && !UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN)) {
            ToastUtils.showShort("您还未安装微信");
            return;
        }
        //图片
        new ShareAction(context)
                .setPlatform(shareMedia)
//                .withText("新浪微博")
                .withMedia(web)
                .share();

    }

    private static class LazyHolder {
        private static final ShareUtils INSTANCE = new ShareUtils();
    }
}
