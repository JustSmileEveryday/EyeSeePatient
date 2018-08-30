package lht.wangtong.gowin120.patient.ui.home.detection;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public interface EyeDetectionContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initAdapter(String [] titles, ArrayList<Fragment> fragments);


    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData(String mfamilyId);

    }
}
