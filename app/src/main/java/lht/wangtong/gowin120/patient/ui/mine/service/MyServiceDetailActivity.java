package lht.wangtong.gowin120.patient.ui.mine.service;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.ServiceRecordAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.ServiceRecordInfo;

/**
 * 我的服务详情
 *
 * @author luoyc
 */
@Route(path = "/mine/service/MyServiceDetailActivity")
public class MyServiceDetailActivity extends BaseActivity<MyServiceDetailPresenter> implements MyServiceDetailContact.View {
    @Autowired
    public String serviceRecordId;
    @BindView(R.id.service_name)
    TextView serviceName;
    @BindView(R.id.service_num)
    TextView serviceNum;
    @BindView(R.id.service_price)
    TextView servicePrice;
    @BindView(R.id.effective_count)
    TextView mEffectiveCount;
    @BindView(R.id.service_agent_name)
    TextView mServiceAgentName;
    @BindView(R.id.has_use_layout)
    LinearLayout mHaveUseLayout;
    @BindView(R.id.record_list)
    RecyclerView mRecordView;
    @BindView(R.id.residue_count)
    TextView mResidueCount;
    @BindView(R.id.pay_state)
    TextView mPayState;
    @Inject
    ServiceRecordAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_service_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mRecordView.setLayoutManager(new LinearLayoutManager(this));
        mRecordView.setAdapter(mAdapter);
        mPresenter.loadService(serviceRecordId);
    }

    @Override
    public void setServiceDetail(ServiceRecordInfo detail) {
        serviceName.setText(detail.getServiceTitle());
        serviceNum.setText(detail.getFormNo());
        servicePrice.setText("￥" + detail.getPrice());
        mServiceAgentName.setText("购买成功后到下列门店消费即可\n" + detail.getServiceAgentName());
        mEffectiveCount.setText(detail.getEffectiveCount() + "次");
        if (!TextUtils.isEmpty(detail.getResidueCount())) {
            mResidueCount.setText(detail.getResidueCount());
        }
        if (detail.getServiceRecordUseRecordList() != null && detail.getServiceRecordUseRecordList().size() > 0) {
            mAdapter.setNewData(detail.getServiceRecordUseRecordList());
            mAdapter.notifyDataSetChanged();
        }
        switch (detail.getUseStatus()) {
            case 1:
                //未使用
                mPayState.setText("订购成功");
                mHaveUseLayout.setVisibility(View.GONE);
                break;
            case 2:
                //使用中
                mPayState.setText("使用中");
                break;
            case 3:
                //已使用
                mPayState.setText("已使用");
                break;
            default:
                break;
        }
    }
}
