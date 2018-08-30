package lht.wangtong.gowin120.patient.ui.mine.balance;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AccountIoInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class BalanceDetailPresenter extends BasePresenter<BalanceDetailContact.View> implements BalanceDetailContact.Presenter{

    @Inject
    public BalanceDetailPresenter() {
    }

    @Override
    public void getBalanceDetail(String memberAccountIoId) {
        HttpUtil.getObject(Api.viewMemberAccountIo.mapClear()
                .addMap("memberAccountIoId", memberAccountIoId)
                .addBody(), AccountIoInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setDetail((AccountIoInfo) obj);
                }
            }
        });
    }
}
