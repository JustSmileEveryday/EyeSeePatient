package lht.wangtong.gowin120.patient.ui.mine.balance;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;

/**
 * 我的余额
 *
 * @author luoyc
 */
@Route(path = "/mine/balance/MyBalanceActivity")
public class MyBalanceActivity extends BaseActivity<MyBalancePresenter> implements MyBalanceContact.View {

    @BindView(R.id.my_balance_num)
    TextView mBalanceNum;
    @BindView(R.id.right_text)
    TextView mRightTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_balance;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mRightTitle.setText("明细");
        mRightTitle.setVisibility(View.VISIBLE);
    }


    @Override
    public void setBalance(String balance) {
        mBalanceNum.setText("￥" + balance);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMemberInfo();
    }

    @OnClick({R.id.recharge_btn, R.id.right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recharge_btn:
                //充值
                ARouter.getInstance()
                        .build("/mine/recharge/RechargeActivity")
                        .navigation();
                break;
            case R.id.right_text:
                //明细
                ARouter.getInstance()
                        .build("/mine/balance/BalanceActivity")
                        .navigation();
                break;
            default:
                break;
        }
    }
}
