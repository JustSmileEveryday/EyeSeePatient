package lht.wangtong.gowin120.patient.ui.mine.family;

import android.app.Activity;
import android.content.Context;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;

/**
 * @author luoyc
 * @date 2018/3/15
 */

public interface FamilyInfoContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setUserData();

        void setAge(CommonCdInfo info);

        Activity getThisContext();

        void setRelationship(CommonCdInfo info);

        void changeFinsh();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void showAgePicker();

        void showRelationPicker();

        void saveInfo(HomeFamilyInfo familyInfo);

        void deleteFamily(String familyId);

    }

}
