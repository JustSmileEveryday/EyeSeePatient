package lht.wangtong.gowin120.patient.ui.splash;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author Luoyc
 * @date 2018/3/28
 */

public interface SplashContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void getUserDB();

        void getTokenDB();

        void showGuide(int type);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

    }

}
