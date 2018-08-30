package lht.wangtong.gowin120.patient.ui.mine.info;

import android.content.Context;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.MemberInfo;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.db.User;

/**
 * @author luoyc
 * @date 2018/3/8
 */

public interface MineInfoContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setUserData(User info);

        void setAge(CommonCdInfo info);

        Context getThisContext();

        void finishThisActivity();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void showAgePicker();

        void saveHeadPic(String userPic);

        void saveMemberInfo(User memberInfo);

        void startUse(User memberInfo);
    }

}
