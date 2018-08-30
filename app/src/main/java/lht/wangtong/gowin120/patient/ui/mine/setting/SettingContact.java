package lht.wangtong.gowin120.patient.ui.mine.setting;

import android.content.Context;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public interface SettingContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setCookies();

        void close();

        Context getThisContext();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loginOut();

        void wipeCache();

    }

}
