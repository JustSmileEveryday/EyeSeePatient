package lht.wangtong.gowin120.patient.ui.mine.recharge;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;

/**
 * @author Luoyc
 */
@Route(path = "/mine/recharge/RechargeSuccActivity")
public class RechargeSuccActivity extends BaseActivity<RechargeSuccPresenter> implements RechargeSuccContract.View {

    @BindView(R.id.recharge_type)
    TextView rechargeType;
    @BindView(R.id.recharge_num)
    TextView rechargeNum;
    @Autowired
    String payType;
    @Autowired
    String payNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_success;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        rechargeType.setText(payType);
        rechargeNum.setText(payNum);
    }

    @OnClick(R.id.sure_btn)
    public void onViewClicked() {
        finish();
    }
}
