package lht.wangtong.gowin120.patient.ui.home.classroom;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.CourseInfo;

/**
 * 课堂列表
 *
 * @author luoyc
 */
public interface ClassroomListContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setCourseList(List<CourseInfo> courseInfos, int total);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void loadCourses();

        void onRefresh();

        void loadMore();

    }

}
