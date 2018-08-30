package lht.wangtong.gowin120.patient.ui.consult.doctor;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.DoctorInfo;
import lht.wangtong.gowin120.patient.bean.DoctorInteractComment;

public interface DoctorContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setDoctorInfo(DoctorInfo doctorInfo);

        void setCommentList(List<DoctorInteractComment> commentList,int total);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData(String docId);

        void getDoctorInfo(String docId);

        void getDoctorInteractComment(String docId);

        void onRefresh(String docId);

        void loadMore(String docId);

    }

}
