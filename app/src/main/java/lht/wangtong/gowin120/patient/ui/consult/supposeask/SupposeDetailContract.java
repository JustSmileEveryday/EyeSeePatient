package lht.wangtong.gowin120.patient.ui.consult.supposeask;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.IllnessQuestionInfo;

/**
 * @author luoyc
 */
public interface SupposeDetailContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setDetail(IllnessQuestionInfo detail);

    }

    interface Presenter extends BasePresenter<View> {

        void loadDetail(String illnessQuestionId);

        void saveIsUseful(String illnessQuestionId,int type);

    }

}
