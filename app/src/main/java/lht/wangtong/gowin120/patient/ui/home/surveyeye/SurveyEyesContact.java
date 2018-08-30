package lht.wangtong.gowin120.patient.ui.home.surveyeye;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.EyeAgeProblemInfo;

/**
 * @author luoyc
 */
public interface SurveyEyesContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setEyeAgeProblem(List<EyeAgeProblemInfo> eyeAgeProblemInfoList);

        void showAge();

        void setProblem();

    }

    interface Presenter extends BasePresenter<View> {

        void getEyeAgeProblem(String crowdAgeGroup);

    }

}
