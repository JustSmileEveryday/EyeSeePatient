package lht.wangtong.gowin120.patient.ui.home.prevention;

import java.util.Date;
import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.EyePreventionInfo;
import lht.wangtong.gowin120.patient.bean.EyeSignInCount;
import lht.wangtong.gowin120.patient.bean.EyeSignInInfo;

/**
 * @author luoyc
 * @date 2018/3/20
 */

public interface EyePreventionContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setEyePrevention(List<EyePreventionInfo> eyePreventionInfos);

        void setEyeSignInCount(EyeSignInCount count);

        void setEyeSignIn(List<EyeSignInInfo> signInInfos,int total);

        void loadMore();

        void initTimeView();

        void showTimePicker();

        void getThisYear(Date date);

        void punchCard();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData(String familyId);

        void loadEyeSignInCount(String familyId);

        void loadEyePrevention();

        void saveSignin(String familyId);

        void loadEyeSignIn(String familyId, String signinDateStart, String signinDateEnd, int pageNo);
    }

}
