package lht.wangtong.gowin120.patient.ui.mine.collection;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;


/**
 *
 * @author luoyc
 * @date 2018/3/14
 */

public interface MyCollectionContact extends BaseContract {

    interface View extends BaseContract.BaseView{

        void setCollectionLits(List<ScienceCategoryInfo> infos, int total);

        void setState(boolean isShow);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void loadCollectionLits();

        void initData();

        void loadMore();

        void onRefresh();

    }

}
