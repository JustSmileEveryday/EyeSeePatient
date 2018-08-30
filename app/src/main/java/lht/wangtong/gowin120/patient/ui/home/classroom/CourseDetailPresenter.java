package lht.wangtong.gowin120.patient.ui.home.classroom;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ArticleDetailInfo;
import lht.wangtong.gowin120.patient.bean.CommentInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class CourseDetailPresenter extends BasePresenter<CourseDetailContact.View> implements CourseDetailContact.Presenter {

    @Inject
    public CourseDetailPresenter() {
    }


    @Override
    public void loadArticle(String articleId) {
        HttpUtil.getObject(Api.VIEWARTICLE.mapClear()
                .addMap("articleId", articleId)
                .addBody(), ArticleDetailInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setCourseInfo((ArticleDetailInfo) obj);
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
}
