package lht.wangtong.gowin120.patient.ui.mine.balance;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.MemberInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class MyBalancePresenter extends BasePresenter<MyBalanceContact.View> implements MyBalanceContact.Presenter {

    @Inject
    public MyBalancePresenter() {
    }

    @Override
    public void getMemberInfo() {
        HttpUtil.getObject(Api.MEMBERINFO.mapClear()
                        .addBody(), MemberInfo.class,
                new HttpUtil.objectCallback() {
                    @Override
                    public void result(boolean b, @Nullable Object obj) {
                        if (b) {
                            MemberInfo info = (MemberInfo) obj;
                            mView.setBalance(info.getCashUsable());
                        }
                    }
                });
    }
}
