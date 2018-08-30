package lht.wangtong.gowin120.patient.ui.science.category;

import android.content.Context;

import java.io.File;
import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ArticleDetailInfo;
import lht.wangtong.gowin120.patient.bean.CommentInfo;

/**
 * @author luoyc
 * @date 2018/3/29
 */

public interface CategoryDetailContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setArticle(ArticleDetailInfo detailInfo);

        Context getMyContext();

        void setShareImg(String path);

        void setComments(List<CommentInfo> commentInfos, int total);

        void setCollection(int favorId);

        void addBadge(int num);

        void showCommentDailog();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadArticle(String articleId);

        void loadComment(String articleId);

        void collectionArticle(String articleId);

        void cancleCollection(String articleId, String favorId);

        void commentArticle(String articleId, String content);

        void supportComment(String interactCommentId);

        void loadImage(String path);

        void compressImage(File file);

        void share(int type, ArticleDetailInfo detailInfo);
    }

}
