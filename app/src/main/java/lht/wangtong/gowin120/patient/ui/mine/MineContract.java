package lht.wangtong.gowin120.patient.ui.mine;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.db.User;

/**
 * Created by luoyc on 2018/3/5.
 */

public interface MineContract {

    interface View extends BaseContract.BaseView {

        void initHeadView();

        void setUserData(User memberInfo);

        void showGuide();

        void setCanGetCoupon(int count);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getMemberInfo();

        void getCanGetCouponList();
    }
}
