package lht.wangtong.gowin120.patient.ui.home.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.WeekAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.PlanScheduleDetailInfo;
import lht.wangtong.gowin120.patient.bean.WeekInfo;
import lht.wangtong.gowin120.patient.view.OrdinaryTitleBar;

/**
 * 添加计划页面
 *
 * @author luoyc
 * @date 2018/3/22
 */
@Route(path = "/home/plan/AddPlanActivity")
public class AddPlanActivity extends BaseActivity<AddPlanPresenter> implements AddPlanContact.View, WeekAdapter.OnItemClickListener {

    @BindView(R.id.title)
    OrdinaryTitleBar titleBar;
    @BindView(R.id.week)
    RecyclerView mWeekRecyclerView;
    @BindView(R.id.add_plan_btn)
    TextView mAddPlanBtn;
    @BindView(R.id.plan_time)
    TextView mPlanTime;
    @BindView(R.id.plan_name)
    EditText mPlanName;
    @Inject
    WeekAdapter mWeekAdapter;
    @Autowired
    String mfamilyId;
    @Autowired
    String mPlanScheduleId;
    private TimePickerView mTimePickerView;
    private String remindDate = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_plan;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mWeekRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWeekRecyclerView.setAdapter(mWeekAdapter);
        mWeekAdapter.setOnItemClickListener(this);
        if (mPlanScheduleId != null) {
            titleBar.setTitle("编辑计划");
            mAddPlanBtn.setText("保存计划");
        }
        mPresenter.initData(mPlanScheduleId);
    }

    @OnClick({R.id.next_btn, R.id.add_plan_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                showTimePicker();
                break;
            case R.id.add_plan_btn:
                String[] remindValue = new String[]{"N", "N", "N", "N", "N", "N", "N"};
                for (int i = 0; i < mWeekAdapter.getData().size(); i++) {
                    if (mWeekAdapter.getData().get(i).isChoosed()) {
                        remindValue[i] = "Y";
                    }
                }
                mPresenter.updatePlanSchedule(mPlanScheduleId, mfamilyId, mPlanName.getText().toString(), remindDate, remindValue);
                break;
            default:
                break;
        }
    }

    @Override
    public void setWeekList(List<WeekInfo> weekInfos) {
        mWeekAdapter.setNewData(weekInfos);
        mWeekAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTimePicker() {
        KeyboardUtils.hideSoftInput(this);
        if (mTimePickerView == null) {
            mTimePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    //选中事件回调
                    getTime(date);
                }
            })
                    .setType(new boolean[]{false, false, false, true, true, false})// 默认全部显示
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("确定")//确认按钮文字
                    .setContentTextSize(18)
                    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(true)//是否循环滚动
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(getResources().getColor(R.color.time_text_color))//取消按钮文字颜色
                    .setLabel("", "", "", "时", "分", "")//默认设置为年月日时分秒
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .build();
            mTimePickerView.show();
        } else {
            mTimePickerView.show();
        }
    }

    @Override
    public void getTime(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        remindDate = dateFormat.format(date);
        mPlanTime.setText(remindDate);
    }

    @Override
    public void finishActivity() {
        Intent data = new Intent();
        data.putExtra("isRefresh", true);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void setPlanInfo(List<WeekInfo> weekInfos, PlanScheduleDetailInfo detailInfo) {
        mPlanName.setText(detailInfo.getName());
        mPlanName.setSelection(detailInfo.getName().length());
        mPlanTime.setText(detailInfo.getRemindDate());
        mWeekAdapter.setNewData(weekInfos);
        mWeekAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (((WeekInfo) adapter.getData().get(position)).isChoosed()) {
            ((WeekInfo) adapter.getData().get(position)).setChoosed(false);
        } else {
            ((WeekInfo) adapter.getData().get(position)).setChoosed(true);
        }
        adapter.notifyDataSetChanged();
    }
}
