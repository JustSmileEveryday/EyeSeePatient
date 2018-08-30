package lht.wangtong.gowin120.patient.ui.home.detection;

import android.app.Activity;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.StepBean;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public interface AchromatopsiaContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initSetpView(List<StepBean> stepBeans);

        void setStepView(int positon);

        void checkData();

        void notSeeData();

        Activity getThisContext();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSteps();

        void loadStepsDetail(StepBean stepBean, int drawalbe, List<String> strings, String rightText);

        void saveEyeHealthData(List<StepBean> stepBeans,String mfamilyId);

    }

}
