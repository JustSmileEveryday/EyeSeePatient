package lht.wangtong.gowin120.patient.ui.home.tryglasses;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author luoyc
 */
public interface TrySuccessContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void buySomething();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

    }
}
