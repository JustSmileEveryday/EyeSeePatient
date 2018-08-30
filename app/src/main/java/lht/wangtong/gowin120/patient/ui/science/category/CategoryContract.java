package lht.wangtong.gowin120.patient.ui.science.category;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;

/**
 *
 * @author luoyc
 * @date 2018/3/6
 */

public interface CategoryContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setScienceArticle(List<ScienceCategoryInfo> categoryInfos, int total);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void loadScienceArticle(String catalogId);

        void refresh(String catalogId);

        void loadMore(String catalogId);

    }

}
