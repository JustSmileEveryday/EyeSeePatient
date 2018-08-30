package lht.wangtong.gowin120.patient.ui.mine.setting.feedback;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public interface FeedbackContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void close();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void commitFeedback(String feedback, String contact);

    }

}
