package lht.wangtong.gowin120.patient.ui.mine.balance;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AccountIoInfo;

/**
 * @author luoyc
 */
@Route(path = "/mine/balance/BalanceDetailActivity")
public class BalanceDetailActivity extends BaseActivity<BalanceDetailPresenter> implements BalanceDetailContact.View {

    @Autowired
    String mMemberAccountIoId;
    @BindView(R.id.coin_move)
    TextView coinMove;
    @BindView(R.id.coin)
    TextView coin;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.expense_project)
    TextView expenseProject;
    @BindView(R.id.balance_num)
    TextView balanceNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_balance_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.getBalanceDetail(mMemberAccountIoId);
    }

    @Override
    public void setDetail(AccountIoInfo accountIoInfo) {
        if (accountIoInfo.getCash() > 0) {
            //充值
            coinMove.setText("入账金额");
            coin.setTextColor(getResources().getColor(R.color.green));
            type.setText("充值");
        } else {
            //消费
            coinMove.setText("出账金额");
            coin.setTextColor(getResources().getColor(R.color.black_text));
            type.setText("支出");
        }
        coin.setText("￥" + Math.abs(accountIoInfo.getCash()));
        date.setText(accountIoInfo.getCreatedDate());
        expenseProject.setText(accountIoInfo.getRemark());
        balanceNum.setText(accountIoInfo.getCashUsableNew());
    }
}
