package lht.wangtong.gowin120.patient.ui.home.appointment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.HomeFamilyAdapter;
import lht.wangtong.gowin120.patient.adapter.ServiceAgentAdapter;
import lht.wangtong.gowin120.patient.adapter.ServiceDayAdapter;
import lht.wangtong.gowin120.patient.adapter.ServiceDetailTypeAdapter;
import lht.wangtong.gowin120.patient.adapter.ServiceTimeAdapter;
import lht.wangtong.gowin120.patient.adapter.ServiceTypeAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AgentInfo;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDayInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDetailType;
import lht.wangtong.gowin120.patient.bean.ServiceTimeInfo;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.util.LoginUtil;
import lht.wangtong.gowin120.patient.view.ScrollTopArrowDrawable;
import lht.wangtong.gowin120.patient.view.ServiceDetailDialog;

/**
 * @author Luoyc
 * @date 2018/3/15
 */
@Route(path = "/home/appointment/AppointmentServiceActivity")
public class AppointmentServiceActivity extends BaseActivity<AppointmentServicePresenter> implements AppointmentServiceContact.View {
    @BindView(R.id.service_type)
    RecyclerView mServiceTypeRecyclerView;
    @BindView(R.id.service_type_detail)
    RecyclerView mServiceTypeDetailRecyclerView;
    @BindView(R.id.date_list)
    RecyclerView mDateRecyclerView;
    @BindView(R.id.time_list)
    RecyclerView mTimeRecyclerView;
    @BindView(R.id.agent_list)
    RecyclerView mAgentRecyclerView;
    @BindView(R.id.family_list)
    RecyclerView mFamilyRecyclerView;
    @BindView(R.id.appointment_agent_btn)
    ImageView mAppointmentAgentBtn;
    @BindView(R.id.appointment_family_btn)
    ImageView mAppointmentFamilyBtn;
    @Inject
    ServiceTypeAdapter serviceTypeAdapter;
    @Inject
    ServiceDetailTypeAdapter serviceDetailTypeAdapter;
    @Inject
    ServiceDayAdapter serviceDayAdapter;
    @Inject
    ServiceTimeAdapter serviceTimeAdapter;
    @Inject
    ServiceAgentAdapter serviceAgentAdapter;
    private HomeFamilyAdapter mFamilyAdapter;
    private ScrollTopArrowDrawable mScrollArrowDrawable;
    private ScrollTopArrowDrawable mScrollArrowDrawable2;
    private List<HomeFamilyInfo> familyInfoList;
    private boolean isShow = true;
    private boolean isFamilyShow = true;
    private ServiceDetailDialog mServiceDetailDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_service;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mScrollArrowDrawable = new ScrollTopArrowDrawable(mServiceTypeRecyclerView, getResources().getColor(R.color.service_type_bg));
        mServiceTypeRecyclerView.setBackgroundDrawable(mScrollArrowDrawable);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mServiceTypeRecyclerView.setLayoutManager(layoutManager);
        mServiceTypeRecyclerView.setAdapter(serviceTypeAdapter);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mServiceTypeDetailRecyclerView.setLayoutManager(layoutManager1);
        mServiceTypeDetailRecyclerView.setAdapter(serviceDetailTypeAdapter);
        mScrollArrowDrawable2 = new ScrollTopArrowDrawable(mDateRecyclerView, getResources().getColor(R.color.service_type_bg));
        mDateRecyclerView.setBackgroundDrawable(mScrollArrowDrawable2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDateRecyclerView.setLayoutManager(layoutManager2);
        mDateRecyclerView.setAdapter(serviceDayAdapter);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTimeRecyclerView.setLayoutManager(layoutManager3);
        mTimeRecyclerView.setAdapter(serviceTimeAdapter);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this);
        mAgentRecyclerView.setLayoutManager(layoutManager4);
        mAgentRecyclerView.setAdapter(serviceAgentAdapter);
        /**设置成员RecyclerView*/
        familyInfoList = new ArrayList<>();
        mFamilyAdapter = new HomeFamilyAdapter(familyInfoList);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager5.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFamilyRecyclerView.setLayoutManager(layoutManager5);
        mFamilyRecyclerView.setAdapter(mFamilyAdapter);
        initItemClick();
        mPresenter.initData();
    }

    @Override
    public void setServiceType(List<CommonCdInfo> serviceTypeInfos) {
        if (serviceTypeInfos.size() > 0) {
            mPresenter.loadServiceTypeDetail(serviceTypeInfos.get(0).getCode());
            serviceTypeInfos.get(0).setChoosed(true);
        }
        serviceTypeAdapter.setNewData(serviceTypeInfos);
    }

    @Override
    public void setServiceTypeDetail(List<ServiceDetailType> detailTypeList) {
        serviceDetailTypeAdapter.setNewData(detailTypeList);
        serviceDetailTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void initItemClick() {
        serviceTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mScrollArrowDrawable.setSelectPosition(position);
                CommonCdInfo serviceTypeInfo = (CommonCdInfo) adapter.getData().get(position);
                if (serviceTypeInfo.isChoosed()) {
                    return;
                } else {
                    mPresenter.loadServiceTypeDetail(((CommonCdInfo) adapter.getData().get(position)).getCode());
                    serviceTypeInfo.setChoosed(true);
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        if (i != position) {
                            ((CommonCdInfo) adapter.getData().get(i)).setChoosed(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        serviceDetailTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ServiceDetailType serviceDetailType = (ServiceDetailType) adapter.getData().get(position);
                if (serviceDetailType.isChoose()) {
                    return;
                } else {
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        ((ServiceDetailType) adapter.getData().get(i)).setChoose(false);
                    }
                    serviceDetailType.setChoose(true);
                }
                adapter.notifyDataSetChanged();
                showDetailDialog(((ServiceDetailType) adapter.getData().get(position)).getName(), ((ServiceDetailType) adapter.getData().get(position)).getIntroduce());
            }
        });
        serviceDayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mScrollArrowDrawable2.setSelectPosition(position);
                ServiceDayInfo serviceDayInfo = (ServiceDayInfo) adapter.getData().get(position);
                if (serviceDayInfo.isChoose()) {
                    return;
                } else {
                    mPresenter.loadTime(adapter.getData(), position);
                    serviceDayInfo.setChoose(true);
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        if (i != position) {
                            ((ServiceDayInfo) adapter.getData().get(i)).setChoose(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        serviceTimeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ServiceTimeInfo serviceTimeInfo = (ServiceTimeInfo) adapter.getData().get(position);
                if (serviceTimeInfo.isChoosed()) {
                    return;
                } else {
                    for (int i = 0; i < serviceDayAdapter.getData().size(); i++) {
                        for (int j = 0; j < serviceDayAdapter.getData().get(i).getServiceTimeInfos().size(); j++) {
                            serviceDayAdapter.getData().get(i).getServiceTimeInfos().get(j).setChoosed(false);
                        }
                    }
                    serviceTimeInfo.setChoosed(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        serviceAgentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AgentInfo agentInfo = (AgentInfo) adapter.getData().get(position);
                if (agentInfo.isCheck()) {
                    return;
                } else {
                    agentInfo.setCheck(true);
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        if (i != position) {
                            ((AgentInfo) adapter.getData().get(i)).setCheck(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        mFamilyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!familyInfoList.get(position).isChoose()) {
                    //未选中
                    familyInfoList.get(position).setChoose(true);
                    for (int i = 0; i < familyInfoList.size(); i++) {
                        if (i != position) {
                            familyInfoList.get(i).setChoose(false);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void setDate(List<ServiceDayInfo> serviceDayInfos) {
        serviceDayAdapter.setNewData(serviceDayInfos);
        serviceDayAdapter.notifyDataSetChanged();
        mPresenter.loadTime(serviceDayInfos, 0);
    }

    @OnClick({R.id.appointment_agent_btn, R.id.appointment_family_btn, R.id.sure_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appointment_agent_btn:
                if (isShow) {
                    //收起
                    isShow = false;
                    mAppointmentAgentBtn.setBackgroundResource(R.drawable.agent_unflod_img);
                    mAgentRecyclerView.setVisibility(View.GONE);
                } else {
                    //展开
                    isShow = true;
                    mAppointmentAgentBtn.setBackgroundResource(R.drawable.pick_up_img);
                    mAgentRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.appointment_family_btn:
                if (isFamilyShow) {
                    //收起
                    isFamilyShow = false;
                    mAppointmentFamilyBtn.setBackgroundResource(R.drawable.agent_unflod_img);
                    mFamilyRecyclerView.setVisibility(View.GONE);
                } else {
                    //展开
                    isFamilyShow = true;
                    mAppointmentFamilyBtn.setBackgroundResource(R.drawable.pick_up_img);
                    mFamilyRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.sure_btn:
                saveService();
                break;
            default:
                break;
        }
    }

    @Override
    public void setTime(List<ServiceTimeInfo> serviceTimeInfos) {
        serviceTimeAdapter.setNewData(serviceTimeInfos);
        serviceTimeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAgent(List<AgentInfo> agentInfoList) {
        serviceAgentAdapter.setNewData(agentInfoList);
        serviceAgentAdapter.notifyDataSetChanged();
    }

    @Override
    public void setHomeFamilys(List<HomeFamilyInfo> familyInfos) {
        familyInfoList.clear();
        HomeFamilyInfo mine = new HomeFamilyInfo();
        mine.setName(LoginUtil.user.getMemberName());
        mine.setPicUrl(LoginUtil.user.getPicUrl());
        mine.setChoose(true);
        mine.setFamilyId("");
        familyInfoList.add(mine);
        familyInfoList.addAll(familyInfos);
        mFamilyAdapter.notifyDataSetChanged();
    }

    @Override
    public void saveService() {
        String reservationTypeId = "";
        String reservationServiceDate = "";
        String agentId = "";
        String familyId = "";
        for (int i = 0; i < serviceDetailTypeAdapter.getData().size(); i++) {
            if (serviceDetailTypeAdapter.getData().get(i).isChoose()) {
                reservationTypeId = serviceDetailTypeAdapter.getData().get(i).getReservationTypeId();
            }
        }
        for (int i = 0; i < serviceDayAdapter.getData().size(); i++) {
            for (int j = 0; j < serviceDayAdapter.getData().get(i).getServiceTimeInfos().size(); j++) {
                if (serviceDayAdapter.getData().get(i).getServiceTimeInfos().get(j).isChoosed()) {
                    reservationServiceDate = serviceDayAdapter.getData().get(i).getDateInfo() + " " + serviceDayAdapter.getData().get(i).getServiceTimeInfos().get(j).getTime();
                }
            }
        }
        for (int i = 0; i < serviceAgentAdapter.getData().size(); i++) {
            if (serviceAgentAdapter.getData().get(i).isCheck()) {
                agentId = serviceAgentAdapter.getData().get(i).getAgentId();
            }
        }
        for (int i = 0; i < mFamilyAdapter.getData().size(); i++) {
            if (mFamilyAdapter.getData().get(i).isChoose()) {
                familyId = mFamilyAdapter.getData().get(i).getFamilyId();
            }
        }
        mPresenter.saveNetService(reservationTypeId, reservationServiceDate, agentId, familyId);
    }

    @Override
    public Activity getThisContext() {
        return this;
    }

    @Override
    public void showDetailDialog(String title, String content) {
        if (mServiceDetailDialog == null) {
            mServiceDetailDialog = new ServiceDetailDialog(this);
        }
        mServiceDetailDialog.setTitle(title);
        mServiceDetailDialog.setContent(content);
        mServiceDetailDialog.show();
    }
}
