package lht.wangtong.gowin120.patient.ui.home.classroom;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * 课堂详情
 *
 * @author luoyc
 */
public class ClassroomListPresenter extends BasePresenter<ClassroomListContract.View> implements ClassroomListContract.Presenter {
    private int pageNo = 1;
    private List<CourseInfo> courseInfos;

    @Inject
    public ClassroomListPresenter() {
    }


    @Override
    public void initData() {
        courseInfos = new ArrayList<>();
        loadCourses();
    }

    @Override
    public void loadCourses() {
        HttpUtil.getObjectListPage(Api.getHealthNewsByCatalog.mapClear()
                        .addPage(10, pageNo)
                        .addMap("catalogCode", "XAKT")
                        .addBody()
                , CourseInfo.class, new HttpUtil.objectListPageCallback() {
                    @Override
                    public void resultPage(boolean b, @Nullable Object obj, int total) {
                        if (b) {
                            courseInfos.addAll(obj != null ? (Collection<? extends CourseInfo>) obj : null);
                            mView.setCourseList(courseInfos, total);
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        courseInfos.clear();
        pageNo = 1;
        loadCourses();
    }

    @Override
    public void loadMore() {
        pageNo++;
        loadCourses();
    }
}
