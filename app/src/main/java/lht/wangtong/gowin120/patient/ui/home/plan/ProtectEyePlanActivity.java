package lht.wangtong.gowin120.patient.ui.home.plan;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.EyePlanAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.PlanScheduleInfo;

/**
 * 儿童护眼计划页面
 *
 * @author luoyc
 * @date 2018/3/21
 */
@Route(path = "/home/plan/ProtectEyePlanActivity")
public class ProtectEyePlanActivity extends BaseActivity<ProtectEyePlanPresenter> implements ProtectEyePlanContact.View, EyePlanAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener {
    private final int ADD_PLAN = 101;
    @BindView(R.id.plan_list)
    RecyclerView mPlanRecyclerView;
    @Inject
    EyePlanAdapter mAdapter;
    @Autowired
    String mfamilyId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_protect_eye_plan;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPlanRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mPresenter.loadPlanList(mfamilyId);
    }

    @OnClick(R.id.add_plan_btn)
    public void addPlan() {
        ARouter.getInstance().build("/home/plan/AddPlanActivity")
                .withString("mfamilyId", mfamilyId)
                .navigation(this, ADD_PLAN);
    }

    @Override
    public void setPlanList(List<PlanScheduleInfo> scheduleInfos) {
        mAdapter.setNewData(scheduleInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.swich_btn:
                mPresenter.changePlanScheduleStatus(((PlanScheduleInfo) adapter.getData().get(position)).getPlanScheduleId());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ADD_PLAN:
                boolean isRefresh = data.getBooleanExtra("isRefresh", false);
                if (isRefresh) {
                    mPresenter.loadPlanList(mfamilyId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance().build("/home/plan/AddPlanActivity")
                .withString("mfamilyId", mfamilyId)
                .withString("mPlanScheduleId",((PlanScheduleInfo) adapter.getData().get(position)).getPlanScheduleId())
                .navigation(this, ADD_PLAN);
    }
}
