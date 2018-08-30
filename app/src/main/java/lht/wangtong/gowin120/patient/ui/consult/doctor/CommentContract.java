package lht.wangtong.gowin120.patient.ui.consult.doctor;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.CommentLabelInfo;

/**
 * @author luoyc
 */
public interface CommentContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setCommentList(List<CommentLabelInfo> labelInfos);

        void saveSuccess();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getCommentLabelList();

        void saveRemoteConsultationComment(String keywordIds,String content,String commentRemoteConsultationId);

    }

}
