package lht.wangtong.gowin120.patient.ui.science.category;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.CommentInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class CommentPresenter extends BasePresenter<CommentContract.View> implements CommentContract.Presenter {
    private List<CommentInfo> commentInfos;
    private int pageNo = 1;

    @Inject
    public CommentPresenter() {
    }

    @Override
    public void initData(String articleId) {
        commentInfos = new ArrayList<>();
        loadComment(articleId);
    }

    @Override
    public void loadMore(String articleId) {
        pageNo++;
        loadComment(articleId);
    }

    @Override
    public void onRefresh(String articleId) {
        pageNo = 1;
        commentInfos.clear();
        loadComment(articleId);
    }

    @Override
    public void loadComment(String articleId) {
        HttpUtil.getObjectListPage(Api.getArticleComments.mapClear()
                .addPage(10, pageNo)
                .addMap("articleId", articleId)
                .addMap("sortType", 1)
                .addBody(), CommentInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
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
                    mView.setCollection(0);
                }
            }
        });
    }

    @Override
    public void commentArticle(final String articleId, String content) {
        HttpUtil.getObject(Api.commentArticle.mapClear()
                .addMap("articleId", articleId)
                .addMap("content", content)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
//                    ToastUtils.showShort("提交成功");
                    onRefresh(articleId);
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
}
