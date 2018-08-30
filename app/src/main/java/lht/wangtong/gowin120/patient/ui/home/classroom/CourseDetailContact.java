package lht.wangtong.gowin120.patient.ui.home.classroom;

import android.content.Context;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ArticleDetailInfo;
import lht.wangtong.gowin120.patient.bean.CommentInfo;

/**
 * @author luoyc
 */
public interface CourseDetailContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initPlayer();

        void setCourseInfo(ArticleDetailInfo detailInfo);

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

    }
}
