package lht.wangtong.gowin120.patient.ui.home.data;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.ServiceDataAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.EyeDataInfo;
import lht.wangtong.gowin120.patient.bean.LatestEyeData;
import lht.wangtong.gowin120.patient.bean.ServiceDataInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDetailType;

/**
 * 眼健康数据 页面
 *
 * @author luoyc
 * @date 2018/3/22
 */
@Route(path = "/home/data/EyeHealthDataActivity")
public class EyeHealthDataActivity extends BaseActivity<EyeHealthDataPresenter> implements EyeHealthDataContact.View {
    @BindView(R.id.detection_tab)
    CommonTabLayout mDetectionTab;
    @BindView(R.id.eye_tab)
    CommonTabLayout mEyeTab;
    @BindView(R.id.service_tab)
    TabLayout mServiceTab;
    @BindView(R.id.service_list)
    RecyclerView mServiceRecyclerView;
    @BindView(R.id.latest_detection_time)
    TextView mLatestDetectionTime;
    @BindView(R.id.right_vision)
    TextView mRightVision;
    @BindView(R.id.left_vision)
    TextView mLeftVision;
    @BindView(R.id.achromatopsia_probability)
    TextView mAchromatopsia;
    @BindView(R.id.is_astigmatism)
    TextView mAstigmatism;
    @BindView(R.id.is_macular)
    TextView mMacular;
    @BindView(R.id.latest_service_time)
    TextView mLatestServiceTime;
    @BindView(R.id.detection_start_btn)
    TextView mDetectionStartTime;
    @BindView(R.id.detection_end_btn)
    TextView mDetectionEndTime;
    @BindView(R.id.service_start_btn)
    TextView mServiceStartTime;
    @BindView(R.id.service_end_btn)
    TextView mServiceEndTime;
    @BindView(R.id.detection_chart)
    LineChartView mDetectionChart;
    @BindView(R.id.detection_sign_text)
    TextView mDetectionSign;
    @Inject
    ServiceDataAdapter mDataAdapter;
    @Autowired
    String mfamilyId;
    private String reservationServiceDateStart = "";
    private String reservationServiceDateEnd = "";
    private String testDateStart = "";
    private String testDateEnd = "";
    private TimePickerView mTimePickerView;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateFormat1;
    private boolean isReLoadDate = false;
    private int dataType = 1;
    private int mCurrentItem = 0;
    private List<ServiceDetailType> mServiceDetailTypes;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eye_health_data;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void initView() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat1 = new SimpleDateFormat("yyyy年MM月dd日");
        mServiceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mServiceRecyclerView.setAdapter(mDataAdapter);
        setInitTime(TimeUtils.getNowDate());
        mPresenter.initData();
        mPresenter.loadLatestEyeData(mfamilyId);
        mPresenter.loadEyeHealthData(1, testDateStart, testDateEnd, mfamilyId);
    }

    @Override
    public void initChart(List<AxisValue> mAxisValues, List<PointValue> mPointValues) {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#01C1B4"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setFilled(true);//是否填充曲线的面积
        line.setStrokeWidth(2);//线粗细
        line.setPointRadius(4);//坐标点大小
//      line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setTextColor(Color.parseColor("#C9C9C9"));  //设置字体颜色
//        axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(12);//设置字体大小
        axisX.setMaxLabelChars(8);  //最多几个X轴坐标
        axisX.setAutoGenerated(true);
        axisX.setValues(mAxisValues);  //填充X轴的坐标名称
        axisX.setHasTiltedLabels(false);//坐标轴文字是否倾斜(默认为false,不倾斜)
        axisX.setMaxLabelChars(0);//最大间隔
        data.setAxisXBottom(axisX); //x 轴在底部
//      data.setAxisXTop(axisX);  //x 轴在顶部

        Axis axisY = new Axis();  //Y轴
        axisY.setMaxLabelChars(7); //默认是3，只能看最后三个数字
//        if (dataType == 1 || dataType == 2) {
//            axisY.setName("视力");//y轴标注
//        } else {
//            axisY.setName("概率单位(%)");//y轴标注
//        }
        axisY.setTextSize(12);//设置字体大小
        axisY.setHasLines(true);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
//      data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        mDetectionChart.setInteractive(true);
        mDetectionChart.setZoomType(ZoomType.HORIZONTAL);
        mDetectionChart.setMaxZoom((float) 2);//最大方法比例
        mDetectionChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mDetectionChart.setLineChartData(data);
        if (dataType != 1 && dataType != 2) {
            final Viewport v = new Viewport(mDetectionChart.getMaximumViewport());
            v.top = 100; //max value
            v.bottom = 0;  //min value
            v.left = mAxisValues.get(0).getValue();
            mDetectionChart.setMaximumViewport(v);
            mDetectionChart.setCurrentViewport(v);
        } else {
            final Viewport v = new Viewport(mDetectionChart.getMaximumViewport());
            v.top = 5.1f; //max value
            v.bottom = 4.0f;  //min value
            v.left = mAxisValues.get(0).getValue();
            mDetectionChart.setMaximumViewport(v);
            mDetectionChart.setCurrentViewport(v);
        }
        mDetectionChart.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDetectionTab(ArrayList<CustomTabEntity> detectionTab) {
        mDetectionTab.setTabData(detectionTab);
        mDetectionTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        mEyeTab.setVisibility(View.VISIBLE);
                        dataType = 1;
                        mCurrentItem = 0;
                        mPresenter.loadEyeHealthData(1, testDateStart, testDateEnd, mfamilyId);
                        break;
                    case 1:
                        mEyeTab.setVisibility(View.GONE);
                        dataType = 3;
                        mCurrentItem = 1;
                        mPresenter.loadEyeHealthData(3, testDateStart, testDateEnd, mfamilyId);
                        break;
                    case 2:
                        mEyeTab.setVisibility(View.GONE);
                        dataType = 4;
                        mPresenter.loadEyeHealthData(4, testDateStart, testDateEnd, mfamilyId);
                        break;
                    case 3:
                        mEyeTab.setVisibility(View.GONE);
                        dataType = 5;
                        mPresenter.loadEyeHealthData(5, testDateStart, testDateEnd, mfamilyId);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void setEyeTab(ArrayList<CustomTabEntity> eyeTab) {
        mEyeTab.setTabData(eyeTab);
        mEyeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        dataType = 1;
                        mPresenter.loadEyeHealthData(1, testDateStart, testDateEnd, mfamilyId);
                        break;
                    case 1:
                        dataType = 2;
                        mPresenter.loadEyeHealthData(2, testDateStart, testDateEnd, mfamilyId);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void setServiceTab(ArrayList<CustomTabEntity> serviceTab, final List<ServiceDetailType> serviceDetailTypes) {
        for (int i = 0; i < serviceTab.size(); i++) {
            mServiceTab.addTab(mServiceTab.newTab().setText(serviceTab.get(i).getTabTitle()));
        }
        mServiceDetailTypes = new ArrayList<>();
        mServiceDetailTypes.addAll(serviceDetailTypes);
        if (mServiceDetailTypes.size() > 0) {
            mPresenter.loadServiceData(serviceDetailTypes.get(0).getReservationTypeId(), reservationServiceDateStart, reservationServiceDateEnd, mfamilyId);
        }
        mServiceTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPresenter.loadServiceData(serviceDetailTypes.get(tab.getPosition()).getReservationTypeId(), reservationServiceDateStart, reservationServiceDateEnd, mfamilyId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void setEyeHealthData(List<EyeDataInfo> dataList) {
        if (dataList.size() == 0) {
            mDetectionChart.setVisibility(View.GONE);
            mDetectionSign.setVisibility(View.VISIBLE);
            switch (dataType) {
                case 1:
                    mDetectionSign.setText("还没做过视力测试？点我开始测试吧");
                    break;
                case 2:
                    mDetectionSign.setText("还没做过视力测试？点我开始测试吧");
                    break;
                case 3:
                    mDetectionSign.setText("还没做过色盲测试？点我开始测试吧");
                    break;
                case 4:
                    mDetectionSign.setText("还没做过散光测试？点我开始测试吧");
                    break;
                case 5:
                    mDetectionSign.setText("还没做过黄斑测试？点我开始测试吧");
                    break;
                default:
                    break;
            }
        } else {
            mDetectionSign.setVisibility(View.GONE);
            List<AxisValue> axisValues = new ArrayList<>();
            List<PointValue> pointValues = new ArrayList<>();
            if (dataList.size() > 1) {
                for (int i = 0; i < dataList.size(); i++) {
                    axisValues.add(new AxisValue(i).setLabel(dataList.get(i).getTestDate()));
                    if (dataType != 1 && dataType != 2) {
                        pointValues.add(new PointValue(i, Float.valueOf(dataList.get(i).getDataValue()) * 100));
                    } else {
                        pointValues.add(new PointValue(i, Float.valueOf(dataList.get(i).getDataValue())));
                    }
                }
            } else {
                axisValues.add(new AxisValue(0).setLabel("0"));
                if (dataType != 1 && dataType != 2) {
                    pointValues.add(new PointValue(0, 0f));
                } else {
                    pointValues.add(new PointValue(0, 4.0f));
                }
                for (int i = 0; i < dataList.size(); i++) {
                    axisValues.add(new AxisValue(1).setLabel(dataList.get(i).getTestDate()));
                    if (dataType != 1 && dataType != 2) {
                        pointValues.add(new PointValue(1, Float.valueOf(dataList.get(i).getDataValue()) * 100));
                    } else {
                        pointValues.add(new PointValue(1, Float.valueOf(dataList.get(i).getDataValue())));
                    }
                }
            }
            initChart(axisValues, pointValues);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setServiceData(List<ServiceDataInfo> infos, String reservationTypeId) {
        if (mServiceDetailTypes != null && mServiceDetailTypes.size() > 0) {
            for (int i = 0; i < mServiceDetailTypes.size(); i++) {
                if (TextUtils.equals(reservationTypeId, mServiceDetailTypes.get(i).getReservationTypeId())) {
                    //判断当前点击的服务
                    if (infos.size() == 0) {
                        mLatestServiceTime.setText("还没做过" + mServiceDetailTypes.get(i).getName() + "？赶紧去预约体验吧");
                    } else {
                        String day = TimeUtils.getFitTimeSpan(TimeUtils.getNowString(dateFormat), infos.get(0).getReservationServiceDate(), dateFormat, 1);
                        mLatestServiceTime.setText("距离上一次" + mServiceDetailTypes.get(i).getName() + "已有" + day);
                    }
                }
            }
        }
        mDataAdapter.setNewData(infos);
    }

    @Override
    public void setLatestEyeData(LatestEyeData eyeData) {
        mLatestDetectionTime.setText("测试时间：" + eyeData.getTestDate());
        DecimalFormat df = new DecimalFormat("0%");
        mRightVision.setText(eyeData.getYysl());
        mLeftVision.setText(eyeData.getZysl());
        if (eyeData.getSmgl() != null) {
            mAchromatopsia.setText(df.format(Float.valueOf(eyeData.getSmgl())));
        }
        mAstigmatism.setText(eyeData.getSfsg());
        mMacular.setText(eyeData.getSfhb());
    }


    @Override
    public void setInitTime(Date date) {
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.setTime(date);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        reservationServiceDateStart = dateFormat.format(mCalendar.getTime());
        testDateStart = dateFormat.format(mCalendar.getTime());
        mDetectionStartTime.setText(dateFormat1.format(mCalendar.getTime()));
        mServiceStartTime.setText(dateFormat1.format(mCalendar.getTime()));
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        reservationServiceDateEnd = dateFormat.format(mCalendar.getTime());
        testDateEnd = dateFormat.format(mCalendar.getTime());
        mDetectionEndTime.setText(dateFormat1.format(mCalendar.getTime()));
        mServiceEndTime.setText(dateFormat1.format(mCalendar.getTime()));
    }

    @Override
    public void showTimePicker(final int type) {
        if (mTimePickerView == null) {
            mTimePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    //选中事件回调
                    switch (type) {
                        case 1:
                            //检测时间开始
                            setTestDateStart(date);
                            break;
                        case 2:
                            //检测时间结束
                            setTestDateEnd(date);
                            break;
                        case 3:
                            //预约服务时间开始
                            setServiceDateStart(date);
                            break;
                        case 4:
                            //预约服务时间结束
                            setServiceDateEnd(date);
                            break;
                        default:
                            break;
                    }
                }
            })
                    .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("确定")//确认按钮文字
                    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(true)//是否循环滚动
                    .setContentTextSize(18)////滚轮文字大小
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(getResources().getColor(R.color.time_text_color))//取消按钮文字颜色
                    .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .build();
            mTimePickerView.show();
        } else {
            mTimePickerView.show();
        }
    }

    @Override
    public void setTestDateStart(Date date) {
        testDateStart = dateFormat.format(date);
        mDetectionStartTime.setText(dateFormat1.format(date));
        mPresenter.loadEyeHealthData(dataType, testDateStart, testDateEnd, mfamilyId);
    }

    @Override
    public void setTestDateEnd(Date date) {
        testDateEnd = dateFormat.format(date);
        mDetectionEndTime.setText(dateFormat1.format(date));
        mPresenter.loadEyeHealthData(dataType, testDateStart, testDateEnd, mfamilyId);
    }

    @Override
    public void setServiceDateStart(Date date) {
        isReLoadDate = true;
        reservationServiceDateStart = dateFormat.format(date);
        mServiceStartTime.setText(dateFormat1.format(date));
        mPresenter.loadServiceData(mServiceDetailTypes.get(mServiceTab.getSelectedTabPosition()).getReservationTypeId(), reservationServiceDateStart, reservationServiceDateEnd, mfamilyId);
    }

    @Override
    public void setServiceDateEnd(Date date) {
        isReLoadDate = true;
        reservationServiceDateEnd = dateFormat.format(date);
        mServiceEndTime.setText(dateFormat1.format(date));
        mPresenter.loadServiceData(mServiceDetailTypes.get(mServiceTab.getSelectedTabPosition()).getReservationTypeId(), reservationServiceDateStart, reservationServiceDateEnd, mfamilyId);
    }

    @OnClick({R.id.consulting, R.id.start_detection_btn, R.id.appointment_service_btn, R.id.detection_start_btn, R.id.detection_end_btn, R.id.service_start_btn, R.id.service_end_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.consulting:
                //立即咨询
                break;
            case R.id.start_detection_btn:
                //开始测试
                ARouter.getInstance().build("/home/detection/EyeDetectionActivity")
                        .withInt("mCurrentItem", mCurrentItem)
                        .withString("mfamilyId", mfamilyId)
                        .navigation();
                break;
            case R.id.appointment_service_btn:
                //预约服务
                ARouter.getInstance().build("/home/appointment/AppointmentServiceActivity").navigation();
                break;
            case R.id.detection_start_btn:
                //检测时间开始
                showTimePicker(1);
                break;
            case R.id.detection_end_btn:
                //检测时间结束
                showTimePicker(2);
                break;
            case R.id.service_start_btn:
                //预约服务时间开始
                showTimePicker(3);
                break;
            case R.id.service_end_btn:
                //预约服务时间结束
                showTimePicker(4);
                break;
            default:
                break;
        }
    }
}
