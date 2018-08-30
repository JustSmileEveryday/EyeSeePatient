package lht.wangtong.gowin120.patient.ui.mine.balance;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.AccountIoInfo;

/**
 * @author luoyc
 */
public interface BalanceDetailContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setDetail(AccountIoInfo accountIoInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBalanceDetail(String memberAccountIoId);

    }

}
