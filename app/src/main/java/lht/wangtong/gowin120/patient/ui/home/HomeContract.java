package lht.wangtong.gowin120.patient.ui.home;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.CourseInfo;

/**
 * Created by luoyc on 2018/3/5.
 */

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void initBanner();

        void setHomeBanners(List<BannerInfo> banners);

        void initOnClick();

        void oneCall();

        void setTodaySignStatus(int num);

        void oneCallSuccess();

        void setCourseList(List<CourseInfo> courseInfos, int total);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void getTodaySignStatus();

        void loadHomeBanners();

        void refresh();

        void loadMore();

        void saveServiceRecord();

        void loadCourse();
    }
}
