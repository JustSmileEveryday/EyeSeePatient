package lht.wangtong.gowin120.patient.ui.consult.doctor;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.CommentLabelInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class CommentPresenter extends BasePresenter<CommentContract.View> implements CommentContract.Presenter {

    @Inject
    public CommentPresenter() {
    }

    @Override
    public void getCommentLabelList() {
        HttpUtil.getObjectList(Api.getCommentLabelList.mapClear()
                .addBody(), CommentLabelInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<CommentLabelInfo> labelInfos = new ArrayList<>();
                    labelInfos.addAll((Collection<? extends CommentLabelInfo>) obj);
                    mView.setCommentList(labelInfos);
                }
            }
        });
    }

    @Override
    public void saveRemoteConsultationComment(String keywordIds, String content, String commentRemoteConsultationId) {
        HttpUtil.getObject(Api.saveRemoteConsultationComment.mapClear()
                .addMap("keywordIds", keywordIds)
                .addMap("content", content)
                .addMap("commentRemoteConsultationId", commentRemoteConsultationId)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.saveSuccess();
                }
            }
        });
    }
}
