package lht.wangtong.gowin120.patient.ui.mine.setting.agreement;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.HomeActivityInfo;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public interface AgreementContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setData(int type, String url, HomeActivityInfo homeActivityInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData(int type,String activityId);

        void getActivityInfo(String activityId);

    }

}
