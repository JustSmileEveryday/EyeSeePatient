package lht.wangtong.gowin120.patient.ui.home.detection;

import android.app.Activity;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.StepBean;

public interface MacularContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initSetpView(List<StepBean> stepBeans);

        void setStepView(int positon);

        void checkData(boolean isRight);

        Activity getThisContext();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadSteps();

        void saveEyeHealthData(List<StepBean> stepBeans, String mfamilyId);
    }
}
