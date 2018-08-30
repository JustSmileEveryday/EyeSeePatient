package lht.wangtong.gowin120.patient.ui.consult;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;

/**
 * Created by luoyc on 2018/3/5.
 */

public interface ConsultContract {

    interface View extends BaseContract.BaseView {

        void setTitles(List<String> titles);

    }


    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadTitles();

    }
}
