package lht.wangtong.gowin120.patient.ui.mine.setting.about;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.HospitalDetailInfo;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public interface AboutContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setAbout(HospitalDetailInfo detailInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadAbout();

    }

}
