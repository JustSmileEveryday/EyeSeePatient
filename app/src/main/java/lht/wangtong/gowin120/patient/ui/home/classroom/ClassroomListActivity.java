package lht.wangtong.gowin120.patient.ui.home.classroom;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.CourseListAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.CourseInfo;


/**
 * 课堂详情
 *
 * @author luoyc
 */

@Route(path = "/home/classroom/ClassroomListActivity")
public class ClassroomListActivity extends BaseActivity<ClassroomListPresenter> implements ClassroomListContract.View,
        SwipeRefreshLayout.OnRefreshListener, CourseListAdapter.RequestLoadMoreListener, CourseListAdapter.OnItemClickListener {

    @BindView(R.id.course_list)
    RecyclerView mCourseRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    CourseListAdapter mCourseListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classroom_list;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCourseListAdapter.setOnItemClickListener(this);
        mCourseListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mCourseRecyclerView.setAdapter(mCourseListAdapter);
        mPresenter.initData();
    }


    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void setCourseList(List<CourseInfo> courseInfos, int total) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (courseInfos.size() > 0) {
            courseInfos.get(0).setOne(true);
        }
        mCourseListAdapter.setNewData(courseInfos);
        if (total == courseInfos.size()) {
            //已经完全加载
            mCourseListAdapter.loadMoreEnd();
            mCourseListAdapter.setEnableLoadMore(false);
        } else {
            mCourseListAdapter.setEnableLoadMore(true);
            mCourseListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance()
                .build("/home/classroom/CourseDetailActivity")
                .withString("mArticleId", ((CourseInfo) adapter.getData().get(position)).getArticleId())
                .navigation();
    }
}
