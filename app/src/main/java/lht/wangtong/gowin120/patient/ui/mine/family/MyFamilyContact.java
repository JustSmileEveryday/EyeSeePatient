package lht.wangtong.gowin120.patient.ui.mine.family;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public interface MyFamilyContact extends BaseContract {

    interface View extends BaseContract.BaseView {
        void setFamilyLits(List<HomeFamilyInfo> infos);

        void setState(boolean isShow);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadFamilyLits();

        void initData();
    }

}
