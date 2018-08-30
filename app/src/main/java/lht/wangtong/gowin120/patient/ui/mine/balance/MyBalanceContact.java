package lht.wangtong.gowin120.patient.ui.mine.balance;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author luoyc
 */
public interface MyBalanceContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setBalance(String balance);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getMemberInfo();

    }

}
