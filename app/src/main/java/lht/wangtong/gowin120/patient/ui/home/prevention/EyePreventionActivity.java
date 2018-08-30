package lht.wangtong.gowin120.patient.ui.home.prevention;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.EyePreventionAdapter;
import lht.wangtong.gowin120.patient.adapter.EyeSignInAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.EyePreventionInfo;
import lht.wangtong.gowin120.patient.bean.EyeSignInCount;
import lht.wangtong.gowin120.patient.bean.EyeSignInInfo;

;

/**
 * 眼保健操界面
 *
 * @author luoyc
 * @date 2018/3/20
 */
@Route(path = "/home/prevention/EyePreventionActivity")
public class EyePreventionActivity extends BaseActivity<EyePreventionPresenter> implements EyePreventionContact.View {
    @BindView(R.id.punch_card_btn)
    TextView mPunchCardBtn;
    @BindView(R.id.eye_prevention_list)
    RecyclerView mEyePrevention;
    @BindView(R.id.punch_card_list)
    RecyclerView mPunchCard;
    @BindView(R.id.date_choose_btn)
    TextView mDatePickerBtn;
    @BindView(R.id.load_more_btn)
    TextView mLoadMoreBtn;
    @Inject
    EyePreventionAdapter mPreventionAdapter;
    @Inject
    EyeSignInAdapter mSignInAdapter;
    @Autowired
    String mfamilyId;
    private int pageNo = 1;
    private TimePickerView mTimePickerView;
    private List<EyeSignInInfo> eyeSignInInfos;
    private String startTime = "";
    private String endTime = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eye_prevention;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        eyeSignInInfos = new ArrayList<>();
        mEyePrevention.setLayoutManager(new LinearLayoutManager(this));
        mEyePrevention.setAdapter(mPreventionAdapter);
        mPunchCard.setLayoutManager(new LinearLayoutManager(this));
        mPunchCard.setAdapter(mSignInAdapter);
        initTimeView();
        mPresenter.initData(mfamilyId);
    }

    @Override
    public void setEyePrevention(List<EyePreventionInfo> eyePreventionInfos) {
        mPreventionAdapter.setNewData(eyePreventionInfos);
        mPreventionAdapter.notifyDataSetChanged();
        mPunchCardBtn.requestFocus();
    }

    @Override
    public void setEyeSignInCount(EyeSignInCount count) {
        mPunchCardBtn.setText("今日打卡(" + count.getCount() + "/2)");
    }

    @Override
    public void setEyeSignIn(List<EyeSignInInfo> signInInfos, int total) {
        if (total == signInInfos.size()) {
            mLoadMoreBtn.setVisibility(View.INVISIBLE);
        } else {
            mLoadMoreBtn.setVisibility(View.VISIBLE);
        }
        if (pageNo > 1) {
            eyeSignInInfos.addAll(signInInfos);
        } else {
            eyeSignInInfos.clear();
            eyeSignInInfos.addAll(signInInfos);
        }
        mSignInAdapter.setNewData(eyeSignInInfos);
        mSignInAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.load_more_btn)
    @Override
    public void loadMore() {
        pageNo++;
        mPresenter.loadEyeSignIn(mfamilyId, startTime, endTime, pageNo);
    }

    @Override
    public void initTimeView() {
        getThisYear(TimeUtils.getNowDate());
    }

    @OnClick(R.id.date_choose_btn)
    @Override
    public void showTimePicker() {
        if (mTimePickerView == null) {
            mTimePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    pageNo = 1;
                    getThisYear(date);
                }
            })
                    .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("确定")//确认按钮文字
                    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(true)//是否循环滚动
                    .setContentTextSize(18)//滚轮文字大小
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(getResources().getColor(R.color.time_text_color))//取消按钮文字颜色
                    .setLabel("年", "月", "", "", "", "")//默认设置为年月日时分秒
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .build();
            mTimePickerView.show();
        } else {
            mTimePickerView.show();
        }
    }

    @Override
    public void getThisYear(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy年MM月");
        mDatePickerBtn.setText(dateFormat1.format(date));
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.setTime(date);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startTime = dateFormat.format(mCalendar.getTime());
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        endTime = dateFormat.format(mCalendar.getTime());
        mPresenter.loadEyeSignIn(mfamilyId, startTime, endTime, pageNo);
    }

    @OnClick(R.id.punch_card_btn)
    @Override
    public void punchCard() {
        mPresenter.saveSignin(mfamilyId);
    }


}
