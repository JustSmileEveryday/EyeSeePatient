package lht.wangtong.gowin120.patient.ui.login;

import android.content.Context;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author luoyc
 */
public interface AuthLoginContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void sendVerifycode();

        void showTime(int time);

        void login();

        void close();

        Context getThisActivity();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getVerifycode(String phone);

        void netAuthLogin(String phone, String code);

    }

}
