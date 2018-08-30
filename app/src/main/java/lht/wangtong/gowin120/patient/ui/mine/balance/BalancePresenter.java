package lht.wangtong.gowin120.patient.ui.mine.balance;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.BalanceInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class BalancePresenter extends BasePresenter<BalanceContact.View> implements BalanceContact.Presenter {

    private int pageNo = 1;
    private List<BalanceInfo> balanceInfos;

    @Inject
    public BalancePresenter() {
    }

    @Override
    public void initData() {
        balanceInfos = new ArrayList<>();
        getBalance();
    }

    @Override
    public void getBalance() {
        HttpUtil.getObjectListPage(Api.queryMemberAccountIo.mapClear()
                .addPage(20, pageNo)
                .addBody(), BalanceInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    balanceInfos.addAll(obj != null ? (List<BalanceInfo>) obj : null);
                    mView.setBalance(balanceInfos,total);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        balanceInfos.clear();
        getBalance();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getBalance();

    }
}
