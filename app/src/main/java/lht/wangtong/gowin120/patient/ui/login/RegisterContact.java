package lht.wangtong.gowin120.patient.ui.login;

import android.content.Context;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * Created by luoyc on 2018/3/12.
 */

public interface RegisterContact extends BaseContract {

    interface View extends BaseContract.BaseView {
        void register();

        void sightless();

        void setEidtSelection();

        void sendVerifycode();

        void showTime(int time);

        void close();

        Context getThisActivity();

        void agreement();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void netRegister(String userName, String password, String verifycode);

        void getVerifycode(String userName);

        void netLogin(String userName, String password);
    }

}
