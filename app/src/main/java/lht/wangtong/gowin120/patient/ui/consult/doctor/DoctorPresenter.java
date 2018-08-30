package lht.wangtong.gowin120.patient.ui.consult.doctor;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.DoctorInfo;
import lht.wangtong.gowin120.patient.bean.DoctorInteractComment;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class DoctorPresenter extends BasePresenter<DoctorContract.View> implements DoctorContract.Presenter {
    private int mPageNo = 1;
    private List<DoctorInteractComment> commentList;

    @Inject
    public DoctorPresenter() {
    }

    @Override
    public void initData(String docId) {
        commentList = new ArrayList<>();
        getDoctorInfo(docId);
        getDoctorInteractComment(docId);
    }

    @Override
    public void getDoctorInfo(String docId) {
        HttpUtil.getObject(Api.getDoctorInfo.mapClear()
                .addMap("docId", docId)
                .addBody(), DoctorInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    mView.setDoctorInfo((DoctorInfo) obj);
                }
            }
        });
    }


    @Override
    public void getDoctorInteractComment(String docId) {
        HttpUtil.getObjectListPage(Api.getDoctorInteractComment.mapClear()
                .addPage(10, mPageNo)
                .addMap("docId", docId)
                .addBody(), DoctorInteractComment.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    commentList.addAll((Collection<? extends DoctorInteractComment>) obj);
                    mView.setCommentList(commentList, total);
                }
            }
        });
    }

    @Override
    public void onRefresh(String docId) {
        mPageNo = 1;
        commentList.clear();
        initData(docId);
    }

    @Override
    public void loadMore(String docId) {
        mPageNo++;
        getDoctorInteractComment(docId);
    }
}
