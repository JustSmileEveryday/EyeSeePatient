package lht.wangtong.gowin120.patient.ui.login;

import android.content.Context;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 *
 * @author luoyc
 * @date 2018/3/12
 */

public interface LoginContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void login();

        void forgetPassword();

        void authLogin();

        void sightless();

        void setEidtSelection();

        void register();

        void close();

        void agreement();

        Context getThisActivity();

    }

    interface Presenter extends BasePresenter<View> {

        void netLogin(String userName, String password);

    }

}
