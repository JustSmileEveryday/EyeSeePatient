package lht.wangtong.gowin120.patient.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.MemberInfo;
import lht.wangtong.gowin120.patient.bean.SignDate;
import lht.wangtong.gowin120.patient.bean.event.SignEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.widget.SimpleSignMonthView;


/**
 * 签到
 *
 * @author luoyc
 */
public class SignDialog extends BaseDialog implements View.OnClickListener, CalendarView.OnMonthChangeListener
        , CalendarView.OnDateSelectedListener {

    private TextView mIntegral;
    private TextView mSignDay;
    private CalendarView mCalendarView;
    private TextView mMonthView;
    private List<SignDate> signDates;
    //今天是否签到
    private boolean isSign = false;
    //已经签到天数集合
    private Map<String, Calendar> mSchemeMap;
    private String mSigninDateStart, mSigninDateEnd;

    public SignDialog(@NonNull Context context) {
        super(context);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sign_layout, null);
        mIntegral = mView.findViewById(R.id.mine_integral);
        mSignDay = mView.findViewById(R.id.mine_sign_day);
        mCalendarView = mView.findViewById(R.id.calendarView);
        mMonthView = mView.findViewById(R.id.month_text);
        ImageView mNextBtn = mView.findViewById(R.id.next_btn);
        ImageView mUpBtn = mView.findViewById(R.id.up_btn);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnDateSelectedListener(this);
        mUpBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        setContentView(mView);
        initData();
    }

    private void initData() {
        signDates = new ArrayList<>();
        mSchemeMap = new HashMap<>();
        mMonthView.setText(mCalendarView.getCurYear() + "年" + mCalendarView.getCurMonth() + "月");
        getMemberInfo();
        getSupportBeginDayofMonth(mCalendarView.getCurYear(), mCalendarView.getCurMonth());
        getSigninList(mSigninDateStart, mSigninDateEnd);
    }

    @Override
    public void show() {
        super.show();
        setGravity(Gravity.CENTER_VERTICAL);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                mCalendarView.scrollToNext();
                break;
            case R.id.up_btn:
                mCalendarView.scrollToPre();
                break;
            default:
                break;
        }
    }

    /**
     * 获取个人信息
     */
    private void getMemberInfo() {
        HttpUtil.getObject(Api.MEMBERINFO.mapClear()
                        .addBody(), MemberInfo.class,
                new HttpUtil.objectCallback() {
                    @Override
                    public void result(boolean b, @Nullable Object obj) {
                        if (b) {
                            MemberInfo info = (MemberInfo) obj;
                            mIntegral.setText(info.getBonusUsable());
                            mSignDay.setText(info.getSigninTimes());
                        }
                    }
                });
    }


    private void getSigninList(String signinDateStart, String signinDateEnd) {
        HttpUtil.getObjectList(Api.getMemberSigninList.mapClear()
                .addMap("signinDateStart", signinDateStart)
                .addMap("signinDateEnd", signinDateEnd)
                .addBody(), SignDate.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    signDates.clear();
                    signDates.addAll((Collection<? extends SignDate>) obj);
                    if (signDates.size() > 0) {
                        setSchemeData(signDates);
                    }
                }
            }
        });
    }

    /**
     * 签到
     */
    private void signIn() {
        HttpUtil.getObject(Api.signIn.mapClear()
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mSchemeMap.put(getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay()).toString(), getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay()));
                    mCalendarView.setMonthView(SimpleSignMonthView.class);
                    mCalendarView.setSchemeDate(mSchemeMap);
                    mCalendarView.update();
                    EventBus.getDefault().post(new SignEvent(true));
                }
            }
        });
    }

    /**
     * 设置签到集合
     *
     * @param signDateList
     */
    private void setSchemeData(List<SignDate> signDateList) {
        mSchemeMap.clear();
        for (int i = 0; i < signDateList.size(); i++) {
            mSchemeMap.put(getSchemeCalendar(signDateList.get(i).getDate()).toString(), getSchemeCalendar(signDateList.get(i).getDate()));
        }
        mCalendarView.setSchemeDate(mSchemeMap);
    }


    @SuppressWarnings("all")
    private Calendar getSchemeCalendar(String date) {
        java.util.Calendar mCalendar = java.util.Calendar.getInstance();
        mCalendar.setTime(TimeUtils.string2Date(date, new SimpleDateFormat("yyyy-MM-dd")));
        int year = mCalendar.get(java.util.Calendar.YEAR);
        int month = mCalendar.get(java.util.Calendar.MONTH) + 1;
        int day = mCalendar.get(java.util.Calendar.DAY_OF_MONTH);
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(Color.parseColor("#04C1B5"));
        if (calendar.isCurrentDay()) {
            isSign = true;
        }
        return calendar;
    }

    @SuppressWarnings("all")
    private Calendar getSchemeCalendar(int year, int month, int day) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(Color.parseColor("#04C1B5"));
        return calendar;
    }

    @Override
    public void onMonthChange(int year, int month) {
        Log.e("luoyc", " year + " + year + "  month +  " + month);
        mMonthView.setText(year + "年" + month + "月");
        getSupportBeginDayofMonth(year, month);
//        getSigninList(mSigninDateStart, mSigninDateEnd);
    }


    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        if (isClick) {
            if (calendar.isCurrentDay() && !isSign) {
                isSign = true;
                signIn();
            }
        }
    }

    /**
     * 根据提供的年月日获取该月份的第一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private void getSupportBeginDayofMonth(int year, int monthOfYear) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(java.util.Calendar.YEAR, year);
        cal.set(java.util.Calendar.MONTH, monthOfYear);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);

        cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        mSigninDateEnd = TimeUtils.date2String(lastDate, new SimpleDateFormat("yyyy-MM-dd"));

        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        mSigninDateStart = TimeUtils.date2String(firstDate, new SimpleDateFormat("yyyy-MM-dd"));
    }
}
