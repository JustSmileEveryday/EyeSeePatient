package lht.wangtong.gowin120.patient.ui.home.detection;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.EyeHealthData;

/**
 * @author luoyc
 * @date 2018/3/20
 */

public interface DetectionCompleteContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initData();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void saveEyeHealthData(EyeHealthData healthData);

    }

}
