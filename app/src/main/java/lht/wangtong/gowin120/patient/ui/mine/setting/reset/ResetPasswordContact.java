package lht.wangtong.gowin120.patient.ui.mine.setting.reset;

import android.app.Activity;
import android.content.Context;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 *
 * @author luoyc
 * @date 2018/3/26
 */

public interface ResetPasswordContact extends BaseContract {

    interface View extends BaseContract.BaseView{

        void reset();

        void sightless();

        void setEidtSelection();

        void sendVerifycode();

        void showTime(int time);

        Activity getThisContext();

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void netResetPassword(String userName, String password, String verifycode);

        void getVerifycode(String userName);

    }

}
