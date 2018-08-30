package lht.wangtong.gowin120.patient.ui.home.surveyeye;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.EyeAgeProblemResult;

/**
 * @author luoyc
 */
public interface SurveyResultContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setResult(EyeAgeProblemResult resultInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getEyeAgeProblemResult(String eyeAgeProblemIds);

    }

}
