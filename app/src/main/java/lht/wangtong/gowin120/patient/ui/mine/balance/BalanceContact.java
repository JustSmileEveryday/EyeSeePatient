package lht.wangtong.gowin120.patient.ui.mine.balance;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.BalanceInfo;

/**
 * @author luoyc
 */
public interface BalanceContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setBalance(List<BalanceInfo> balanceInfos, int total);

        void setState(boolean isShow);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void getBalance();

        void onRefresh();

        void onLoadMore();
    }

}
