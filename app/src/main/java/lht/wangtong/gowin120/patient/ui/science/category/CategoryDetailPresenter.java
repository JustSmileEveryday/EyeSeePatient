package lht.wangtong.gowin120.patient.ui.science.category;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ArticleDetailInfo;
import lht.wangtong.gowin120.patient.bean.CommentInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.FileUtils;
import lht.wangtong.gowin120.patient.util.Util;

/**
 * @author luoyc
 * @date 2018/3/29
 */

public class CategoryDetailPresenter extends BasePresenter<CategoryDetailContact.View> implements CategoryDetailContact.Presenter {


    @Inject
    public CategoryDetailPresenter() {
    }

    @Override
    public void loadArticle(String articleId) {
        HttpUtil.getObject(Api.VIEWARTICLE.mapClear()
                .addMap("articleId", articleId)
                .addBody(), ArticleDetailInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setArticle((ArticleDetailInfo) obj);
                    loadImage(Api.HOST_IMG + ((ArticleDetailInfo) obj).getBigImage());
                }
            }
        });
    }

    @Override
    public void loadComment(String articleId) {
        HttpUtil.getObjectListPage(Api.getArticleComments.mapClear()
                .addPage(3, 1)
                .addMap("articleId", articleId)
                .addMap("sortType", 2)
                .addBody(), CommentInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    List<CommentInfo> commentInfos = new ArrayList<>();
                    commentInfos.addAll((Collection<? extends CommentInfo>) obj);
                    mView.setComments(commentInfos, total);
                }
            }
        });
    }

    @Override
    public void collectionArticle(String articleId) {
        HttpUtil.getObject(Api.collectArticle.mapClear()
                .addMap("articleId", articleId)
                .addBody(), Integer.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    ToastUtils.showShort("收藏成功");
                    mView.setCollection((Integer) obj);
                }
            }
        });
    }

    @Override
    public void cancleCollection(String articleId, String favorId) {
        HttpUtil.getObject(Api.deleteCollect.mapClear()
                .addMap("articleId", articleId)
                .addMap("favorId", favorId)
                .addBody(), Integer.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    ToastUtils.showShort("取消收藏");
                }
            }
        });
    }

    @Override
    public void commentArticle(String articleId, String content) {
        HttpUtil.getObject(Api.commentArticle.mapClear()
                .addMap("articleId", articleId)
                .addMap("content", content)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    ToastUtils.showShort("提交成功");
                }
            }
        });
    }

    @Override
    public void supportComment(String interactCommentId) {
        HttpUtil.getObject(Api.supportComment.mapClear()
                .addMap("interactCommentId", interactCommentId)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {

            }
        });
    }

    @Override
    public void loadImage(String path) {
        // 文件在服务器上的存放路径
        RequestParams params = new RequestParams(path);
        // 是否使用断点下载
        params.setAutoResume(true);
        // 下载后是否根据文件的属性自动命名
        params.setAutoRename(false);
        // 文件的本地保存路径
        params.setSaveFilePath(FileUtils.newOutgoingPicPath());
        // 为请求创建新的线程, 取消时请求线程被立即中断; false: 请求建立过程可能不被立即终止
        params.setCancelFast(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }

            @Override
            public void onSuccess(File result) {
                compressImage(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void compressImage(File file) {
        new Compressor(mView.getMyContext())
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.PNG)
                .compressToFileAsFlowable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        mView.setShareImg(file.getAbsolutePath());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void share(int type, ArticleDetailInfo detailInfo) {
        if (!App.mWXAPI.isWXAppInstalled()) {
            ToastUtils.showShort("您还未安装微信客户端");
            return;
        }
        if (detailInfo.getShareImg() == null || TextUtils.isEmpty(detailInfo.getShareImg())) {
            return;
        }
        WXWebpageObject webpageObject = new WXWebpageObject();
        //文章
        webpageObject.webpageUrl = "http://www.eyesee8.com/share.html?articleId=" + detailInfo.getArticleId();
        final WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webpageObject;
        msg.title = detailInfo.getTitle();
        msg.description = detailInfo.getSummer();
        msg.thumbData = Util.bmpToByteArray(BitmapFactory.decodeFile(detailInfo.getShareImg()), true);
        if (msg.thumbData.length > 32768) {
            Bitmap thumb = BitmapFactory.decodeResource(mView.getMyContext().getResources(), R.drawable.wx_share_img);
            msg.thumbData = Util.bmpToByteArray(thumb, true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = detailInfo.getArticleId();
        req.message = msg;
        switch (type) {
            case 1:
                //微信好友
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case 2:
                //微信朋友圈
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            default:
                break;
        }
        App.mWXAPI.sendReq(req);
    }


}
