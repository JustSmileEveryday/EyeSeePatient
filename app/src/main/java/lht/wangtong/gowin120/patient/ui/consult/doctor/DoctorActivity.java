package lht.wangtong.gowin120.patient.ui.consult.doctor;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.CommentAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.DoctorInfo;
import lht.wangtong.gowin120.patient.bean.DoctorInteractComment;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.view.CustomLoadMoreView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 医生主页
 *
 * @author luoyc
 */

@Route(path = "/consult/doctor/DoctorActivity")
public class DoctorActivity extends BaseActivity<DoctorPresenter> implements DoctorContract.View, SwipeRefreshLayout.OnRefreshListener
        , CommentAdapter.RequestLoadMoreListener {

    @BindView(R.id.comment_list)
    RecyclerView mCommentRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Autowired
    String doctorId;
    @Inject
    CommentAdapter mAdapter;
    private CircleImageView mDoctorHead;
    private TextView mDoctorName;
    private TextView mDoctorJob;
    private TextView mDoctorDeptName;
    private TextView mDoctorCompanyName;
    private TextView mDoctorEvaluateNum;
    private TextView mDoctorGootAt;
    private TagFlowLayout mTag;
    private TagAdapter<DoctorInfo.EmployeeKeywordListBean> mTagAdapter;
    private List<DoctorInfo.EmployeeKeywordListBean> keywordListBeans;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        View headerView = getLayoutInflater().inflate(R.layout.doctor_header_view_layout, null);
        mDoctorHead = headerView.findViewById(R.id.doctor_head);
        mDoctorName = headerView.findViewById(R.id.doctor_name);
        mDoctorJob = headerView.findViewById(R.id.doctor_job);
        mDoctorDeptName = headerView.findViewById(R.id.hospital_department);
        mDoctorCompanyName = headerView.findViewById(R.id.hospital_name);
        mDoctorEvaluateNum = headerView.findViewById(R.id.comment_num);
        mDoctorGootAt = headerView.findViewById(R.id.good_at);
        mTag = headerView.findViewById(R.id.tag);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, mCommentRecyclerView);
        mAdapter.addHeaderView(headerView);
        mCommentRecyclerView.setAdapter(mAdapter);
        initTagAdapter();
        mPresenter.initData(doctorId);
    }

    private void initTagAdapter() {
        keywordListBeans = new ArrayList<>();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mTagAdapter = new TagAdapter<DoctorInfo.EmployeeKeywordListBean>(keywordListBeans) {
            @Override
            public View getView(FlowLayout parent, int position, DoctorInfo.EmployeeKeywordListBean s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.doctor_tag_layout,
                        mTag, false);
                tv.setText(s.getKeyword());
                return tv;
            }
        };
        mTag.setAdapter(mTagAdapter);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh(doctorId);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore(doctorId);
    }

    @Override
    public void setDoctorInfo(DoctorInfo doctorInfo) {
        if (keywordListBeans.size() > 0) {
            keywordListBeans.clear();
        }
        Glide.with(this)
                .load(Api.HOST_IMG + doctorInfo.getPicUrl())
                .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                .transition(withCrossFade())
                .into(mDoctorHead);
        mDoctorName.setText(doctorInfo.getDocName());
        mDoctorJob.setText(doctorInfo.getJobTitle());
        mDoctorGootAt.setText("擅长:" + doctorInfo.getGoodAt());
        mDoctorCompanyName.setText(doctorInfo.getCompanyName());
        mDoctorDeptName.setText(doctorInfo.getDeptName());
        keywordListBeans.addAll(doctorInfo.getEmployeeKeywordList());
        mTagAdapter.notifyDataChanged();
    }

    @Override
    public void setCommentList(List<DoctorInteractComment> commentList, int total) {
        mDoctorEvaluateNum.setText(total + "");
        if (commentList.size() > 0) {
            mAdapter.setNewData(commentList);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (total == commentList.size()) {
                //已经完全加载
                mAdapter.loadMoreEnd();
                mAdapter.setEnableLoadMore(false);
            } else {
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
            }
        }
    }
}
